package cn.gem.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import cn.gem.entity.DoctorsTbl;
import cn.gem.mydoctor.DoctorsActivity;
import cn.gem.mydoctor.R;
import cn.gem.util.CommonAdapter;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;
import cn.gem.weight.newlistview;

/**
 * Created by admin on 2016/10/7.
 */

public class AAAAA extends Fragment implements newlistview.Onlister {

    List<DoctorsTbl> list = new ArrayList<DoctorsTbl>();
    CommonAdapter<DoctorsTbl> doctors_commomadapter;


    Handler handler = new Handler();
    @InjectView(R.id.shoucang_fragment_listview)
    newlistview shoucangFragmentListview;
    @InjectView(R.id.framelayout_shoucang_yisheng)
    FrameLayout framelayoutShoucangYisheng;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_newlistview, null);
        Log.i("11111", "onCreateView: ");
        ButterKnife.inject(this, v);
        shoucangFragmentListview.setOnlister(this);
        return v;
    }

    @Override
    public void onStart() {

        initof();
        super.onStart();
    }

    public void initof() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "doctors_tbl_shoucang_servlet");
        NetUtil netUtil = new NetUtil();
        int id = netUtil.getUser().getUserId();
        requestParams.addQueryStringParameter("userid", id + "");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<DoctorsTbl>>() {
                }.getType();

                List<DoctorsTbl> new_list = gson.fromJson(result, type);

                list.clear();
                list.addAll(new_list);
                if (doctors_commomadapter == null) {
                    doctors_commomadapter = new CommonAdapter<DoctorsTbl>(getActivity(), list, R.layout.shoucang_yisheng_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, DoctorsTbl doctor_tbl, int position) {
                            TextView tv1 = viewHolder.getViewById(R.id.shouacang_list_item_tv_xingming);
                            tv1.setText(doctor_tbl.getDoctorsSname());//商品名称
                            Log.i("66666", "convert: " + doctor_tbl.getDoctorsSname());
                            String name = null;
                            switch (doctor_tbl.getDoctorsPosition()) {
                                case 1:
                                    name = "主任医师";
                                    break;
                                case 2:
                                    name = "副主任医师";
                                    break;
                                case 3:
                                    name = "主治医师";
                                    break;
                                case 4:
                                    name = "普通医师";
                                    break;
                            }
                            TextView tv2 = viewHolder.getViewById(R.id.shouacang_list_item_tv_zhiwu);
                            tv2.setText(" " + name);

                            TextView tv3 = viewHolder.getViewById(R.id.shouacang_list_item_tv_keshi);
                            tv3.setText(" " + doctor_tbl.getDoctorsSname());

                            TextView tv4 = viewHolder.getViewById(R.id.shouacang_list_item_tv_yiyuan);
                            tv4.setText(" " + doctor_tbl.getHospitalSname());


                        }
                    };
                    shoucangFragmentListview.setAdapter(doctors_commomadapter);
                } else {
                    doctors_commomadapter.notifyDataSetChanged();
                }

                //点击事件
                shoucangFragmentListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if(newlistview.isTag()==false&&position!=0&&position<=list.size()) {
                        Intent intent = new Intent(getContext(), DoctorsActivity.class);
                        intent.putExtra("doctorsId",list.get(position-1).getDoctorsId());
                            Log.i("AAAAA", "onItemClick: ----"+list.get(position-1).getDoctorsId());
                        startActivity(intent);
                        }
                    }
                });


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

    public static AAAAA newInstance() {
        AAAAA fragment = new AAAAA();
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @Override
    public void ondown() {
        Log.i("jjjj", "ondown: ");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initof();
                shoucangFragmentListview.fanghui();
                Log.i("AAAAA", "run: =============");
            }
        }, 1000);
    }

    @Override
    public void onup() {

        get();


    }

    public void get() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "doctors_tbl_shoucang_servlet");
        Log.i("222222", "onCreateView: ");
        NetUtil netUtil = new NetUtil();
        int id = netUtil.getUser().getUserId();
        Log.i("aaaa", "initof: " + id);
        requestParams.addQueryStringParameter("userid", id + "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Log.i("333333", "onCreateView: ");
                Type type = new TypeToken<List<DoctorsTbl>>() {
                }.getType();
                List<DoctorsTbl> new_list = gson.fromJson(result, type);

                if (new_list.size() == list.size()) {//服务器没有返回新的数据
                    //下一次继续加载这一页
                    Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
                    shoucangFragmentListview.completeLoad();//没获取到数据也要改变界面
                    return;
                }
                list.addAll(new_list);
                Log.i("444444", "onSuccess: " + list);
                if (doctors_commomadapter == null) {
                    Log.i("5555", "onSuccess: ");
                    doctors_commomadapter = new CommonAdapter<DoctorsTbl>(getActivity(), list, R.layout.shoucang_yisheng_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, DoctorsTbl doctor_tbl, int position) {
                            TextView tv1 = viewHolder.getViewById(R.id.shouacang_list_item_tv_xingming);
                            tv1.setText(doctor_tbl.getDoctorsSname());//商品名称
                            Log.i("66666", "convert: " + doctor_tbl.getDoctorsSname());
                            String name = null;
                            switch (doctor_tbl.getDoctorsPosition()) {
                                case 1:
                                    name = "主任医师";
                                    break;
                                case 2:
                                    name = "副主任医师";
                                    break;
                                case 3:
                                    name = "主治医师";
                                    break;
                                case 4:
                                    name = "普通医师";
                                    break;
                            }
                            TextView tv2 = viewHolder.getViewById(R.id.shouacang_list_item_tv_zhiwu);
                            tv2.setText(" " + name);

                            TextView tv3 = viewHolder.getViewById(R.id.shouacang_list_item_tv_keshi);
                            tv3.setText(" " + doctor_tbl.getDepartmentsSname());

                            TextView tv4 = viewHolder.getViewById(R.id.shouacang_list_item_tv_yiyuan);
                            tv4.setText(" " + doctor_tbl.getHospitalSname());


                        }
                    };
                    shoucangFragmentListview.setAdapter(doctors_commomadapter);
                } else {
                    doctors_commomadapter.notifyDataSetChanged();
                }
                shoucangFragmentListview.completeLoad();
            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), "无法获取网络数据，请检查网络连接", Toast.LENGTH_SHORT).show();
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
