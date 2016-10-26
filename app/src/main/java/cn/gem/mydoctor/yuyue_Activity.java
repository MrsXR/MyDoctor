package cn.gem.mydoctor;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.gem.entity.OrderTbl;
import cn.gem.util.CommonAdapter;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;
import cn.gem.weight.newlistview;


public class yuyue_Activity extends AppCompatActivity implements newlistview.Onlister{

    ImageButton imageButton;
    @InjectView(R.id.image_yuyue)
    ImageButton imageYuyue;
    @InjectView(R.id.toolbar_yuye)
    Toolbar toolbarYuye;
    @InjectView(R.id.yuyue_list)
    newlistview yuyueList;
    @InjectView(R.id.activity_yuyue)
    RelativeLayout activityYuyue;

    List<OrderTbl> list = new ArrayList<OrderTbl>();
    CommonAdapter<OrderTbl> order_tblCommonAdapter;
    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuyue);
        ButterKnife.inject(this);

        imageButton = (ImageButton) findViewById(R.id.image_yuyue);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initof();
        yuyueList.setOnlister(this);



    }

    public void initof() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "order_tbl_servlet");
        NetUtil netUtil=new NetUtil();
        int id=netUtil.getUser().getUserId();

        requestParams.addQueryStringParameter("userid",id+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();

                Type type = new TypeToken<List<OrderTbl>>() {
                }.getType();


                List<OrderTbl> new_list = gson.fromJson(result, type);

                list.clear();
                list.addAll(new_list);
                Log.i("1111", "onSuccess: "+list);

                if (order_tblCommonAdapter == null) {
                    order_tblCommonAdapter = new CommonAdapter<OrderTbl>(yuyue_Activity.this, list, R.layout.yuyue_itme) {
                        @Override
                        public void convert(ViewHolder viewHolder, OrderTbl order_tbl, int position) {
                            String name = null;
                            String index = null;
                            TextView t1 = viewHolder.getViewById(R.id.yuyue_list_item_tv_jiuzhengren);
                            t1.setText(" 就诊人     " + order_tbl.getUserSname());


                            switch (order_tbl.getOrderState()) {
                                case 1:
                                    name = "预约中";
                                    break;
                                case 2:
                                    name = "预约成功";
                                    break;
                                case 3:
                                    name = "取消预约";
                                    break;
                            }
                            TextView t2 = viewHolder.getViewById(R.id.yuyue_list_item_tv_yuyuezhuangtai);
                            t2.setText(" " + name);
                            Log.i("1111", "convert: "+name);
                            TextView t3 = viewHolder.getViewById(R.id.yuyue_list_item_tv_xingming);
                            t3.setText(" " + order_tbl.getDoctorsSname());

                            switch (order_tbl.getDoctorsPosition()) {
                                case 1:
                                    index = "主任医师";
                                    break;
                                case 2:
                                    index = "副主任医师";
                                    break;
                                case 3:
                                    index = "主治医师";
                                    break;
                                case 4:
                                    index = "普通医师";
                                    break;

                            }
                            TextView t4 = viewHolder.getViewById(R.id.yuyue_list_item_tv_zhiwu);
                            t4.setText(" " + index);

                            TextView t5 = viewHolder.getViewById(R.id.yuyue_list_item_tv_keshi);
                            t5.setText(" " + order_tbl.getHospitalSname());

                            TextView t6 = viewHolder.getViewById(R.id.yuyue_list_item_tv_yiyuan);
                            t6.setText(" " + order_tbl.getDepartmentsSname());

                            TextView t7 = viewHolder.getViewById(R.id.yuyue_list_item_tv_shijian);
                            t7.setText(" " + order_tbl.getOrderMessageTime());
                        }
                    };

                    yuyueList.setAdapter(order_tblCommonAdapter);
                } else {

                    order_tblCommonAdapter.notifyDataSetChanged();
                }


                //yuyueList的点击事件
                yuyueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        list.get(position).getOrderId();//订单的ID
                    }
                });

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Log.i("yuyue_Activity", "onError: "+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void get() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "order_tbl_servlet");
        NetUtil netUtil=new NetUtil();
        int id=netUtil.getUser().getUserId();

        requestParams.addQueryStringParameter("userid",id+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();

                Type type = new TypeToken<List<OrderTbl>>() {
                }.getType();


                List<OrderTbl> new_list = gson.fromJson(result, type);

                if(new_list.size()==list.size()){
                    Toast.makeText(yuyue_Activity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                    yuyueList.completeLoad();//没获取到数据也要改变界面
                    return;
                }


                list.addAll(new_list);
                Log.i("1111", "onSuccess: "+list);

                if (order_tblCommonAdapter == null) {
                    order_tblCommonAdapter = new CommonAdapter<OrderTbl>(yuyue_Activity.this, list, R.layout.yuyue_itme) {
                        @Override
                        public void convert(ViewHolder viewHolder, OrderTbl order_tbl, int position) {
                            String name = null;
                            String index = null;
                            TextView t1 = viewHolder.getViewById(R.id.yuyue_list_item_tv_jiuzhengren);
                            t1.setText(" 就诊人     " + order_tbl.getUserSname());


                            switch (order_tbl.getOrderState()) {
                                case 1:
                                    name = "预约中";
                                    break;
                                case 2:
                                    name = "预约成功";
                                    break;
                                case 3:
                                    name = "取消预约";
                                    break;
                            }
                            TextView t2 = viewHolder.getViewById(R.id.yuyue_list_item_tv_yuyuezhuangtai);
                            t2.setText(" " + name);
                            Log.i("1111", "convert: "+name);
                            TextView t3 = viewHolder.getViewById(R.id.yuyue_list_item_tv_xingming);
                            t3.setText(" " + order_tbl.getDoctorsSname());

                            switch (order_tbl.getDoctorsPosition()) {
                                case 1:
                                    index = "主任医师";
                                    break;
                                case 2:
                                    index = "副主任医师";
                                    break;
                                case 3:
                                    index = "主治医师";
                                    break;
                                case 4:
                                    index = "普通医师";
                                    break;

                            }
                            TextView t4 = viewHolder.getViewById(R.id.yuyue_list_item_tv_zhiwu);
                            t4.setText(" " + index);

                            TextView t5 = viewHolder.getViewById(R.id.yuyue_list_item_tv_keshi);
                            t5.setText(" " + order_tbl.getHospitalSname());

                            TextView t6 = viewHolder.getViewById(R.id.yuyue_list_item_tv_yiyuan);
                            t6.setText(" " + order_tbl.getDepartmentsSname());

                            TextView t7 = viewHolder.getViewById(R.id.yuyue_list_item_tv_shijian);
                            t7.setText(" " + order_tbl.getOrderMessageTime());
                        }
                    };

                    yuyueList.setAdapter(order_tblCommonAdapter);
                } else {

                    order_tblCommonAdapter.notifyDataSetChanged();
                }

                yuyueList.completeLoad();

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


    @Override
    public void ondown() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initof();
                 yuyueList.fanghui();
            }
        },1000);
    }

    @Override
    public void onup() {

        get();

    }
}
