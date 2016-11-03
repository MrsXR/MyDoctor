package cn.gem.mydoctor;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.sql.Timestamp;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.application.MyApplication;
import cn.gem.entity.CommentOrderDetailTbl;
import cn.gem.util.CommonQuantity;
import cn.gem.util.IpChangeAddress;
import cn.gem.util.MaxLengthWatcher;
import cn.gem.weight.MyReflashList;

public class UserCommentDetailActivity extends AppCompatActivity {

    @InjectView(R.id.user_comment_detail_toolbar)
    Toolbar userCommentDetailToolbar;
    @InjectView(R.id.user_comment_detail_edittext)
    EditText userCommentDetailEdittext;
    @InjectView(R.id.user_comment_detail_number)
    TextView userCommentDetailNumber;
    @InjectView(R.id.user_comment_detail_attitude)
    RatingBar userCommentDetailAttitude;
    @InjectView(R.id.user_comment_detail_treat)
    RatingBar userCommentDetailTreat;
    @InjectView(R.id.user_comment_detail_comment)
    RatingBar userCommentDetailComment;
    @InjectView(R.id.user_comment_detail_radioButton)
    CheckBox userCommentDetailRadioButton;
    @InjectView(R.id.user_comment_detail_publish)
    Button userCommentDetailPublish;

    boolean isKeep=false;
    int orderId;
    int doctorId;
    String userComment=null;
    int userAttitude=0;
    int userTreat=0;
    int userCommentNumber=0;
    String userIllSname=null;
    String userSname=null;
    MyApplication myApplication;
    CommentOrderDetailTbl commentOrderDetailTbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_comment_detail);
        ButterKnife.inject(this);

        //toolbar的点击事件
        userCommentDetailToolbar.setTitle("");
        userCommentDetailToolbar.setNavigationIcon(R.mipmap.back6);
        userCommentDetailToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isBack();
            }
        });

        Intent intent=getIntent();
        userIllSname=intent.getStringExtra("userIll");
        orderId=intent.getIntExtra("orderId",0);
        doctorId=intent.getIntExtra("doctorId",0);
        //输入文字监听事件
        userCommentDetailEdittext.addTextChangedListener(new MaxLengthWatcher(300,userCommentDetailNumber,this));


        //医生态度
        userCommentDetailAttitude.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                userAttitude= (int) rating;
            }
        });
        //治疗效果
        userCommentDetailTreat.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                userTreat= (int) rating;
            }
        });
        //医生
        userCommentDetailComment.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                userCommentNumber= (int) rating;
            }
        });


    }

    private void initData(){
        myApplication= (MyApplication) getApplication();
        //用户输入的信息
        userComment= userCommentDetailEdittext.getText().toString();

        if(userCommentDetailRadioButton.isChecked()){
            userSname=myApplication.getUserTbl1().getUserSname().substring(0,1).trim()+"***";
        }else {
            userSname=myApplication.getUserTbl1().getUserSname();
        }

        Timestamp d = new Timestamp(System.currentTimeMillis());
        commentOrderDetailTbl=new CommentOrderDetailTbl(orderId,doctorId,myApplication.getUserTbl1().getUserId(),userComment,
                userCommentNumber,userAttitude,userTreat,d,userIllSname,userSname);

        sendData();//插入数据

    }

    @OnClick(R.id.user_comment_detail_publish)
    public void onClick() {
        if(!userCommentDetailEdittext.getText().toString().equals("")){
            initData();
        }else {
            Toast.makeText(UserCommentDetailActivity.this, "请填写评价内容！", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendData(){
        String stl= IpChangeAddress.ipChangeAddress+"SetOrderDetailServlet";
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
        String data=gson.toJson(commentOrderDetailTbl);

        RequestParams requestParams=new RequestParams(stl);
        requestParams.addBodyParameter("data",data);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(UserCommentDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                setResult(CommonQuantity.ORDERCOMMRNT);
                isKeep=true;
                finish();
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);//true对任何Activity都适用
            isBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void isBack(){

        if(isKeep==false){
            if(!userCommentDetailEdittext.getText().toString().equals("")) {
                final Dialog dialog = new Dialog(this, R.style.CustomDialog);
                dialog.setContentView(R.layout.dialog_layout);
                dialog.setTitle("提示");
                dialog.setCancelable(true);

                TextView textView = (TextView) dialog.findViewById(R.id.dialog_layout_content);
                textView.setText("信息尚未发布，确定退出吗？");
                Button buttonE = (Button) dialog.findViewById(R.id.dialog_layout_ensure);

                buttonE.setText("确定");
                dialog.show();
                buttonE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        finish();
                    }
                });

                //取消按钮
                final Button buttonC = (Button) dialog.findViewById(R.id.dialog_layout_cancel);
                buttonC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }else if(userCommentDetailEdittext.getText().toString().equals("")){
                finish();
            }
        }
    }



}
