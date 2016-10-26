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
import cn.gem.entity.UserTradeTbl;
import cn.gem.util.NetUtil;

public class qianbao_Activity extends AppCompatActivity {


    ImageButton imageButton;


    int num;
    double balance;

    String usertradeProject;
    String userId;

    public static final int MYREQUESECODE = 1;
    @InjectView(R.id.image_qianbao)
    ImageButton imageQianbao;
    @InjectView(R.id.toolbar_qianbao)
    Toolbar toolbarQianbao;
    @InjectView(R.id.text_qianbao_yue)
    TextView textQianbaoYue;
    @InjectView(R.id.line_qianbao_yue_liner)
    LinearLayout lineQianbaoYueLiner;
    @InjectView(R.id.text_jilu_yue)
    TextView textJiluYue;
    @InjectView(R.id.line_qianbao_jilu_liner)
    LinearLayout lineQianbaoJiluLiner;
    @InjectView(R.id.line_qianbao)
    LinearLayout lineQianbao;
    @InjectView(R.id.line_qianbao2)
    LinearLayout lineQianbao2;
    @InjectView(R.id.activity_qianbao_)
    RelativeLayout activityQianbao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("qianbao_Activity", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qianbao_);

        ButterKnife.inject(this);

        imageButton = (ImageButton) findViewById(R.id.image_qianbao);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        intoif();
    }

    @OnClick({R.id.line_qianbao_yue_liner, R.id.line_qianbao_jilu_liner})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.line_qianbao_yue_liner:
                Intent intent = new Intent(qianbao_Activity.this, qianbao_yue_Activity.class);
                intent.putExtra("values", balance + "");
                intent.putExtra("userid", userId);
                Log.i("qianbao_Activity", "onClick: " + balance);
                startActivity(intent);
                break;
            case R.id.line_qianbao_jilu_liner:
                Intent intent2 = new Intent(qianbao_Activity.this, jiaoijilu_Activity.class);
                startActivity(intent2);
                break;
        }
    }

    public void intoif() {

        Log.i("qianbao_Activity", "intoif: 111");
        RequestParams requestParams = new RequestParams(NetUtil.url + "user_qianbao_servlet");
        Log.i("qianbao_Activity", "intoif: 222");
        requestParams.addBodyParameter("userid", new NetUtil().getUser().getUserId() + "");
        Log.i("qianbao_Activity", "intoif: 333");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {


                Log.i("qianbao_Activity", "onSuccess:1111111111111 ");
                Gson gson = new Gson();
                Log.i("qianbao_Activity", "onSuccess: 222222222222");
                Type type = new TypeToken<List<UserTradeTbl>>() {
                }.getType();
                Log.i("qianbao_Activity", "onSuccess: 33333333333333");

                List<UserTradeTbl> new_list = gson.fromJson(result, type);
                Log.i("qianbao_Activity", "onSuccess: " + new_list);

                for (UserTradeTbl u : new_list) {
                    num = u.getNum();
                    balance = u.getUserBalance();
                    usertradeProject=u.getUsertradeProject();
                    userId=u.getUserId()+"";
                }
                Log.i("qianbao_Activity", "onSuccess: "+num);
                Log.i("qianbao_Activity", "onSuccess: "+balance);


                textJiluYue.setText(num + "");
                textQianbaoYue.setText(balance + "");


                Bundle bundle = new Bundle();
                bundle.getString("values", num + "");
                bundle.getString("userid",userId);

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

