package cn.gem.mydoctor;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.entity.UserRecordTbl;
import cn.gem.util.CommonAdapter;
import cn.gem.util.CommonQuantity;
import cn.gem.util.IpChangeAddress;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;
import cn.gem.weight.newlistview;


public class dangan_Activity extends AppCompatActivity implements newlistview.Onlister {




    @InjectView(R.id.imageButton_dangan_tianjia)
    ImageButton imageButtonDangan;
    @InjectView(R.id.image_dangan_fanghui)
    ImageButton imageDangan;
    @InjectView(R.id.dangan_toolbar)
    Toolbar danganToolbar;
    @InjectView(R.id.dangan_newlistview)
    newlistview danganNewlistview;
    @InjectView(R.id.activity_dangan_)
    RelativeLayout activityDangan;

    List<UserRecordTbl> list = new ArrayList<UserRecordTbl>();
    CommonAdapter<UserRecordTbl> userrecordtblcommonadapter;

    String string;
    Handler handler=new Handler();
    ImageButton imageButton;


    String age;
    String name;
    String num;
    String userrecordid;

    int pageNo=0;
    int pageSize=5;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangan_);
        ButterKnife.inject(this);

        inituy();
        danganNewlistview.setOnlister(this);

    }


    @OnClick({R.id.image_dangan_fanghui, R.id.imageButton_dangan_tianjia})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_dangan_fanghui:
                finish();
                break;
            case R.id.imageButton_dangan_tianjia:
                Intent intent = new Intent(this, dangan_xiugaiActivity.class);
                intent.putExtra("flag", CommonQuantity.FIRST);
                startActivity(intent);
                break;


        }
    }

    public void inituy() {
        RequestParams requestParams = new RequestParams(NetUtil.url+"user_record_show_servlet");

        requestParams.addQueryStringParameter("userid",new NetUtil().getUser().getUserId()+"");
        requestParams.addQueryStringParameter("pageNo", 0 + "");
        requestParams.addQueryStringParameter("pageSize", pageSize + "");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();

                Type type = new TypeToken<List<UserRecordTbl>>() {}.getType();

                List<UserRecordTbl> new_list=gson.fromJson(result,type);

                list.clear();
                list.addAll(new_list);
                if (userrecordtblcommonadapter == null) {
                    userrecordtblcommonadapter = new CommonAdapter<UserRecordTbl>(dangan_Activity.this, list, R.layout.userrecord_itme) {
                        @Override
                        public void convert(ViewHolder viewHolder, final UserRecordTbl user_record_tbl, int position) {

                            name=user_record_tbl.getUserrecordName();
                            age=user_record_tbl.getUserrecordAge()+"";
                            num=user_record_tbl.getUserrecordPhone();

                            userrecordid=user_record_tbl.getUserrecordId()+"";

                            TextView tv1= viewHolder.getViewById(R.id.dangan_list_item_tv_xingming);
                               tv1.setText(name);

                            TextView tv2= viewHolder.getViewById(R.id.dangan_list_item_tv_shouji);
                            tv2.setText(user_record_tbl.getUserrecordPhone());

                            ImageButton imagebutton=viewHolder.getViewById(R.id.dangan_xiugai_button);
                            imagebutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(dangan_Activity.this,dangan_xiugaiActivity.class);
                                    Bundle bundle=new Bundle();
                                    intent.putExtra("flag", CommonQuantity.SECOND);
                                    intent.putExtra("user_record_tbl",user_record_tbl);
                                    startActivity(intent);
                                }
                            });


                            switch (user_record_tbl.getUserrecordIdentity()){
                                case 0:
                                    string="本人";
                                    break;
                                case 1:
                                    string="父母";
                                    break;
                                case 2:
                                    string="子女";
                                    break;
                                case 3:
                                    string="朋友";
                                    break;
                                case 4:
                                    string="其他";
                                    break;

                            }


                            TextView tv3= viewHolder.getViewById(R.id.dangan_list_item_tv_shenfen);
                            tv3.setText(string);


                            ImageView imageView=viewHolder.getViewById(R.id.dangan_list_item_tupian);

                            String str= IpChangeAddress.ipChangeAddress+user_record_tbl.getUserrecordPhoto();
                            ImageOptions imageOptions = new ImageOptions.Builder()
                                    .setSquare(true)
                                    .setCrop(true).setSize(100,100).build();


                            x.image().bind(imageView,str,imageOptions);


                        }
                    };

                    danganNewlistview.setAdapter(userrecordtblcommonadapter);
                }else {
                    userrecordtblcommonadapter.notifyDataSetChanged();
                }

                //listView的点击事件
                danganNewlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       int userRecordId= list.get(position).getUserrecordId();//档案人的ID
                        Intent intent=getIntent();
                        intent.putExtra("userRecordId",userRecordId);
                        intent.putExtra("userIllName",list.get(position).getUserrecordName());
                        dangan_Activity.this.setResult(RESULT_OK,intent);
                        dangan_Activity.this.finish();
                    }
                });
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
                inituy();
                danganNewlistview.fanghui();
            }
        },1000);
    }

    @Override
    public void onup() {
        pageNo++;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                get();
                danganNewlistview.fanghui();
            }
        },1000);
    }



    public void get() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "user_record_show_servlet");

        requestParams.addQueryStringParameter("userid", new NetUtil().getUser().getUserId()+ "");
        requestParams.addQueryStringParameter("pageNo", pageNo + "");
        requestParams.addQueryStringParameter("pageSize", pageSize + "");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<UserRecordTbl>>() {
                }.getType();
                List<UserRecordTbl> new_list = gson.fromJson(result, type);

                if (new_list.size() == list.size()) {
                    Toast.makeText(dangan_Activity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                    danganNewlistview.completeLoad();//没获取到数据也要改变界面
                    return;
                }

                list.addAll(new_list);
                if (userrecordtblcommonadapter == null) {
                    userrecordtblcommonadapter = new CommonAdapter<UserRecordTbl>(dangan_Activity.this, list, R.layout.userrecord_itme) {
                        @Override
                        public void convert(ViewHolder viewHolder, UserRecordTbl user_record_tbl, int position) {

                            TextView tv1 = viewHolder.getViewById(R.id.dangan_list_item_tv_xingming);
                            tv1.setText(user_record_tbl.getUserrecordName());

                            TextView tv2 = viewHolder.getViewById(R.id.dangan_list_item_tv_shouji);
                            tv2.setText(user_record_tbl.getUserrecordPhone());

                            switch (user_record_tbl.getUserrecordIdentity()) {
                                case 0:
                                    string = "本人";
                                    break;
                                case 1:
                                    string = "父母";
                                    break;
                                case 2:
                                    string = "子女";
                                    break;
                                case 3:
                                    string = "朋友";
                                    break;
                                case 4:
                                    string = "其他";
                                    break;

                            }


                            TextView tv3 = viewHolder.getViewById(R.id.dangan_list_item_tv_shenfen);
                            tv3.setText(string);


                            ImageView imageView=viewHolder.getViewById(R.id.dangan_list_item_tupian);

                            String str= IpChangeAddress.ipChangeAddress+user_record_tbl.getUserrecordPhoto();

                            ImageOptions imageOptions = new ImageOptions.Builder()
                                    .setSquare(true)
                                    .setCrop(true).setSize(100,100).build();

                            x.image().bind(imageView,str,imageOptions);
                        }
                    };

                    danganNewlistview.setAdapter(userrecordtblcommonadapter);
                } else {
                    userrecordtblcommonadapter.notifyDataSetChanged();
                }
                danganNewlistview.completeLoad();
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
