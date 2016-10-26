package cn.gem.mydoctor;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.entity.UserTradeTbl;
import cn.gem.util.CommonAdapter;
import cn.gem.weight.newlistview;


public class qianbao_yue_shuzhimingxi_Activity extends AppCompatActivity {


    List<UserTradeTbl> list = new ArrayList<UserTradeTbl>();
    CommonAdapter<UserTradeTbl> usertradetblcommonadapter;

    @InjectView(R.id.shouzhimingxi_fanhui)
    ImageButton shouzhimingxiFanhui;
    @InjectView(R.id.yueshouzhimingxi_toolbar)
    Toolbar yueshouzhimingxiToolbar;
    @InjectView(R.id.qianbao_shouzhimingxi_newlistview)
    newlistview qianbaoShouzhimingxiNewlistview;
    @InjectView(R.id.activity_qianbao_yue_shuzhimingxi_)
    RelativeLayout activityQianbaoYueShuzhimingxi;


    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qianbao_yue_shuzhimingxi_);
        ButterKnife.inject(this);
        Log.i("333", "onCreate: 2222222");

    }




    @OnClick(R.id.shouzhimingxi_fanhui)
    public void onClick() {
        finish();
    }
}
