package cn.gem.mydoctor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.gem.entity.CommentOrderDetailTbl;
import cn.gem.util.CommonAdapter;
import cn.gem.util.IpChangeAddress;
import cn.gem.util.ViewHolder;
import cn.gem.weight.MyReflashList;

public class MangCommentActivity extends AppCompatActivity implements MyReflashList.OnRefreshUploadChangeListener{

    @InjectView(R.id.mang_comment_number)
    TextView mangCommentNumber;
    @InjectView(R.id.many_comment_toolbar)
    Toolbar manyCommentToolbar;
    @InjectView(R.id.many_comment_myreflashlist)
    MyReflashList manyCommentMyreflashlist;

    TextView userSname;
    TextView userTime;
    TextView illSname;
    RatingBar  attitudeNumber;
    RatingBar treatNumber;
    RatingBar commentNumber;
    TextView commentText;



    CommonAdapter<CommentOrderDetailTbl> commonA;
    int doctorId=0;
    int pageNo=1;
    int pageSize=5;
    List<CommentOrderDetailTbl> comments;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mang_comment);
        ButterKnife.inject(this);
        comments=new ArrayList<>();
        //toolbar的点击事件
        manyCommentToolbar.setTitle("");
        manyCommentToolbar.setNavigationIcon(R.mipmap.back6);
        manyCommentToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //实现自定义ListView里的OnRefreshUploadChangeListener接口
        manyCommentMyreflashlist.setOnRefreshUploadChangeListener(this);

       Intent intent=getIntent();
        doctorId=intent.getIntExtra("doctotId",0);
        getListData();
    }


    private void getListData(){
        String stl= IpChangeAddress.ipChangeAddress+"GetOrderDetailTblServlet";

        RequestParams requestParams=new RequestParams(stl);
        requestParams.addQueryStringParameter("flag",0+"");
        requestParams.addQueryStringParameter("lookId",doctorId+"");
        requestParams.addQueryStringParameter("pageNo",pageNo+"");
        requestParams.addQueryStringParameter("pageSize",pageSize+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                List<CommentOrderDetailTbl> listNew =new ArrayList<CommentOrderDetailTbl>();

                Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
                Type type=new TypeToken< List<CommentOrderDetailTbl>>(){}.getType();
                listNew=gson.fromJson(result,type);
                comments.clear();
                comments.addAll(listNew);

                if(commonA==null) {
                    commonA = new CommonAdapter<CommentOrderDetailTbl>(MangCommentActivity.this, comments, R.layout.many_comment_list) {
                        @Override
                        public void convert(ViewHolder viewHolder, CommentOrderDetailTbl commentOrderDetailTbl, int position) {
                            inintView(viewHolder, commentOrderDetailTbl);
                        }
                    };
                    manyCommentMyreflashlist.setAdapter(commonA);
                }else {
                    commonA.notifyDataSetChanged();
                }

                manyCommentMyreflashlist.completeLoad();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MangCommentActivity.this, "无法获取网络数据，请检查网络连接", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    //空间的赋值
    public void inintView(ViewHolder viewHolder,CommentOrderDetailTbl commentOrderDetailTbl){
        userSname=viewHolder.getViewById(R.id.many_comment_username);
        userSname.setText(commentOrderDetailTbl.getUserSname());

        userTime=viewHolder.getViewById(R.id.many_comment_time);
        userTime.setText(commentOrderDetailTbl.getCommentOrderDetailTime().toString());

        illSname=viewHolder.getViewById(R.id.mang_comment_user_ill);
        illSname.setText(commentOrderDetailTbl.getOrderIllSname());

        attitudeNumber=viewHolder.getViewById(R.id.many_comment_list_attitude);
        attitudeNumber.setRating(commentOrderDetailTbl.getCommentOrderDetailAttitude());
        treatNumber=viewHolder.getViewById(R.id.many_comment_list_treat);
        treatNumber.setRating(commentOrderDetailTbl.getCommentOrderDetailTreat());
        commentNumber=viewHolder.getViewById(R.id.many_comment_list_comment);
        commentNumber.setRating(commentOrderDetailTbl.getCommentOrderDetailType());

        commentText=viewHolder.getViewById(R.id.many_comment_list_consult);
        commentText.setText(commentOrderDetailTbl.getCommentOrderDetailContent());

    }

    @Override
    public void onRefresh() {
        pageNo = 1; //每次刷新，让pageNo变成初始值1
        //1秒后发一个消息
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getListData();  //再次获取数据
                manyCommentMyreflashlist.completeRefresh();  //刷新数据后，改变页面为初始页面：隐藏头部
            }
        },1000);
    }

    @Override
    public void onPull() {
        pageNo++;
        //原来数据基础上增加
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMoreData();
            }
        },1000);
    }


    private void loadMoreData(){
        String stl= IpChangeAddress.ipChangeAddress+"GetOrderDetailTblServlet";
        RequestParams requestParams=new RequestParams(stl);
        requestParams.addQueryStringParameter("flag",0+"");
        requestParams.addQueryStringParameter("lookId",doctorId+"");
        requestParams.addQueryStringParameter("pageNo",pageNo+"");
        requestParams.addQueryStringParameter("pageSize",pageSize+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                List<CommentOrderDetailTbl> listNew =new ArrayList<CommentOrderDetailTbl>();

                Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
                Type type=new TypeToken< List<CommentOrderDetailTbl>>(){}.getType();
                listNew=gson.fromJson(result,type);

                if(listNew.size()==0){//服务器没有返回新的数据
                    Log.i("MangCommentActivity", "onSuccess: -------------");
                    pageNo--; //下一次继续加载这一页
                    Toast.makeText(MangCommentActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                    manyCommentMyreflashlist.completeLoad();//没获取到数据也要改变界面
                    return;
                }

                //不需要clear
                comments.addAll(listNew);

                if(commonA==null) {
                    commonA = new CommonAdapter<CommentOrderDetailTbl>(MangCommentActivity.this, comments, R.layout.many_comment_list) {
                        @Override
                        public void convert(ViewHolder viewHolder, CommentOrderDetailTbl commentOrderDetailTbl, int position) {
                            inintView(viewHolder, commentOrderDetailTbl);
                        }
                    };
                    manyCommentMyreflashlist.setAdapter(commonA);
                }else {
                    commonA.notifyDataSetChanged();
                }

                manyCommentMyreflashlist.completeLoad();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MangCommentActivity.this, "无法获取网络数据，请检查网络连接", Toast.LENGTH_SHORT).show();
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
