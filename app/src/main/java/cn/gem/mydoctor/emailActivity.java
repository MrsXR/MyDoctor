package cn.gem.mydoctor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.util.NetUtil;
import cn.gem.weight.login_edittext;


public class emailActivity extends AppCompatActivity {


    @InjectView(R.id.toolbar2)
    Toolbar toolbar2;
    @InjectView(R.id.textView7)
    TextView textView7;
    @InjectView(R.id.edit_user_zhucei_miam)
    login_edittext editUserZhuceiMiam;
    @InjectView(R.id.button_zhucei)
    Button buttonZhucei;
    @InjectView(R.id.button_houquyanzhengma)
    Button buttonHouquyanzhengma;
    @InjectView(R.id.imageButton_fanghui)
    ImageButton imageButtonFanghui;
    @InjectView(R.id.edit_user_zhucei_zhanghao)
    login_edittext editUserZhuceiZhanghao;
    @InjectView(R.id.edit_user_zhucei_yanzheng)
    login_edittext editUserZhuceiYanzheng;
    @InjectView(R.id.activity_email)
    RelativeLayout activityEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        ButterKnife.inject(this);


    }


    @OnClick({R.id.button_zhucei, R.id.button_houquyanzhengma, R.id.imageButton_fanghui})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_zhucei:
                String name=editUserZhuceiZhanghao.getText().toString();
                String password=editUserZhuceiMiam.getText().toString();

                RequestParams requestParams=new RequestParams(NetUtil.url+"email_servlet");

                requestParams.addQueryStringParameter("name",name);
                requestParams.addQueryStringParameter("password",password);

                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(emailActivity.this, result, Toast.LENGTH_SHORT).show();

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

                break;
            case R.id.button_houquyanzhengma:
                break;
            case R.id.imageButton_fanghui:
                finish();
                break;
        }
    }
}
