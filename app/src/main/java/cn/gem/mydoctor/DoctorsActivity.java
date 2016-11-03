package cn.gem.mydoctor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.gem.application.MyApplication;
import cn.gem.entity.CommentOrderDetailTbl;
import cn.gem.entity.ConsultTbl;
import cn.gem.entity.DoctorInHospital;
import cn.gem.entity.DoctorsTbl;
import cn.gem.entity.DoctorsWork;
import cn.gem.fragment.BaseFragment;
import cn.gem.fragment.ChangeDateFirst;
import cn.gem.fragment.ChangeDateSecond;
import cn.gem.util.CommonAdapter;
import cn.gem.util.CommonGson;
import cn.gem.util.IpChangeAddress;
import cn.gem.util.ViewHolder;
import cn.gem.weight.NoScrollListview;

public class DoctorsActivity extends AppCompatActivity {

    @InjectView(R.id.doctor_photo)
    ImageView doctorPhoto;
    @InjectView(R.id.doctor_sname)
    TextView doctorSname;
    @InjectView(R.id.doctor_sex)
    TextView doctorSex;
    @InjectView(R.id.doctor_in_position)
    TextView doctorInPosition;
    @InjectView(R.id.doctor_recommend)
    TextView doctorRecommend;
    @InjectView(R.id.doctors_detail_content1)
    RelativeLayout doctorsDetailContent1;
    @InjectView(R.id.doctor_in_hospital)
    TextView doctorInHospital;
    @InjectView(R.id.hospital_address)
    TextView hospitalAddress;
    @InjectView(R.id.doctor_brief)
    TextView doctorBrief;
    @InjectView(R.id.collect_doctor_button)
    Button collectDoctorButton;
    @InjectView(R.id.doctors_detail_content2)
    RelativeLayout doctorsDetailContent2;
    @InjectView(R.id.doctor_brief_viewpage)
    ViewPager doctorBriefViewpage;
    @InjectView(R.id.doctor_go_comment)
    ImageButton doctorGoComment;
    @InjectView(R.id.doctor_user_comment_listview)
    NoScrollListview doctorUserCommentListview;
    @InjectView(R.id.doctor_go_into)
    ImageButton doctorGoInto;
    @InjectView(R.id.doctor_user_consult_listview)
    NoScrollListview doctorUserConsultListview;
    @InjectView(R.id.doctors_detail_content_top)
    LinearLayout doctorsDetailContentTop;
    @InjectView(R.id.doctors_scrollView)
    ScrollView doctorsScrollView;
    @InjectView(R.id.doctor_in_subject)
    TextView doctorInSubject;
    @InjectView(R.id.doctor_look_comment)
    TextView doctorLookComment;

    private static final int FIRSTFLAG = 1;
    private static final int SECONDFLAG = 2;

    Toolbar toolbar;
    int i = 2;
    DoctorsTbl doctorsTbl;
    List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
    int doctorsId;
    int isData=0;

    DoctorsWork doctorsWork = new DoctorsWork();
    List<ConsultTbl> consultTbls;
    List<CommentOrderDetailTbl> commentOrderDetailTbls;

    CommonAdapter<ConsultTbl> commonC;//咨询
    CommonAdapter<CommentOrderDetailTbl> commonR;//评价

    DoctorInHospital doctorInH;//医生所在医院的信息
    TextView tvPrice = null;
    MyApplication myApplication= (MyApplication) getApplication();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        ButterKnife.inject(this);
        tvPrice = (TextView) findViewById(R.id.consult_doctor_price);
        toolbar = (Toolbar) findViewById(R.id.doctors_detail_toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.back6);
        setSupportActionBar(toolbar);

        //获取选择医生的信息
        Intent intent = getIntent();
        if (intent.getParcelableExtra("doctorOne") != null) {
            doctorsTbl = intent.getParcelableExtra("doctorOne");
            doctorsId = doctorsTbl.getDoctorsId();
            initView();
        } else if (intent.getIntExtra("doctorsId", 0) != 0) {
            doctorsId = intent.getIntExtra("doctorsId", 0);
            Log.i("DoctorsActivity", "onCreate: ------------------------");
            getOneDoctor();
        }


        //toolbar上的导航事件按钮点击事件
        checkToolbar();
        buttonOnCheck();//评价+咨询的跳转界面
    }

    //从再次预约界面跳转进来
    private void getOneDoctor() {

        String stl = IpChangeAddress.ipChangeAddress + "OneDoctorsTblServlet";
        RequestParams requestParams = new RequestParams(stl);
        requestParams.addQueryStringParameter("doctorsId", doctorsId + "");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = CommonGson.getGson();
                doctorsTbl = gson.fromJson(result, DoctorsTbl.class);

                initView();
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


    //设置初始显示的fragment+医生的信息展示
    public void initView() {


        //对医生信息的展示
        fragmentList.add(new ChangeDateFirst(doctorsId));
        fragmentList.add(new ChangeDateSecond(doctorsId));

        //像doctorBriefViewpage中添加fragment
        doctorBriefViewpage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });

        doctorBriefViewpage.setOffscreenPageLimit(2);

        //对listview控件添加数据
        getListViewData();

    }

    //获取数据库中医生对于的咨询信息、评价信息
    public void getListViewData() {
        String stl = IpChangeAddress.ipChangeAddress + "DoctorsWorkServlet";
        RequestParams requestParams = new RequestParams(stl);
        requestParams.addQueryStringParameter("doctorsId", doctorsId + "");
        requestParams.addQueryStringParameter("userId", myApplication.getUserTbl().getUserId() + "");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson = CommonGson.getGson();
                doctorsWork = gson.fromJson(result, DoctorsWork.class);
                //对应的集合
                consultTbls = doctorsWork.getConsultTbl();
                commentOrderDetailTbls = doctorsWork.getCommentOrderDetailTbl();
                doctorInH = doctorsWork.getDoctorInHospital();//医生所在医院的信息
                isData=doctorsWork.getIsData();
                
                //将数据展示在ListView上面
                showListViewData();

                //对应的医生信息展示
                getDoctorsData();
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


    public void showListViewData() {
        //相关咨询
        if (commonC == null) {
            commonC = new CommonAdapter<ConsultTbl>(this, consultTbls, R.layout.doctor_user_informatoin) {

                @Override
                public void convert(ViewHolder viewHolder, ConsultTbl consultTbl, int position) {

                    TextView tvUser = viewHolder.getViewById(R.id.doctor_user_informatoin);//医患信息

                    TextView tvComment = viewHolder.getViewById(R.id.doctor_user_consult);//咨询内容

                    String sex = null;
                    if (consultTbl.getUserTbl().getUserSex() == 0) {
                        sex = "男";
                    } else {
                        sex = "女";
                    }

                    tvUser.setText("患者" + consultTbl.getUserTbl().getUserAge() + "岁 * " + sex);
                    tvComment.setText(consultTbl.getConsultDetailContent());
                }
            };
            doctorUserCommentListview.setAdapter(commonC);
        } else {
            commonC.notifyDataSetChanged();
        }

        //评价
        if (commonR == null) {
            commonR = new CommonAdapter<CommentOrderDetailTbl>(this, commentOrderDetailTbls, R.layout.doctor_user_informatoin) {
                @Override
                public void convert(ViewHolder viewHolder, CommentOrderDetailTbl commentOrderDetailTbl, int position) {
                    TextView tvUser = viewHolder.getViewById(R.id.doctor_user_informatoin);//医患信息
                    LinearLayout linearLayout = viewHolder.getViewById(R.id.doctor_user_informatoin_temp);
                    linearLayout.setVisibility(View.VISIBLE);
                    LinearLayout linearLayout2 = viewHolder.getViewById(R.id.doctor_user_informatoin_temp2);
                    linearLayout2.setVisibility(View.VISIBLE);
                    TextView tvIllName = viewHolder.getViewById(R.id.doctor_user_ill_name);
                    RatingBar ratingBar = viewHolder.getViewById(R.id.doctor_user_attitude);//医生态度评星
                    RatingBar ratingBarT = viewHolder.getViewById(R.id.doctor_user_treat);//治疗评星
                    RatingBar ratingBarC = viewHolder.getViewById(R.id.doctor_user_comment);//推荐评星
                    TextView tvComment = viewHolder.getViewById(R.id.doctor_user_consult);//咨询内容

                    tvUser.setText("来自患者" + commentOrderDetailTbl.getUserSname() + "的评论");
                    tvIllName.setText(commentOrderDetailTbl.getOrderIllSname());
                    ratingBar.setRating(commentOrderDetailTbl.getCommentOrderDetailAttitude());
                    ratingBarT.setRating(commentOrderDetailTbl.getCommentOrderDetailTreat());
                    ratingBarC.setRating(commentOrderDetailTbl.getCommentOrderDetailType());
                    tvComment.setText(commentOrderDetailTbl.getCommentOrderDetailContent());

                }
            };
            doctorUserConsultListview.setAdapter(commonR);
        } else {
            commonR.notifyDataSetChanged();
        }

    }

    //对应的医生信息展示
    public void getDoctorsData() {
        //医生头像获取数据库得到头像
        if (doctorsTbl.getDoctorsPhoto() != null) {
            String photoUrl = IpChangeAddress.ipChangeAddress + doctorsTbl.getDoctorsPhoto();
            ImageOptions imageOptions = new ImageOptions.Builder()
                    .setCircular(true)
                    .setCrop(true).setSize(65, 65).build();

            x.image().bind(doctorPhoto, photoUrl, imageOptions);
        }

        //姓名
        doctorSname.setText(doctorsTbl.getDoctorsSname());
        //性别
        if (doctorsTbl.getDoctorsSex() == 0) {
            doctorSex.setText("男");
        } else {
            doctorSex.setText("女");
        }

        //职位
        switch (doctorsTbl.getDoctorsPosition()) {
            case 1:
                doctorInPosition.setText("主任医师");
                break;
            case 2:
                doctorInPosition.setText("副主任医师");
                break;
            case 3:
                doctorInPosition.setText("主治医师");
                break;
            case 4:
                doctorInPosition.setText("普通医师");
                break;
        }

        doctorRecommend.setText(doctorsTbl.getDoctorsRecommend() + "");
        tvPrice.setText(" ￥ " + doctorsTbl.getDoctorsConsultPrice() + "元");

        doctorInHospital.setText(doctorInH.getHospitalSname());//医院名称
        doctorInSubject.setText(doctorInH.getSubjectSname());//科室名称
        hospitalAddress.setText(doctorInH.getHospitalAddress());

        doctorBrief.setText(doctorsTbl.getDoctorsBrief());//医生简介


        if(isData!=0){
            Log.i("DoctorsActivity", "getDoctorsData: ========+++++======"+isData);
            toolbar.getMenu().getItem(R.id.collect_doctor_menu).setIcon(R.drawable.collect2);
            Log.i("DoctorsActivity", "getDoctorsData: ========+++++======"+isData);
        }

        setMenuItem(isData);
    }


    private void setMenuItem(int isData){
        //设置menu的item的点击事件
        if(isData==0){
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    //医生收藏
                    case R.id.collect_doctor_menu:
                        if ((i++) % 2 == 0) {
                            item.setIcon(R.drawable.collect2);
                        } else {
                            item.setIcon(R.mipmap.collect1);
                        }
                        break;
                    //医生分享
                    case R.id.share_doctor_menu:
                }
                return false;
            }
        });}

        if(isData!=0){
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        //医生收藏
                        case R.id.collect_doctor_menu:
                            if ((i++) % 2 == 0) {
                                item.setIcon(R.mipmap.collect1);
                            } else {
                                item.setIcon(R.drawable.collect2);
                            }
                            break;
                        //医生分享
                        case R.id.share_doctor_menu:
                    }
                    return false;
                }
            });}
    }

    //toolbar上的导航事件按钮点击事件
    public void checkToolbar() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Navigation", "MyToolbarActivity");//未设置！！！
            }
        });

        //返回上一个界面
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //设置toolbar中menu显示内容
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.doctors_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void buttonOnCheck() {
        doctorGoInto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorsActivity.this, MangCommentActivity.class);
                intent.putExtra("doctotId", doctorsId);
                startActivity(intent);
            }
        });

        doctorLookComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorsActivity.this, MangCommentActivity.class);
                intent.putExtra("doctotId", doctorsId);
                startActivity(intent);
            }
        });
    }

}
