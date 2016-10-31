package cn.gem.mydoctor;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.gem.entity.ConsultTbl;
import cn.gem.util.CommonAdapter;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;
import cn.gem.weight.newlistview;


public class zixun_Activity extends AppCompatActivity implements newlistview.Onlister {


    ImageButton imageButton;
    @InjectView(R.id.zixun_listview)
    newlistview zixunListview;

    Timestamp timestamp;
    String keshi;
    String jianjie;
    String name;

    Handler handler=new Handler();

    CommonAdapter<ConsultTbl> consult_tblAdapter;
    List<ConsultTbl> list_consult_tbl = new ArrayList<ConsultTbl>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("zixun_Activity", "onCreate: 111");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixun_);
        ButterKnife.inject(this);

        imageButton = (ImageButton) findViewById(R.id.image_zixun_fanghui);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        initof();
        zixunListview.setOnlister(this);

    }

    public void initof() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "consult_item_servlet");
        NetUtil netUtil = new NetUtil();
        int id = netUtil.getUser().getUserId();
        //requestParams.addQueryStringParameter("zixun",1+"");
        requestParams.addQueryStringParameter("userid", id + "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<ConsultTbl>>() {}.getType();
                List<ConsultTbl> new_list_consult_tbl = gson.fromJson(result, type);

                list_consult_tbl.clear();
                list_consult_tbl.addAll(new_list_consult_tbl);

                if (consult_tblAdapter==null) {

                    consult_tblAdapter=new CommonAdapter<ConsultTbl>(zixun_Activity.this,list_consult_tbl,R.layout.zixun_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ConsultTbl consult_tbl, int position) {



                            TextView tv1= viewHolder.getViewById(R.id.prod_list_item_tv_xingming);
                            tv1.setText(consult_tbl.getDoctorsSname());//商品名称

                            String name=null;
                            switch (consult_tbl.getDoctorsPosition()) {
                                case 1:
                                    name="主任医师";
                                    break;
                                case 2:
                                    name="副主任医师";
                                    break;
                                case 3:
                                    name="主治医师";
                                    break;
                                case 4:
                                    name ="普通医师";
                                    break;
                            }

                            TextView tv2=viewHolder.getViewById(R.id.prod_list_item_tv_jianjie);
                            tv2.setText("职位："+name);

                            TextView tv3=viewHolder.getViewById(R.id.prod_list_item_tv_keshi);
                            tv3.setText(consult_tbl.getSubjectSname());

                            TextView tv4=viewHolder.getViewById(R.id.prod_list_item_tv_shijian);
                            tv4.setText("时间： "+consult_tbl.getConsultUserTime());


                        }
                    };
                    zixunListview.setAdapter(consult_tblAdapter);
                }else {

                    consult_tblAdapter.notifyDataSetChanged();
                }
            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Log.i("c", "onError: "+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void get(){
        RequestParams requestParams=new RequestParams(NetUtil.url+"consult_item_servlet");

        NetUtil netUtil=new NetUtil();
        int id=netUtil.getUser().getUserId();
        //requestParams.addQueryStringParameter("zixun",1+"");
        requestParams.addQueryStringParameter("userid",id+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();

                Type type = new TypeToken<List<ConsultTbl>>() {}.getType();

                List<ConsultTbl> new_list_consult_tbl = gson.fromJson(result, type);

                if(new_list_consult_tbl.size()==list_consult_tbl.size()){

                    Toast.makeText(zixun_Activity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                    zixunListview.completeLoad();//没获取到数据也要改变界面
                    return;
                }


                list_consult_tbl.addAll(new_list_consult_tbl);
                ;
                if (consult_tblAdapter==null) {

                    consult_tblAdapter=new CommonAdapter<ConsultTbl>(zixun_Activity.this,list_consult_tbl,R.layout.zixun_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ConsultTbl consult_tbl, int position) {



                            TextView tv1= viewHolder.getViewById(R.id.prod_list_item_tv_xingming);
                            tv1.setText(consult_tbl.getDoctorsSname());//商品名称

                            String name=null;
                            switch (consult_tbl.getDoctorsPosition()) {
                                case 1:
                                    name="主任医师";
                                    break;
                                case 2:
                                    name="副主任医师";
                                    break;
                                case 3:
                                    name="主治医师";
                                    break;
                                case 4:
                                    name ="普通医师";
                                    break;
                            }

                            TextView tv2=viewHolder.getViewById(R.id.prod_list_item_tv_jianjie);
                            tv2.setText("职位："+name);

                            TextView tv3=viewHolder.getViewById(R.id.prod_list_item_tv_keshi);
                            tv3.setText(consult_tbl.getSubjectSname());

                            TextView tv4=viewHolder.getViewById(R.id.prod_list_item_tv_shijian);
                            tv4.setText("时间： "+consult_tbl.getConsultUserTime());


                        }
                    };
                    zixunListview.setAdapter(consult_tblAdapter);
                }else {

                    consult_tblAdapter.notifyDataSetChanged();
                }
                zixunListview.completeLoad();
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
                zixunListview.fanghui();
            }
        },1000);
    }

    @Override
    public void onup() {

        get();
    }
}
