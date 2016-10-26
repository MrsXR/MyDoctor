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
import cn.gem.entity.ThemeTbl;
import cn.gem.mydoctor.R;
import cn.gem.util.CommonAdapter;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;
import cn.gem.weight.newlistview;

/**
 * Created by admin on 2016/10/7.
 */

public class CCCCC extends Fragment implements newlistview.Onlister {


    List<ThemeTbl> list = new ArrayList<ThemeTbl>();

    Handler handler = new Handler();

    CommonAdapter<ThemeTbl> theme_tblCommonAdapter;
    @InjectView(R.id.shoucang_fragment_listview)
    newlistview shoucangFragmentListview;
    @InjectView(R.id.framelayout_shoucang_yisheng)
    FrameLayout framelayoutShoucangYisheng;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_newlistview, null);
        ButterKnife.inject(this, v);
        initof();
        shoucangFragmentListview.setOnlister(this);
        Log.i("CCCCC", "onCreateView: 111111111111");
        return v;

    }

    public void initof() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "theme_tbl_shoucang_servlet");

        int userid = new NetUtil().getUser().getUserId();
        Log.i("CCCCC", "initof: " + userid);
        requestParams.addQueryStringParameter("userid", userid + "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();

                Type type = new TypeToken<List<ThemeTbl>>() {
                }.getType();
                List<ThemeTbl> new_list = new ArrayList<ThemeTbl>();
                Log.i("CCCCC", "onSuccess: " + new_list);
                new_list = gson.fromJson(result, type);

                list.clear();
                list.addAll(new_list);
                if (theme_tblCommonAdapter == null) {
                    theme_tblCommonAdapter = new CommonAdapter<ThemeTbl>(getActivity(), list, R.layout.shoucang_huati_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ThemeTbl theme_tbl, int position) {

                            TextView t1 = viewHolder.getViewById(R.id.shouacang_list_item_tv_huati_mokuai);
                            t1.setText("  " + theme_tbl.getModuleSname());
                            Log.i("CCCCC", "convert: 44444444444");
                            TextView t2 = viewHolder.getViewById(R.id.shouacang_list_item_huati_tieziming);
                            t2.setText("  " + theme_tbl.getThemeName());

                            TextView t3 = viewHolder.getViewById(R.id.shouacang_list_item_huati_fatieren);
                            t3.setText("  " + theme_tbl.getUserSname());

                            TextView t4 = viewHolder.getViewById(R.id.shouacang_list_item_huati_shijian);
                            t4.setText("  " + theme_tbl.getThemeTime());

                            TextView t5 = viewHolder.getViewById(R.id.shouacang_list_item_theme_renshu);
                            t5.setText(" 浏览量： " + theme_tbl.getLookNum());

                        }

                    };
                    shoucangFragmentListview.setAdapter(theme_tblCommonAdapter);

                } else {
                    theme_tblCommonAdapter.notifyDataSetChanged();

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
        final RequestParams requestParams = new RequestParams(NetUtil.url + "theme_tbl_shoucang_servlet");

        int userid = new NetUtil().getUser().getUserId();
        requestParams.addQueryStringParameter("userid", userid + "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();

                Type type = new TypeToken<List<ThemeTbl>>() {
                }.getType();
                List<ThemeTbl> new_list = new ArrayList<ThemeTbl>();

                new_list = gson.fromJson(result, type);

                if (new_list.size() == list.size()) {
                    Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
                    shoucangFragmentListview.completeLoad();//没获取到数据也要改变界面
                    return;

                }

                list.addAll(new_list);
                if (theme_tblCommonAdapter == null) {
                    theme_tblCommonAdapter = new CommonAdapter<ThemeTbl>(getActivity(), list, R.layout.shoucang_huati_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ThemeTbl theme_tbl, int position) {

                            TextView t1 = viewHolder.getViewById(R.id.shouacang_list_item_tv_huati_mokuai);
                            t1.setText("  " + theme_tbl.getModuleSname());

                            TextView t2 = viewHolder.getViewById(R.id.shouacang_list_item_huati_tieziming);
                            t2.setText("  " + theme_tbl.getThemeName());

                            TextView t3 = viewHolder.getViewById(R.id.shouacang_list_item_huati_fatieren);
                            t3.setText("  " + theme_tbl.getUserSname());

                            TextView t4 = viewHolder.getViewById(R.id.shouacang_list_item_huati_shijian);
                            t4.setText("  " + theme_tbl.getThemeTime());

                            TextView t5 = viewHolder.getViewById(R.id.shouacang_list_item_theme_renshu);
                            t5.setText(" 浏览量： " + theme_tbl.getLookNum());

                        }

                    };
                    shoucangFragmentListview.setAdapter(theme_tblCommonAdapter);

                } else {
                    theme_tblCommonAdapter.notifyDataSetChanged();

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


    public static CCCCC newInstance() {

        CCCCC fragment = new CCCCC();
        return fragment;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
