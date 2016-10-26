package cn.gem.mydoctor;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.overlayutil.PoiOverlay;

public class GetLocationActivity extends FragmentActivity implements
        OnGetPoiSearchResultListener, OnGetSuggestionResultListener {

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    MapView mMapView = null;
    double altitude;
    double longitude;
    String address = null;
    @InjectView(R.id.get_location_back)
    ImageButton getLocationBack;
    @InjectView(R.id.search_clear)
    ImageButton searchClear;
    @InjectView(R.id.go_location)
    TextView goLocation;

    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private BaiduMap mBaiduMap = null;
    private List<String> suggest;
    /**
     * 搜索关键字输入窗口
     */
    private EditText editCity = null;
    private AutoCompleteTextView keyWorldsView = null;
    private ArrayAdapter<String> sugAdapter = null;
    private int loadIndex = 0;
    int searchType = 0;  // 搜索的类型，在显示时区分

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_get_location);
        ButterKnife.inject(this);

        initBack();//返回首页

        //d对应控件
        mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager()
                .findFragmentById(R.id.bmapView_fragment))).getBaiduMap();//bmapView

        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数

        initPosition();//d定位
        mLocationClient.start();

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 构造定位数据
        getLocation(altitude, longitude);
//  getLocationHospital(null);

        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        // 初始化建议搜索模块，注册建议搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        //输入的文本
        editCity = (EditText) findViewById(R.id.baidu_search);
        //设置文本改变的时候监听事件
        editCity.addTextChangedListener(new TextChange());
    }

    void initBack(){
        ImageButton imageButton= (ImageButton) findViewById(R.id.get_location_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initPosition() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    private void getLocation(double altitude, double longitude) {
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(150)
                //此处设置开发者获取到的方向信息，顺时针0-360.direction(location.getDirection())
                .direction(1).latitude(altitude)
                .longitude(longitude).build();
//        Log.i("MyLocationListener", "纬度: "+location.getLatitude()+"经度:"+location.getLongitude());
        //设置定位数据
        mBaiduMap.setMyLocationData(locData);
        //跳转到当前位置
        LatLng ll = new LatLng(31.281843, 120.749582);//地理坐标数据
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 15.0f);//设置地图中心点以及缩放级别
        mBaiduMap.animateMapStatus(u);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mPoiSearch.destroy();
        mSuggestionSearch.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    //监听事件的重写方法----------
    @Override
    public void onGetPoiResult(PoiResult poiResult) {

        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(GetLocationActivity.this, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(poiResult);
            overlay.addToMap();
            overlay.zoomToSpan();
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : poiResult.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(GetLocationActivity.this, strInfo, Toast.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(GetLocationActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(GetLocationActivity.this, poiDetailResult.getName() + ": " + poiDetailResult.getAddress(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {

    }


    void getLocationHospital(String address) {
        searchType = 1;
        String citystr;
        if (address != null) {
            citystr = address;
        } else {
            citystr = editCity.getText().toString();
        }
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(citystr).keyword("医院").pageNum(loadIndex));
    }

    //点击事件
    @OnClick({R.id.get_location_back, R.id.search_clear, R.id.go_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_location_back:
                //返回主页
                break;
            case R.id.search_clear:
                //清除字段
                break;
            case R.id.go_location:
                //搜索
                getLocationHospital(null);
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            altitude = location.getAltitude();
            longitude = location.getLatitude();
            address = location.getAddrStr().substring(5, 7).trim();

            getLocationHospital(address);
        }
    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
    }


    //重写Text的长度改变的事件
    class  TextChange implements TextWatcher {

        //输入字符之前
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        //输入字符的时候
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            //如果输入长度不为空，且要显示的按钮存在
            if (!TextUtils.isEmpty(s)) {
                if (searchClear != null) {
                    searchClear.setVisibility(View.VISIBLE);
                    //点击事件
                    searchClear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ( editCity!= null) {
                                editCity.getText().clear();
                            }
                        }
                    });
                }
            } else {
                if (searchClear != null) {
                    searchClear.setVisibility(View.GONE);
                }
            }
        }

        //字符输入结束的时候
        @Override
        public void afterTextChanged(Editable s) {

        }

    }


}
