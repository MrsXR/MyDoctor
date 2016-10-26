package cn.gem.mydoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.entity.UserTbl;
import cn.gem.util.NetUtil;
import cn.gem.weight.login_edittext;

public class login_mydoctorActivity extends AppCompatActivity {



    List list = null;
    @InjectView(R.id.button_shoujichucei)
    Button buttonShoujichucei;
    @InjectView(R.id.button_denglu)
    Button buttonDenglu;
    @InjectView(R.id.edit_user1_mima)
    login_edittext editUser1Mima;
    @InjectView(R.id.edit_user_zhanghao)
    login_edittext editUserZhanghao;
    @InjectView(R.id.imageView2)
    ImageView imageView2;
    @InjectView(R.id.button_zhaohuimima)
    Button buttonZhaohuimima;
    @InjectView(R.id.toolbar4)
    Toolbar toolbar4;
    @InjectView(R.id.imageButton6)
    ImageButton imageButton6;
    @InjectView(R.id.textView9)
    TextView textView9;
    @InjectView(R.id.activity_login_mydoctor)
    RelativeLayout activityLoginMydoctor;



    String pw;
    String ui;
    String n;

    public String getN() {
        return n;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mydoctor);
        ButterKnife.inject(this);


    }

    @OnClick({R.id.button_shoujichucei, R.id.button_denglu, R.id.button_zhaohuimima, R.id.imageButton6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_shoujichucei:
                final Intent intent=new Intent(this,emailActivity.class);
                startActivity(intent);
                break;
            case R.id.button_denglu:
                RequestParams requestParams=new RequestParams(NetUtil.url+"login_servlet");
                String name=editUserZhanghao.getText().toString();
                String password=editUser1Mima.getText().toString();

                requestParams.addQueryStringParameter("name",name);
                requestParams.addQueryStringParameter("password",password);

                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        String string;
                        if(result!=null){
                            string="登录成功";
                        }else {
                            string="登录失败";
                        }
                        Toast.makeText(login_mydoctorActivity.this, string, Toast.LENGTH_SHORT).show();
                        Gson gson=new Gson();
                        Type type=new TypeToken<UserTbl>(){}.getType();
                        UserTbl userTbl=gson.fromJson(result,type);
                        pw=userTbl.getUserPassword();
                        ui=userTbl.getUserId()+"";
                        n=userTbl.getUserLoginAccount();
                        Log.i("login_mydoctorActivity", "onSuccess: "+pw);
                        Log.i("login_mydoctorActivity", "onSuccess: "+ui);
                        Log.i("login_mydoctorActivity", "onSuccess: "+n);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                        //Toast.makeText(login_mydoctorActivity.this, "当前无网络连接", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });


                finish();
                break;
            case R.id.button_zhaohuimima:
                break;
            case R.id.imageButton6:
                finish();
                break;
        }
    }
}
