package cn.gem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import cn.gem.entity.OrdertimeDetailTbl;
import cn.gem.mydoctor.OrderDoctorActivity;
import cn.gem.mydoctor.R;
import cn.gem.util.GetCalendar;
import cn.gem.util.GridViewAdapterSecond;
import cn.gem.util.IpChangeAddress;
import cn.gem.weight.GridViewAdapter;
import cn.gem.weight.WeekDayView;

/**
 * Created by sony on 2016/10/23.
 */
public class ChangeDateSecond  extends BaseFragment{


    Map<Integer,Integer> mp;//网格对应位置，位置对应的ID
    List<String> list=new ArrayList<>();
    WeekDayView weekDayView;
    GridView gridView;
    TextView textViewDate;
    GetCalendar getCalendar=new GetCalendar();//日期
    int doctorId=0;
    List<OrdertimeDetailTbl> orderDetailTbl=new ArrayList<>();//医生提供预约时间

    Map<Integer,String> mm;

    public ChangeDateSecond() {
    }

    public ChangeDateSecond(int doctorId) {
        this.doctorId = doctorId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_grid_view,null);
        weekDayView= (WeekDayView) v.findViewById(R.id.week_day_view);
        gridView= (GridView) v.findViewById(R.id.gridView_date);
        textViewDate= (TextView) v.findViewById(R.id.date_show);//展示日期
        //初始化日期展示

        textViewDate.setText(getCalendar.isNextToday());


        return v;
    }

    @Override
    public void initView() {
        list=getCalendar.getSecondDate();
        weekDayView.setWeekDate(list);
    }

    @Override
    public void initEvent() {
        getDoctorDate();
    }

    @Override
    public void initData() {
        //gridView的点击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (Map.Entry<Integer, Integer> entry : mp.entrySet()) {
                    if (entry.getKey()==position){
                        Intent intent =new Intent(getContext(),OrderDoctorActivity.class);
                        String sendDate=mm.get(position)+isMoning(position);
                        Bundle bundle =new Bundle();
                        bundle.putString("sendDate",sendDate);
                        bundle.putInt("doctorId",doctorId);
                        bundle.putInt("orderDetailTblId",entry.getValue());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }

            }
        });
    }

    public  void getDoctorDate(){
        String stl= IpChangeAddress.ipChangeAddress+"OrdertimeDetailTblServlet";
        RequestParams requestParams=new RequestParams(stl);
        requestParams.addQueryStringParameter("doctorId",doctorId+"");

        //获取医生的提供时间
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson= new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                Type type=new TypeToken< List<OrdertimeDetailTbl>>(){}.getType();
                List<OrdertimeDetailTbl> listNew=gson.fromJson(result,type);
                orderDetailTbl.clear();
                orderDetailTbl.addAll(listNew);

                getPosition();//判断时间,对temp[]进行赋值

                gridView.setHorizontalSpacing(5);
                gridView.setVerticalSpacing(5);

                GridViewAdapterSecond gridViewAdapter=new GridViewAdapterSecond(getActivity(),mp);
                gridView.setAdapter(gridViewAdapter);


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


    private void getPosition(){
        mm=new Hashtable<>();
        mp=new Hashtable<>();
        int k=0;

        GetCalendar getCalendar=new GetCalendar();

        list=getCalendar.getSecondDate();//获取下一周的时间段

        Calendar mCalendar = Calendar.getInstance();
        //获取日期
        List<String> date=new ArrayList<String>();
        SimpleDateFormat dfM = new SimpleDateFormat("MM-dd");//定义格式，不显示毫秒

        for(OrdertimeDetailTbl o:orderDetailTbl){

            //如果预约人数没有满员
            if(o.getOrdertimeDetailNumber()<=o.getOrdertimeDetailTotal()) {
                String dateM = dfM.format(o.getOrdertimeDetailTimeTo());//取出对应开始日期
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals(dateM)) {
                        //大于八点，小于12点---- 上午
                        if (o.getOrdertimeDetailTimeTo().getHours() >= 8&&o.getOrdertimeDetailTimeTo().getHours() <= 12) {
                            //上午

                            mp.put(i,o.getOrdertimeDetailId());
                            //记录对应的时间
                            mm.put(i,list.get(i));

                            //判断是不是到下午+晚上
                            if(o.getOrdertimeDetailTimeFrom().getHours()>=12&&o.getOrdertimeDetailTimeFrom().getHours()<=18){
                                //上午+下午

                                mp.put(i+8,o.getOrdertimeDetailId());
                                mm.put(i+8,list.get(i));
                            }else if(o.getOrdertimeDetailTimeFrom().getHours()>=12&&o.getOrdertimeDetailTimeFrom().getHours()<=24){

                                mp.put(i+8,o.getOrdertimeDetailId());
                                mp.put(i+16,o.getOrdertimeDetailId());
                                mm.put(i+8,list.get(i));
                                mm.put(i+16,list.get(i));
                            }

                            //下午
                        }else if(o.getOrdertimeDetailTimeTo().getHours() >= 12&&o.getOrdertimeDetailTimeTo().getHours()<=18){

                            mp.put(i+8,o.getOrdertimeDetailId());
                            mm.put(i+8,list.get(i));
                            if(o.getOrdertimeDetailTimeFrom().getHours()>=18
                                    &&o.getOrdertimeDetailTimeFrom().getHours()<=24){
                                //下午+晚上

                                mp.put(i+16,o.getOrdertimeDetailId());
                                mm.put(i+16,list.get(i));

                            }
                        }else if(o.getOrdertimeDetailTimeTo().getHours()>=18){
                            mp.put(i+16,o.getOrdertimeDetailId());
                            mm.put(i+16,list.get(i));
                        }

                    }
                }
            }
        }
    }


    private  String isMoning(int i){
        String is=null;
        if (i<8){
            is="上午";
        }else if (i>=8&&i<16){
            is="下午";
        }else if (i>=16&&i<24){
            is="晚上";
        }
        return is;
    }


}
