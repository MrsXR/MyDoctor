package cn.gem.mydoctor;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.entity.UserTradeTbl;
import cn.gem.util.CommonAdapter;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;
import cn.gem.weight.newlistview;


public class jiaoijilu_Activity extends AppCompatActivity implements newlistview.Onlister {

    @InjectView(R.id.image_jiaoyi_fanhui)
    ImageButton imageFanhui;
    @InjectView(R.id.toolbar_yue_toolbar)
    Toolbar toolbarYueToolbar;
    @InjectView(R.id.activity_jiaoijilu_)
    RelativeLayout activityJiaoijilu;
    @InjectView(R.id.qianbao_wodejiaoyi_newlistview)
    newlistview qianbaoWodejiaoyiNewlistview;


    List<UserTradeTbl> list = new ArrayList<UserTradeTbl>();
    CommonAdapter<UserTradeTbl> usertradetblcommonadapter;

    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiaoijilu_);
        ButterKnife.inject(this);

        intoid();
        qianbaoWodejiaoyiNewlistview.setOnlister(this);
    }

    public void intoid() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "user_qianbao_shouzhimingxi_servlet");

        requestParams.addQueryStringParameter("userid",new NetUtil().getUser().getUserId()+"");

        Log.i("333", "intoid: "+new NetUtil().getUser().getUserId());
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<UserTradeTbl>>() {
                }.getType();

                List<UserTradeTbl> new_list = gson.fromJson(result, type);

                Log.i("111111111", "onSuccess: "+new_list);
                list.clear();
                list.addAll(new_list);
                if (usertradetblcommonadapter == null) {
                    usertradetblcommonadapter = new CommonAdapter<UserTradeTbl>(jiaoijilu_Activity.this, list, R.layout.yueshouzhimingxi_itme) {
                        @Override
                        public void convert(ViewHolder viewHolder, UserTradeTbl user_trade_tbl, int position) {
                            Log.i("111111111", "onSuccess: ");
                            TextView tv1 = viewHolder.getViewById(R.id.zhanghuyue_list_item_tv_mingcheng);
                            tv1.setText(" " + user_trade_tbl.getUsertradeProject());

                            TextView tv2 = viewHolder.getViewById(R.id.yue_list_item_tv_shijian);
                            tv2.setText(" " + user_trade_tbl.getUsertradeDate());


                            TextView tv3=viewHolder.getViewById(R.id.yue_list_item_tv_money);
                            tv3.setText("-"+user_trade_tbl.getUsertradePrice()+"¥");
                           // TextView tv3 = viewHolder.getViewById(R.id.yue_list_item_tv_money);
                           // tv3.setText("  - " +user_trade_tbl.getUsertradePrice()+ " ¥ ");
                           // Log.i("jiaoijilu_Activity", "convert: "+ user_trade_tbl.getUsertradePrice());
                        }
                    };
                    qianbaoWodejiaoyiNewlistview.setAdapter(usertradetblcommonadapter);
                } else {

                    usertradetblcommonadapter.notifyDataSetChanged();
                }

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

    public void get() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "user_qianbao_shouzhimingxi_servlet");

        requestParams.addQueryStringParameter("userid", new NetUtil().getUser().getUserId() + "");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<UserTradeTbl>>() {
                }.getType();

                List<UserTradeTbl> new_list = gson.fromJson(result, type);

                if(new_list.size()==list.size()){
                    Toast.makeText(jiaoijilu_Activity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                    qianbaoWodejiaoyiNewlistview.completeLoad();
                    return;
                }

                list.addAll(new_list);
                if (usertradetblcommonadapter == null) {
                    usertradetblcommonadapter = new CommonAdapter<UserTradeTbl>(jiaoijilu_Activity.this, list, R.layout.yueshouzhimingxi_itme) {
                        @Override
                        public void convert(ViewHolder viewHolder, UserTradeTbl user_trade_tbl, int position) {

                            TextView tv1 = viewHolder.getViewById(R.id.zhanghuyue_list_item_tv_mingcheng);
                            tv1.setText(" " + user_trade_tbl.getUsertradeProject());

                            TextView tv2 = viewHolder.getViewById(R.id.yue_list_item_tv_shijian);
                            tv2.setText(" " + user_trade_tbl.getUsertradeDate());

                            TextView tv3 = viewHolder.getViewById(R.id.yue_list_item_tv_money);
                            tv3.setText("  - " + user_trade_tbl.getUsertradePrice() + " ¥ ");

                        }
                    };
                    qianbaoWodejiaoyiNewlistview.setAdapter(usertradetblcommonadapter);
                } else {

                    usertradetblcommonadapter.notifyDataSetChanged();
                }
                qianbaoWodejiaoyiNewlistview.completeLoad();
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



    @OnClick(R.id.image_jiaoyi_fanhui)
    public void onClick() {
          finish();
    }

    @Override
    public void ondown() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intoid();
                qianbaoWodejiaoyiNewlistview.fanghui();
            }
        },1000);
    }

    @Override
    public void onup() {

        get();
    }
}
