package cn.gem.mydoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

    int number=0;
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
                finish();
            }
        });

        Intent intent=getIntent();
        userIllSname=intent.getStringExtra("userIll");
        orderId=intent.getIntExtra("orderId",0);
        doctorId=intent.getIntExtra("doctorId",0);
        //输入文字监听事件
        userCommentDetailEdittext.addTextChangedListener(new MaxLengthWatcher(300,userCommentDetailNumber,this));

    }

    private void initData(){
        myApplication= (MyApplication) getApplication();
        //用户输入的信息
        userComment= userCommentDetailEdittext.getText().toString();
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
        initData();
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

}
