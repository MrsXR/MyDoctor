package cn.gem.mydoctor;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.entity1.OrderUserDetail;
import cn.gem.util.CommonQuantity;
import cn.gem.util.IpChangeAddress;

public class OrderDetailOneActivity extends AppCompatActivity {

    @InjectView(R.id.order_lookdetail_toolbar)
    Toolbar orderLookdetailToolbar;
    @InjectView(R.id.order_lookdetail_name)
    TextView orderLookdetailName;
    @InjectView(R.id.order_lookdetail_subjectname)
    TextView orderLookdetailSubjectname;
    @InjectView(R.id.order_lookdetail_doctorname)
    TextView orderLookdetailDoctorname;
    @InjectView(R.id.order_hospital_address)
    TextView orderHospitalAddress;
    @InjectView(R.id.order_lookdetail_user_name)
    TextView orderLookdetailUserName;
    @InjectView(R.id.order_lookdetail_user_number)
    TextView orderLookdetailUserNumber;
    @InjectView(R.id.order_lookdetail_user_phone)
    TextView orderLookdetailUserPhone;
    @InjectView(R.id.order_lookdetail_user_ill)
    TextView orderLookdetailUserIll;
    @InjectView(R.id.order_lookdetail_pay_money)
    TextView orderLookdetailPayMoney;
    @InjectView(R.id.order_detail_user_cancel)
    TextView orderDetailUserCancel;
    @InjectView(R.id.order_detail_user_again)
    TextView orderDetailUserAgain;
    @InjectView(R.id.order_detail_user_cance2)
    TextView orderDetailUserCance2;


    OrderUserDetail orderUserDetail;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_one);
        ButterKnife.inject(this);

        orderUserDetail = getIntent().getParcelableExtra("orderUserDetail");
        position = getIntent().getIntExtra("position",0);
        initView();//初始化界面
    }

    private void initView() {
        orderLookdetailName.setText(orderUserDetail.getHospitalSname());
        orderLookdetailSubjectname.setText(orderUserDetail.getDepartmentsSname());
        orderLookdetailDoctorname.setText(orderUserDetail.getDoctorsSname());
        orderHospitalAddress.setText(orderUserDetail.getHospitalAddress());
        orderLookdetailUserName.setText(orderUserDetail.getUserSname());
        orderLookdetailUserNumber.setText(orderUserDetail.getUserCard());
        orderLookdetailUserPhone.setText(orderUserDetail.getOrderTbl().getUserPhone());
        orderLookdetailUserIll.setText(orderUserDetail.getOrderTbl().getOrderMessage());
        orderLookdetailPayMoney.setText(orderUserDetail.getOrderTbl().getOrderState() + "");

        Log.i("OrderDetailOneActivity", "initView: ----"+orderUserDetail.getOrderTbl().getOrderPayState());
        buttonInit(orderUserDetail.getOrderTbl().getOrderPayState());


        orderLookdetailToolbar.setTitle("");
        orderLookdetailToolbar.setNavigationIcon(R.mipmap.back6);
        setSupportActionBar(orderLookdetailToolbar);
        //返回上一个界面
        orderLookdetailToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @OnClick({R.id.order_detail_user_cancel, R.id.order_detail_user_again,R.id.order_detail_user_cance2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_detail_user_cancel:
                //取消预约
                int number = orderUserDetail.getOrderTbl().getOrderPayState();
                switch (number) {
                    case CommonQuantity.FIRST:
                        Intent intent = new Intent(OrderDetailOneActivity.this, OrderPayActivity.class);
                        intent.putExtra("orderUserDetail", orderUserDetail);
                        startActivityForResult(intent,CommonQuantity.ORDEEONE);
                        break;
                    case CommonQuantity.SECOND:
                       //链接数据库取消预约，并且在医生表预约人减一
                        dialogContent("您确定取消此次预约吗？",5);

                        break;
                    case CommonQuantity.THIRD:
                        //预约成功后，可以进行评价页面，评价结束按钮改为删除订单 和 查看评论
                        Intent intent2=new Intent(OrderDetailOneActivity.this,UserCommentDetailActivity.class);
                        intent2.putExtra("userIll",orderUserDetail.getOrderTbl().getOrderIllSname());
                        intent2.putExtra("orderId",orderUserDetail.getOrderTbl().getOrderId());//订单ID
                        intent2.putExtra("doctorId",orderUserDetail.getOrderTbl().getDoctorsId());//医生ID
                        startActivityForResult(intent2,CommonQuantity.ORDERCOMMRNT);

                        break;
                    case CommonQuantity.FOURTH:
                        //查看此订单的评论，评论可修改，可删除
                        

                        break;

                    case CommonQuantity.SIXTH:
                        //并未去看病 点击申请退款
                        break;
                }

                break;
            case R.id.order_detail_user_again:
                //再次预约
                Intent intent =new Intent(OrderDetailOneActivity.this,DoctorsActivity.class);
                startActivity(intent);
                break;
            case R.id.order_detail_user_cance2:
                //取消预约后的 可删除预约信息
                Log.i("OrderDetailOneActivity", "onClick: ");
                dialogContent("您确定删除此次预约信息吗？",7);
                break;
        }
    }

    private void dialogContent(String header,final int k){
        final Dialog dialog = new Dialog(this,R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setTitle("提示");
        dialog.setCancelable(true);

        TextView textView= (TextView) dialog.findViewById(R.id.dialog_layout_content);
        textView.setText(header);
        Button buttonE=(Button) dialog.findViewById(R.id.dialog_layout_ensure);

        buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消订单,k=5
                if(k==CommonQuantity.FIFTH){
                    cancelOrder(k);
                    reduceOrder();
                    dialog.cancel();
                    Toast.makeText(OrderDetailOneActivity.this,"取消成功！",Toast.LENGTH_SHORT).show();
                    //改变状态
                    orderDetailUserCancel.setText("预约已取消");
                    orderDetailUserCance2.setVisibility(View.VISIBLE);
                    orderDetailUserCance2.setText("删除预约");
                }else if(k==CommonQuantity.SEVENTH){
                    //删除订单,k=7
                    cancelOrder(k);
                    dialog.cancel();
                    Toast.makeText(OrderDetailOneActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                    //返回上一个界面，并刷新界面
                    Intent intent1=new Intent();
                    intent1.putExtra("back",position+"");
                    // 结果码：响应成功，RESULT_OK
                    setResult(RESULT_OK,intent1);
                    //销毁当前activity (返回键)
                    finish();
                }
            }
        });


        //取消按钮
        final Button buttonC=(Button) dialog.findViewById(R.id.dialog_layout_cancel);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }


    //取消------删除------
    private void cancelOrder(int k){
        String stl= IpChangeAddress.ipChangeAddress+"UpdateOrderStateServlet";

        RequestParams requestParams=new RequestParams(stl);
        requestParams.addQueryStringParameter("isFlag",1+"");
        requestParams.addQueryStringParameter("orderId",orderUserDetail.getOrderTbl().getOrderId()+"");
        requestParams.addQueryStringParameter("stateId",k+"");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

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
    //链接数据库取消预约，并且在医生表预约人减一
    private void reduceOrder(){
        int orderDetailId=orderUserDetail.getOrderTbl().getOrdertimeDetailId();

        String stl= IpChangeAddress.ipChangeAddress+"UpdateOrderStateServlet";
        RequestParams requestParams=new RequestParams(stl);
        requestParams.addQueryStringParameter("isFlag",2+"");
        requestParams.addQueryStringParameter("orderDetailId",orderDetailId+"");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

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

    private void buttonInit(int number) {
        switch (number) {
            case CommonQuantity.FIRST:
                orderDetailUserCancel.setText("未支付");
                break;
            case CommonQuantity.SECOND:
                orderDetailUserCancel.setText("取消预约");
                break;
            case CommonQuantity.THIRD:
                orderDetailUserCancel.setText("评论");
                orderDetailUserCance2.setVisibility(View.VISIBLE);
                orderDetailUserCance2.setText("删除预约");
                break;
            case CommonQuantity.FOURTH:
                orderDetailUserCancel.setText("查看评论");
                orderDetailUserCance2.setVisibility(View.VISIBLE);
                orderDetailUserCance2.setText("删除预约");
                break;
            case CommonQuantity.FIFTH:
                //1.未支付；2、预约到医生，取消预约；3:就医成功，可评论，4:评论成功，可删除；5、预约已取消6、申请退款7、删除订单
                orderDetailUserCancel.setText("预约已取消");
                orderDetailUserCance2.setVisibility(View.VISIBLE);
                orderDetailUserCance2.setText("删除预约");

                break;
            case CommonQuantity.SIXTH:
                orderDetailUserCancel.setText("申请退款");
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CommonQuantity.ORDEEONE){
            orderDetailUserCancel.setText("取消预约");
        }
    }
}
