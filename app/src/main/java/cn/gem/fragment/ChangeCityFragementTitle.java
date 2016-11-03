package cn.gem.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.gem.entity.ProvinceTbl;
import cn.gem.mydoctor.R;
import cn.gem.util.CommonAdapter;
import cn.gem.util.CommonGson;
import cn.gem.util.IpChangeAddress;
import cn.gem.util.ViewHolder;


/**
 * 静态fragment存放医院中科室的信息
 */
public class ChangeCityFragementTitle extends BaseFragment {

    int selectedPosition = 0;// 选中的位置

    public interface OnChangeA{
       void OnChangeListener(Integer value);
    }

    List<ProvinceTbl> provinceTbls;
    OnChangeA onChangeA;
    CommonAdapter<ProvinceTbl> provinceAdapter;
    ListView listView;
    TextView textView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //判断当前嵌入的activity是否实现  OnChangeListener
        if(getActivity() instanceof  OnChangeA){
            onChangeA=(OnChangeA)getActivity();
        }

    }


    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        getData();//获取网络数据，显示在listview上
    }


    public void getViewData() {
        //适配器
        provinceAdapter = new CommonAdapter<ProvinceTbl>(getContext(), provinceTbls, R.layout.change_city_listview_item) {

            @Override
            public void convert(ViewHolder viewHolder, ProvinceTbl provinceTbl, int position) {
                textView=viewHolder.getViewById(R.id.change_province_listview_item_text);
                textView.setText(provinceTbl.getProvinceSname());


                if (selectedPosition == position) {
                    textView.setSelected(true);
                    textView.setPressed(true);
                    textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {
                    textView.setSelected(false);
                    textView.setPressed(false);
                    textView.setBackgroundColor(Color.parseColor("#AADAF5"));

                }
                if(selectedPosition==0&&position==0){
                    textView.setSelected(true);
                    textView.setPressed(true);
                    textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }


            }
        };

        listView.setAdapter(provinceAdapter);

    }

    //找到控件
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //解析布局文件,找到listView的控件
        View v=inflater.inflate(R.layout.change_city_fragement_title,null);
        listView= (ListView) v.findViewById(R.id.change_city_fragement_tital);
        //点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                Log.i("setOnItemClickListener", "setOnItemClickListener"+position);
                //返回选择的省份对应的ID
                setOnChangeListener(onChangeA,position+1);//items
                selectedPosition=position;
                provinceAdapter.notifyDataSetInvalidated();

            }
        });

        return v;
    }

    //判断对应的Activity是否继承了此方法
    public void setOnChangeListener(OnChangeA onChangeA,Integer value){
        boolean bool=onChangeA!=null;
        if (bool) {
            onChangeA.OnChangeListener(value);
        }
    }


    //获取网络数据
    public void getData(){
        String stl= IpChangeAddress.ipChangeAddress+"ProvinceTblServlet";
        RequestParams requestParams = new RequestParams(stl);//访问服务器获取省份信息
        x.http().get(requestParams,new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }
            @Override
            public void onSuccess(String result) {
                //获取省份信息
                provinceTbls=new ArrayList<ProvinceTbl>();
                Gson gson= CommonGson.getGson();
                provinceTbls = gson.fromJson(result,new TypeToken<List<ProvinceTbl>>(){}.getType());
                //获取数据传给TextView
                getViewData();
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
