package cn.gem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.application.MyApplication;
import cn.gem.entity.QueryThemeBean;
import cn.gem.entity1.ThemeTbl;
import cn.gem.mydoctor.R;
import cn.gem.mydoctor.ThemeDetailActivity;
import cn.gem.util.CommonAdapter;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;

/**
 * Created by panwenpeng on 2016/10/14.
 */
public class ThemeFragment extends BaseFragment {


    @InjectView(R.id.tv_ill)
    TextView tvIll;
    @InjectView(R.id.tv_baojian)
    TextView tvBaojian;
    @InjectView(R.id.tv_jijiu)
    TextView tvJijiu;
    @InjectView(R.id.tv_eat)
    TextView tvEat;
    @InjectView(R.id.tv_heart)
    TextView tvHeart;
    @InjectView(R.id.tv_sex)
    TextView tvSex;
    @InjectView(R.id.tv_nice)
    TextView tvNice;
    @InjectView(R.id.tv_zhongyi)
    TextView tvJianfei;
    @InjectView(R.id.tv_jianfei)
    TextView tvZhongyi;
    @InjectView(R.id.child)
    TextView child;
    @InjectView(R.id.ll_module)
    LinearLayout llModule;
    @InjectView(R.id.tv_theme_zuire)
    TextView tvThemeZuire;
    @InjectView(R.id.tv_theme_zuixin)
    TextView tvThemeZuixin;
    @InjectView(R.id.lv_theme)
    ListView lvTheme;
    @InjectView(R.id.ll_theme_title)
    LinearLayout llThemeTitle;
    QueryThemeBean queryThemeBean;
     int moduleId=1;
     int flag=5;
     Integer pageNo=1;
     Integer pageSize=5;
     int lookNumber;

    CommonAdapter<ThemeTbl> themeTblCommonAdapter;
    List<ThemeTbl> themeTbls = new ArrayList<ThemeTbl>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_theme, null);
        ButterKnife.inject(this, v);
        return v;
    }


    @Override
    public void initView() {
        lvTheme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getData();
                lookNumber++;
                Log.i("ThemeFragment", "ThemeFragment: onItemClick:LOOKNUMBER"+lookNumber);
                Intent  intent =new Intent(getActivity(),ThemeDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("themeTbl",themeTbls.get(position));
                bundle.putParcelable("moduleTbl",themeTbls.get(position).getModuleTbl());
                Log.i("ThemeFragment", "ThemeFragment: onItemClic：话题页面"+themeTbls.get(position).getModuleTbl());
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

    }

    @Override
    public void initEvent() {
        //

    }

    @Override
    public void initData() {
        getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_ill, R.id.tv_baojian, R.id.tv_jijiu, R.id.tv_eat, R.id.tv_heart, R.id.tv_sex, R.id.tv_nice, R.id.tv_jianfei,R.id.tv_zhongyi, R.id.child, R.id.tv_theme_zuire, R.id.tv_theme_zuixin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_ill:
                moduleId=1;
                getData();
                 break;
            case R.id.tv_baojian:
                moduleId=2;
                getData();
                break;
            case R.id.tv_jijiu:
                moduleId=3;
                getData();
                break;
            case R.id.tv_eat:
                moduleId=4;
                getData();
                break;
            case R.id.tv_heart:
                moduleId=5;
                getData();
                break;
            case R.id.tv_sex:
                moduleId=6;
                getData();
                break;
            case R.id.tv_nice:
                moduleId=7;
                getData();
                break;
            case R.id.tv_jianfei:
                moduleId=8;
                getData();
                break;
            case R.id.tv_zhongyi:
                moduleId=9;
                getData();
                break;
            case R.id.child:
                moduleId=10;
                getData();
                break;
            case R.id.tv_theme_zuire:
                flag=2;
                getData();
                break;
            case R.id.tv_theme_zuixin:
                flag=1;
                getData();
                break;
        }
    }
    public void getData(){
        //初始化数据
        //xutils获取网络数据
        String url= NetUtil.url+"QueryThemeServlet";
        RequestParams requestParams=new RequestParams(url);
        Log.i("ThemeFragment", "ThemeFragment: getData"+url);
        requestParams.addQueryStringParameter("moduleId",moduleId+"");
        requestParams.addQueryStringParameter("flag",flag+"");
        requestParams.addQueryStringParameter("pageNo",pageNo+"");
        requestParams.addQueryStringParameter("pageSize",pageSize+"");
        Log.i("ThemeFragment", "ThemeFragment: getData"+pageNo+"---"+pageSize);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //json转换成List<>
                Gson gson=new Gson();
                Type type=new TypeToken<List<ThemeTbl>>(){}.getType();
                List<ThemeTbl> newThemetbl=new ArrayList<ThemeTbl>();
                newThemetbl=gson.fromJson(result,type);
                themeTbls.clear();//清空数据
                themeTbls.addAll(newThemetbl);
                //设置listview的适配器
                if (themeTblCommonAdapter==null){
                    Log.i("ThemeFragment", "ThemeFragment: onSuccess");
                    themeTblCommonAdapter=new CommonAdapter<ThemeTbl>(getActivity(),themeTbls,R.layout.theme_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ThemeTbl themeTbl, int position) {
                            TextView tvThenmeName=viewHolder.getViewById(R.id.tv_username);
                            tvThenmeName.setText(themeTbl.getUserTbl().getUserSname());
                            TextView tvThemeName=viewHolder.getViewById(R.id.tv_forum_title);
                            tvThemeName.setText(themeTbl.getThemeName());
                            TextView tvThemeTime=viewHolder.getViewById(R.id.tv_themetime);
                            tvThemeTime.setText(themeTbl.getThemeTime()+"");
                            TextView tvThemeReadNum=viewHolder.getViewById(R.id.tv_theme_readnumber);
                            tvThemeReadNum.setText(themeTbl.getLookNumber()+"");
                            TextView tvAnswerNum=viewHolder.getViewById(R.id.tv_answertheme_number);
                            tvAnswerNum.setText(themeTbl.getAnswerNum()+"");
                        }
                    };
                    lvTheme.setAdapter(themeTblCommonAdapter);
                }else {
                    themeTblCommonAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onError(Throwable ex,boolean isOnCallback) {

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

