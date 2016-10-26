package cn.gem.mydoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.sql.Timestamp;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.util.NetUtil;
import cn.gem.weight.login_edittext;

public class qianbao_yuechongzhi_Activity extends AppCompatActivity {

    @InjectView(R.id.image_qianbaochongzhi1_fanghui)
    ImageButton imageQianbaochongzhi1Fanghui;
    @InjectView(R.id.toolbar_chongzhi_toolbar)
    Toolbar toolbarChongzhiToolbar;
    @InjectView(R.id.chongzhi_button)
    Button chongzhiButton;
    @InjectView(R.id.activity_qianbao_yuechongzhi_)
    RelativeLayout activityQianbaoYuechongzhi;
    @InjectView(R.id.text_chognzhi)
    login_edittext textChognzhi;

    int userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qianbao_yuechongzhi_);
        ButterKnife.inject(this);
        Intent intent=getIntent();
        String str=intent.getStringExtra("userid");
        Log.i("1111", "onCreate: "+str);
        userid= Integer.parseInt(str);
    }

    @OnClick({R.id.image_qianbaochongzhi1_fanghui, R.id.chongzhi_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_qianbaochongzhi1_fanghui:
                finish();
                break;
            case R.id.chongzhi_button:

                initof();
                break;
        }
    }

    public void initof() {

        double usermoney= Double.parseDouble(textChognzhi.getText()+"");
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        String project="充值";
        RequestParams requestParams=new RequestParams(NetUtil.url+"user_qianbao_tianjia_servlet");

        requestParams.addQueryStringParameter("userid",userid+"");
        requestParams.addQueryStringParameter("usermoney",usermoney+"");
        requestParams.addQueryStringParameter("userdate",timestamp+"");
        requestParams.addQueryStringParameter("userproject",project);


        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Toast.makeText(qianbao_yuechongzhi_Activity.this, "充值成功", Toast.LENGTH_SHORT).show();
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
