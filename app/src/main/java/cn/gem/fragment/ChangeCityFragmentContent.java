package cn.gem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.gem.entity.CityTbl;
import cn.gem.mydoctor.OrderFirstMainActivity;
import cn.gem.mydoctor.R;
import cn.gem.util.CommonAdapter;
import cn.gem.util.CommonGson;
import cn.gem.util.ViewHolder;


/**
 * 动态fragement存放医院中对应科室的科目的信息
 */
public class ChangeCityFragmentContent extends Fragment {

    List<CityTbl> cityTblList;
    TextView textView;
    CommonAdapter<CityTbl> cityAdapter;
    ListView listview;
    String[] cityPosition=new String[50];
    int i;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.change_city_dync_content,null);
       listview= (ListView) v.findViewById(R.id.change_city_fragment_content_list);
        return v;//不能放回ListView！！！！！！！！！
    }

@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         i=0;
        //获取值
        Bundle bundle=getArguments();
        String value=bundle.getString("cityTbl");
        Gson gson= CommonGson.getGson();
        cityTblList=gson.fromJson(value,new TypeToken<List<CityTbl>>(){}.getType());
        if (cityTblList!=null){
            //万能构造器
            cityAdapter=new CommonAdapter<CityTbl>(getContext(),cityTblList,R.layout.change_city_content_item) {
                @Override
                public void convert(ViewHolder viewHolder, CityTbl cityTbl, int position) {
                    textView=viewHolder.getViewById(R.id.change_city_content_item_text);
                    textView.setText(cityTbl.getCitySname());
                    cityPosition[i++]=cityTbl.getCitySname();//跳转页面的position与City对应的ID不一样
                }
            };

            listview.setAdapter(cityAdapter);
        }


    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String cityName=cityPosition[position];
            Log.i("ChangeCity", "onItemClick: -----------------》"+cityName);
            Intent intent=new Intent(getContext(),OrderFirstMainActivity.class);
            intent.putExtra("cityName",cityName);
            getActivity().startActivity(intent);

        }
    });
    }
}
