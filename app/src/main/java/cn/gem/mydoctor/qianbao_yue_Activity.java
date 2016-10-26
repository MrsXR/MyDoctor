package cn.gem.mydoctor;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class qianbao_yue_Activity extends AppCompatActivity {

    @InjectView(R.id.image_fanhui)
    ImageButton imageFanhui;
    @InjectView(R.id.toolbar_yue_toolbar)
    Toolbar toolbarYueToolbar;
    @InjectView(R.id.text_yue_dangqianzhanghuyue)
    TextView textYueDangqianzhanghuyue;
    @InjectView(R.id.qiaobaoyue_text)
    RelativeLayout qiaobaoyueText;
    @InjectView(R.id.liner_yue_chongzhi)
    LinearLayout linerYueChongzhi;
    @InjectView(R.id.liner_yue_shouzhimingxi)
    LinearLayout linerYueShouzhimingxi;
    @InjectView(R.id.activity_qianbaoyue_)
    RelativeLayout activityQianbaoyue;


    String str1;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qianbao_yue_);
        ButterKnife.inject(this);

        Intent intent = getIntent();
         str1=intent.getStringExtra("userid");
         str = intent.getStringExtra("values");

        textYueDangqianzhanghuyue.setText(str);

        Log.i("111111111", "onCreate: "+str1);
        Log.i("111111111", "onCreate: "+str);
    }

    @OnClick({R.id.image_fanhui, R.id.liner_yue_chongzhi, R.id.liner_yue_shouzhimingxi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_fanhui:
                finish();
                break;
            case R.id.liner_yue_chongzhi:
                Intent intent = new Intent(qianbao_yue_Activity.this, qianbao_yuechongzhi_Activity.class);
                intent.putExtra("userid",str1);
                startActivity(intent);
                break;
            case R.id.liner_yue_shouzhimingxi:
                Intent intent1 = new Intent(qianbao_yue_Activity.this, qianbao_yue_shuzhimingxi_Activity.class);
                startActivity(intent1);
                break;
        }
    }


}
