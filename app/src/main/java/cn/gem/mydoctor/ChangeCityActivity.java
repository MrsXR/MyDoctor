package cn.gem.mydoctor;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.gem.fragment.ChangeCityFragementTitle;
import cn.gem.fragment.ChangeCityFragmentContent;
import cn.gem.util.IpChangeAddress;


public class ChangeCityActivity extends AppCompatActivity implements ChangeCityFragementTitle.OnChangeA{

    String cityTbl=null;
    Integer provinceId=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);
        getCityTbl(1);
    }


    @Override
    public void OnChangeListener(Integer value) {
        //再次访问服务器获取对应的省份
        if(value==null){
            provinceId=1;
        }else {
            provinceId=value;
        }
        getCityTbl(provinceId);

    }

  public  void changeFragment(String value){
    //动态传值
    FragmentManager fragmentManager=getSupportFragmentManager();
    FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
    //动态ChangeCityFragmentContent
    ChangeCityFragmentContent fragmentContent=new ChangeCityFragmentContent();

   //传值
    Bundle bundle=new Bundle();
    bundle.putString("cityTbl",value);
    fragmentContent.setArguments(bundle);

    fragmentTransaction.replace(R.id.change_city_dyncfragment,fragmentContent);
    fragmentTransaction.commit();
}

    //访问省份信息
    public void   getCityTbl(int provinId){
        final String stl= IpChangeAddress.ipChangeAddress+"CityTblServlet";
        RequestParams requestParams=new RequestParams(stl);
        requestParams.addQueryStringParameter("provinceId",provinId+"");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                cityTbl=result;
                changeFragment(cityTbl);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
}
