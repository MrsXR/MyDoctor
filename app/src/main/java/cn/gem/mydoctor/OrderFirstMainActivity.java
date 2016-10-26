package cn.gem.mydoctor;
/**
 * 预约界面---------医院选择界面
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.entity.HospitalTbl;
import cn.gem.util.IpChangeAddress;
import cn.gem.util.OrderFirstBaseAdapter;
import feign.Response;


public class OrderFirstMainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView orderlistView;

    //RequestQueue是一个请求队列对象
    RequestQueue requestQueue;

    List<HospitalTbl> hospitalTbls = null;
    String stlurlget = null;
    @InjectView(R.id.address_name)
    TextView addressName;
    @InjectView(R.id.order_imageButton)
    ImageButton orderImageButton;
    @InjectView(R.id.order_imageButton_down)
    ImageButton orderImageButtonDown;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_main);
        ButterKnife.inject(this);

        requestQueue = Volley.newRequestQueue(this);
        toolbar = (Toolbar) findViewById(R.id.order_toolbar);
        orderlistView = (ListView) findViewById(R.id.order_first_listView);

        //设置默认的选择地址
        stlurlget = IpChangeAddress.ipChangeAddress + "HospitalTblServlet?cityName=苏州市";
        addressName.setText("苏州市");
        getVolley(stlurlget, this);

    }

    //必须重写onNewIntent才能更新界面
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String cityName = getIntent().getStringExtra("cityName");
        if (cityName != null) {
            stlurlget = IpChangeAddress.ipChangeAddress + "HospitalTblServlet?cityName=" + cityName;
            addressName.setText(cityName);
            getVolley(stlurlget, this);
        }
    }

    //使用volley中的get方法
    public void getVolley(final String utl, final Context context) {
        //c创建一个StringRequest的对象
        StringRequest stringRequest = new StringRequest(utl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                hospitalTbls = gson.fromJson(response, new TypeToken<List<HospitalTbl>>(){}.getType());
                Log.i("backmessage", "-------->" + hospitalTbls);
                if (hospitalTbls != null) {
                    //将集合传给ListView
                    OrderFirstBaseAdapter orderFirstBaseAdapter = new OrderFirstBaseAdapter(context, hospitalTbls);
                    orderlistView.setAdapter(orderFirstBaseAdapter);

                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

     //按钮点击事件：1：返回上一级； 2：选择城市
    @OnClick({R.id.order_imageButton, R.id.order_imageButton_down})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_imageButton:
               finish();
                //跳转至主页
                break;
            case R.id.order_imageButton_down:
                intent=new Intent(this,ChangeCityActivity.class);
                startActivity(intent);
                break;
        }
    }
}
