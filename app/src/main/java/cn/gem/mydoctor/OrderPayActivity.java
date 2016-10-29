package cn.gem.mydoctor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import c.b.BP;
import c.b.PListener;
import c.b.QListener;
import cn.gem.application.MyApplication;
import cn.gem.entity.DoctorInHospital;
import cn.gem.entity1.OrderUserDetail;
import cn.gem.util.CommonQuantity;
import cn.gem.util.NetUtil;

public class OrderPayActivity extends AppCompatActivity {

    @InjectView(R.id.order_pay_hospital_name)
    TextView orderDetailPrice;
    @InjectView(R.id.order_pay_subject_name)
    TextView orderPaySubjectName;
    @InjectView(R.id.order_pay_doctor_name)
    TextView orderPayDoctorName;
    @InjectView(R.id.order_pay_user_name)
    TextView orderPayUserName;
    @InjectView(R.id.order_pay_user_number)
    TextView orderPayUserNumber;
    @InjectView(R.id.order_pay_user_phone)
    TextView orderPayUserPhone;
    @InjectView(R.id.order_pay_user_ill)
    TextView orderPayUserIll;
    @InjectView(R.id.order_pay_money)
    TextView orderPayMoney;
    @InjectView(R.id.gopay_select_weixin)
    ImageView gopaySelectWeixin;
    @InjectView(R.id.gopay_rl_weixinpay)
    RelativeLayout gopayRlWeixinpay;
    @InjectView(R.id.gopay_pay)
    Button gopayPay;

    Toolbar toolbar;

    ProgressDialog dialog;
    DoctorInHospital doctorInHospital;
    int orderId = 0;
    float price;
    String userIllContent;
    String orderID;
    boolean isChanged = false;
    OrderUserDetail orderUserDetail;
    MyApplication myApplication= (MyApplication) getApplication();
    Intent intent;

    // 此为测试Appid,请将Appid改成你自己的Bmob AppId   3b2c117189017eecb2f443a2656e016f
    String APPID = "3deb8788a4a0035d1c6ad2454900dfe6";
    // 此为支付插件的官方最新版本号,请在更新时留意更新说明
    int PLUGINVERSION = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.order_toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.back6);
        setSupportActionBar(toolbar);

        intent = getIntent();
        //判断是那个界面传过来的值
        initData();
        //支付
        BP.init(this,APPID);
        int pluginVersion = BP.getPluginVersion();

        if (pluginVersion < PLUGINVERSION) {// 为0说明未安装支付插件, 否则就是支付插件的版本低于官方最新版
            Toast.makeText(OrderPayActivity.this,
                    pluginVersion == 0 ? "监测到本机尚未安装支付插件,无法进行支付,请先安装插件(无流量消耗)"
                            : "监测到本机的支付插件不是最新版,最好进行更新,请先更新插件(无流量消耗)", Toast.LENGTH_SHORT).show();
            installBmobPayPlugin("bp.db");
        }
        //返回上一个界面
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initData(){
        if(intent.getParcelableExtra("doctorInHospital")!=null){
        doctorInHospital = intent.getParcelableExtra("doctorInHospital");
        orderId =intent.getIntExtra("orderId", 0);
        price = intent.getFloatExtra("orderDetailPrice", 0);
        userIllContent =intent.getStringExtra("userIllContent");
            initView(1);
        }else if(intent.getParcelableExtra("orderUserDetail")!=null){
            orderUserDetail=getIntent().getParcelableExtra("orderUserDetail");

            initView(2);
        }
    }



    private void initView(int k) {

        if(k== CommonQuantity.FIRST){
        orderDetailPrice.setText(doctorInHospital.getHospitalSname());
        orderPaySubjectName.setText(doctorInHospital.getSubjectSname());
        orderPayDoctorName.setText(doctorInHospital.getDoctorsSname());
        orderPayUserName.setText(myApplication.getUserTbl1().getUserSname());
        orderPayUserNumber.setText(myApplication.getUserTbl1().getUserCard());
        orderPayUserPhone.setText(myApplication.getUserTbl1().getUserPhone());

        orderPayUserIll.setText(userIllContent);
        orderPayMoney.setText("共 ￥" + price + "元");}
        if(k==CommonQuantity.SECOND){

            orderDetailPrice.setText(orderUserDetail.getHospitalSname());
            orderPaySubjectName.setText(orderUserDetail.getDepartmentsSname());
            orderPayDoctorName.setText(orderUserDetail.getDoctorsSname());
            orderPayUserName.setText(myApplication.getUserTbl().getUserSname());
            orderPayUserNumber.setText(myApplication.getUserTbl().getUserCard());
            orderPayUserPhone.setText(myApplication.getUserTbl().getUserPhone());

            orderPayUserIll.setText(orderUserDetail.getOrderTbl().getOrderMessage());
            orderPayMoney.setText("共 ￥" + orderUserDetail.getOrderTbl().getOrderState() + "元");
        }


    }

    @OnClick({R.id.gopay_rl_weixinpay, R.id.gopay_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gopay_rl_weixinpay:

                if(!isChanged){
                    isChanged=true;
                    gopaySelectWeixin.setImageDrawable(getResources().getDrawable(R.drawable.pay_succeed));
                }else {
                    isChanged=false;
                    gopaySelectWeixin.setImageDrawable(getResources().getDrawable(R.drawable.select_all_gray));
                }

                break;
            case R.id.gopay_pay:
                if(isChanged) {
                    pay(false);
                    updateState(orderId);

                    //如果是再次预约
                    setResult(CommonQuantity.ORDEEANGIN);

                    finish();
                }
                break;
        }
    }



    void updateState(int orderId){

        String stl= NetUtil.url+"UpdateOrderServlet";
        RequestParams requestParams=new RequestParams(stl);
        requestParams.addQueryStringParameter("orderId",orderId+"");
        requestParams.addQueryStringParameter("state",2+"");
        requestParams.addQueryStringParameter("price",price+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("OrderPayActivity", "onSuccess: 更新成功！");
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

    /**
     * 调用支付
     *
     * @param alipayOrWechatPay
     *            支付类型，true为支付宝支付,false为微信支付
     */
    void pay(final boolean alipayOrWechatPay) {
        showDialog("正在获取订单...");
        final String name = getName();
        BP.pay(name, getBody(), getPrice(), alipayOrWechatPay, new PListener() {
            // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
            @Override
            public void unknow() {
                Toast.makeText(OrderPayActivity.this, "支付结果未知,请稍后手动查询", Toast.LENGTH_SHORT)
                        .show();
                hideDialog();
                Log.i("OrderPayActivity", "unknow: =====================");
            }

            // 支付成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                Toast.makeText(OrderPayActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();

                hideDialog();
            }

            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
                orderID=orderId;
                showDialog("获取订单成功!请等待跳转到支付页面~");
            }

            // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
            @Override
            public void fail(int code, String reason) {

                // 当code为-2,意味着用户中断了操作
                // code为-3意味着没有安装BmobPlugin插件
                if (code == -3) {
                    Toast.makeText(
                            OrderPayActivity.this,
                            "监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付",
                            Toast.LENGTH_LONG).show();
                    installBmobPayPlugin("bp.db");
                } else {
                    Toast.makeText(OrderPayActivity.this, "支付中断!", Toast.LENGTH_SHORT)
                            .show();
                }
                hideDialog();
            }
        });
    }

    // 执行订单查询
    void query() {
        showDialog("正在查询订单...");
        final String orderId = getOrder();

        BP.query(orderId, new QListener() {

            @Override
            public void succeed(String status) {
                Toast.makeText(OrderPayActivity.this, "查询成功!该订单状态为 : " + status,
                        Toast.LENGTH_SHORT).show();

                hideDialog();
            }

            @Override
            public void fail(int code, String reason) {
                Toast.makeText(OrderPayActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        });
    }


    // 默认为0.02
    double getPrice() {
        double price = 0.02;
        try {
            price = Double.parseDouble(this.orderPayMoney.getText().toString());
        } catch (NumberFormatException e) {
        }
        return price;
    }

    // 商品详情(可不填)
    String getName() {
        return this.orderPayUserName.getText().toString();
    }

    // 商品详情(可不填)
    String getBody() {
        return this.orderPayUserIll.getText().toString();
    }
    // 支付订单号(查询时必填)
    String getOrder() {
        return orderID;
    }

    void showDialog(String message) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(this);
                dialog.setCancelable(true);
            }
            dialog.setMessage(message);
            dialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    void hideDialog() {
        if (dialog != null && dialog.isShowing())
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
    }

    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
