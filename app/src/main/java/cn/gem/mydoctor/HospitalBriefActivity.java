package cn.gem.mydoctor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.entity.DepartmentsTbl;
import cn.gem.entity.HospitalTblOne;
import cn.gem.fragment.HospitalBriefOrders;
import cn.gem.fragment.HospitalOneBrief;
import cn.gem.util.CommonGson;
import cn.gem.util.GetImageView;
import cn.gem.util.IpChangeAddress;

public class HospitalBriefActivity extends AppCompatActivity {
    Toolbar toolbar;
    @InjectView(R.id.hospital_brief_photo)
    ImageView hospitalBriefPhoto;
    @InjectView(R.id.hospital_brief_name)
    TextView hospitalBriefName;
    @InjectView(R.id.hospital_brief_address)
    TextView hospitalBriefAddress;
    @InjectView(R.id.hosptital_baidu)
    Button hosptitalBaidu;
    @InjectView(R.id.hospital_order)
    Button hospitalOrder;
    @InjectView(R.id.hospital_brief)
    Button hospitalBrief;
    @InjectView(R.id.hospital_regulation)
    Button hospitalRegulation;
    @InjectView(R.id.hospital_brief_fragemnt)
    FrameLayout hospitalBriefFragemnt;

    //医院某一个对象
    HospitalTblOne hospitalTblOne;
    //医院的科目
    List<DepartmentsTbl> departmentsTbl;

    int hospitalId=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_brief);
        ButterKnife.inject(this);
        toolbar = (Toolbar) findViewById(R.id.hospital_detail_toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.back6);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

     //获取医院的ID
        if(getIntent().getExtras()!=null){
            Bundle bundle=getIntent().getExtras();
            hospitalId= bundle.getInt("hospitalId");
            getHospitalTbl(hospitalId);
        }

        getColor(hospitalOrder,hospitalBrief,hospitalRegulation);//默认点击事件
    }

    //toolbar点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.mipmap.back6:
              finish();
                break;
            case R.id.share_hospital_menu:
                //分享医院

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //设置toolbar中menu显示内容
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hospital_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    void onCheck(){
        //百度地图的点击事件，点击进入百度地图
        hosptitalBaidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //点击为科目信息
        hospitalOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragmentOrder(departmentsTbl,hospitalTblOne.getHospitalTbl().getHospitalId());
                hospitalOrder.setSelected(true);

                getColor(hospitalOrder,hospitalBrief,hospitalRegulation);

            }
        });
        //医院简介
        hospitalBrief.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stl=hospitalTblOne.getHospitalTbl().getHospitalBrief();
                getColor(hospitalBrief,hospitalRegulation,hospitalOrder);
                changeFragment(stl);

            }
        });
        //预约规则的描述
        hospitalRegulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp=hospitalTblOne.getHospitalTbl().getHospitalOrderRule();
                getColor(hospitalRegulation,hospitalOrder,hospitalBrief);
                changeFragment(temp);
            }
        });
    }

    private void getColor(Button button1,Button button2,Button button3){

        button1.setSelected(true);
        button1.setPressed(true);
        button1.setBackgroundColor(Color.parseColor("#FFFFFF"));

        button2.setSelected(false);
        button2.setPressed(false);
        button2.setBackgroundColor(Color.parseColor("#AADAF5"));

        button3.setSelected(false);
        button3.setPressed(false);
        button3.setBackgroundColor(Color.parseColor("#AADAF5"));
    }


    //医院简历和医院预约规则fragment被替换
    private void changeFragment(String brief){
        Bundle bundle=new Bundle();
        bundle.putString("hospitalBrief",brief);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        HospitalOneBrief hospitalOneBrief=new HospitalOneBrief();
        hospitalOneBrief.setArguments(bundle);

        fragmentTransaction.replace(R.id.hospital_brief_fragemnt,hospitalOneBrief);
        fragmentTransaction.commit();
    }

    //医院的科室科目信息
    private void changeFragmentOrder(List<DepartmentsTbl> stl,int hospitalId){
        Bundle bundle=new Bundle();
        Gson g= CommonGson.getGson();
        String send=g.toJson(stl);
        bundle.putString("hospitalBrief",send);
        bundle.putInt("hospitalId",hospitalId);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        HospitalBriefOrders hbo=new HospitalBriefOrders();
        hbo.setArguments(bundle);

        fragmentTransaction.replace(R.id.hospital_brief_fragemnt,hbo);
        fragmentTransaction.commit();
    }


    //获取医院信息
    public  void getHospitalTbl(int hospitalId){
        String stl= IpChangeAddress.ipChangeAddress+"HospitalTblOneServlet";
        RequestParams requestParams =new RequestParams(stl);
        requestParams.addQueryStringParameter("hospitalId",hospitalId+"");
        x.http().get(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                departmentsTbl=new ArrayList<DepartmentsTbl>();
                hospitalTblOne=new HospitalTblOne();
                Gson gson= CommonGson.getGson();
                hospitalTblOne=gson.fromJson(result,HospitalTblOne.class);
                departmentsTbl=hospitalTblOne.getDepartmentsTbl();

                //设置医院界面数据
                if(hospitalTblOne.getHospitalTbl().getHospitalPhoto()!=null){
                    //设置医院的图片
                    GetImageView getImageView=new GetImageView(hospitalTblOne.getHospitalTbl().getHospitalPhoto(),hospitalBriefPhoto);
                }

                hospitalBriefName.setText(hospitalTblOne.getHospitalTbl().getHospitalSname());//医院的名字
                hospitalBriefAddress.setText(hospitalTblOne.getHospitalTbl().getHospitalAddress());

                //设置预约信息中的科目数据初始化
                changeFragmentOrder(departmentsTbl,hospitalTblOne.getHospitalTbl().getHospitalId());

                //点击事件
                onCheck();
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

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }

}
