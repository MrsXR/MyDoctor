package cn.gem.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import cn.gem.entity.ArticleTbl;
import cn.gem.mydoctor.R;
import cn.gem.util.CommonAdapter;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;
import cn.gem.weight.newlistview;

/**
 * Created by admin on 2016/10/7.
 */

public class BBBBB extends Fragment implements newlistview.Onlister {

    List<ArticleTbl> list = new ArrayList<ArticleTbl>();
    CommonAdapter<ArticleTbl> article_tblCommonAdapter;


    Handler handler = new Handler();
    @InjectView(R.id.shoucang_fragment_listview)
    newlistview shoucangFragmentListview;
    @InjectView(R.id.framelayout_shoucang_yisheng)
    FrameLayout framelayoutShoucangYisheng;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_newlistview, null);
        ButterKnife.inject(this, v);
        shoucangFragmentListview.setOnlister(this);
        initof();


        return v;


    }


    public void initof() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "article_tbl_shoucang_servlet");


        NetUtil netUtil = new NetUtil();
        int id = netUtil.getUser().getUserId();
        Log.i("bbbbb", "initof: " + id);
        requestParams.addQueryStringParameter("userid", id + "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();

                Type type = new TypeToken<List<ArticleTbl>>() {
                }.getType();


                List<ArticleTbl> new_list = gson.fromJson(result, type);

                list.clear();
                list.addAll(new_list);

                if (article_tblCommonAdapter == null) {
                    article_tblCommonAdapter = new CommonAdapter<ArticleTbl>(getActivity(), list, R.layout.shoucang_wenzhang_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ArticleTbl article_tbl, int position) {
                            String name = null;
                            String index = null;
                            TextView t1 = viewHolder.getViewById(R.id.shouacang_list_item_tv_biaoti);
                            t1.setText("  " + article_tbl.getArticleTitle());


                            TextView t2 = viewHolder.getViewById(R.id.shouacang_list_item_tv_renshu);
                            t2.setText("  " + article_tbl.getArticleReadnumber());
                        }
                    };

                    shoucangFragmentListview.setAdapter(article_tblCommonAdapter);
                } else {

                    article_tblCommonAdapter.notifyDataSetChanged();
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

    public static BBBBB newInstance() {

        BBBBB fragment = new BBBBB();
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void ondown() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initof();
                shoucangFragmentListview.fanghui();
            }
        }, 1000);

    }

    @Override
    public void onup() {

        get();
    }

    public void get() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "article_tbl_shoucang_servlet");


        NetUtil netUtil = new NetUtil();
        int id = netUtil.getUser().getUserId();
        Log.i("bbbbb", "initof: " + id);
        requestParams.addQueryStringParameter("userid", id + "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();

                Type type = new TypeToken<List<ArticleTbl>>() {
                }.getType();


                List<ArticleTbl> new_list = gson.fromJson(result, type);

                if (new_list.size() == list.size()) {
                    Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
                    shoucangFragmentListview.completeLoad();//没获取到数据也要改变界面
                    return;
                }

                list.addAll(new_list);

                if (article_tblCommonAdapter == null) {
                    article_tblCommonAdapter = new CommonAdapter<ArticleTbl>(getActivity(), list, R.layout.shoucang_wenzhang_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ArticleTbl article_tbl, int position) {
                            String name = null;
                            String index = null;
                            TextView t1 = viewHolder.getViewById(R.id.shouacang_list_item_tv_biaoti);
                            t1.setText("  " + article_tbl.getArticleTitle());


                            TextView t2 = viewHolder.getViewById(R.id.shouacang_list_item_tv_renshu);
                            t2.setText(" 阅读量 ： " + article_tbl.getArticleReadnumber());
                        }
                    };

                    shoucangFragmentListview.setAdapter(article_tblCommonAdapter);
                } else {

                    article_tblCommonAdapter.notifyDataSetChanged();
                }

                shoucangFragmentListview.completeLoad();

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
