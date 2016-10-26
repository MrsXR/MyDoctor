package cn.gem.mydoctor;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import cn.gem.application.MyApplication;
import cn.gem.entity.ArticleTbl;
import cn.gem.entity.ThemeTbl;
import cn.gem.fragment.BaseFragment;
import cn.gem.fragment.FindArticleFragment;
import cn.gem.fragment.FindThemeAndArticleFragment;
import cn.gem.fragment.FindThemeFragment;
import cn.gem.fragment.FindTuijianFragment;
import cn.gem.util.NetUtil;

public class FindActivity extends AppCompatActivity {

    String ArticleName;
    int flag = 5;
    Integer pageNo = 1;
    Integer pageSize = 5;
    List<String> popContents = new ArrayList<String>();
    @InjectView(R.id.tv_quxiao)
    TextView tvQuxiao;
    @InjectView(R.id.ll_xuanze)
    LinearLayout llXuanze;
    @InjectView(R.id.et_find)
    EditText etFind;
    @InjectView(R.id.rl_find)
    LinearLayout rlFind;
    @InjectView(R.id.tv_find)
    TextView tvFind;
    @InjectView(R.id.ll_dingbu)
    RelativeLayout llDingbu;
    @InjectView(R.id.tv_xuanze)
    TextView tvXuanze;
    @InjectView(R.id.fl_find_detail)
    FrameLayout flFindDetail;
    MyApplication  myApplication;
    int positionFlag = 0;//popupwindow item的  标记
    int fragmentFlag=0;//fragment的标记
    Fragment[] fragments;
    FindTuijianFragment findTuijianFragment;//推荐页面
    FindThemeFragment findThemeFragment;//搜索话题的页面
    FindArticleFragment findArticleFragment;//搜索文章 页面
    FindThemeAndArticleFragment findThemeAndArticleFragment;//搜索全部的页面
    int oldIndex;//用户看到的item
    int newIndex=0;//用户即将看到的item
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        ButterKnife.inject(this);
        popContents.add("推荐");
        popContents.add("全部");
        popContents.add("热聊");
        popContents.add("资讯");
         //初始化fragment
        findTuijianFragment=new FindTuijianFragment();
        findThemeFragment=new FindThemeFragment();
        findArticleFragment=new FindArticleFragment();
        findThemeAndArticleFragment =new FindThemeAndArticleFragment();
        //所有fragment的数组
        fragments=new Fragment []{findTuijianFragment,findThemeFragment,findArticleFragment,findThemeAndArticleFragment};
       //界面初始化第一个fragment，添加第一个fragment
        oldIndex=0;

        getSupportFragmentManager().beginTransaction().add(R.id.fl_find_detail,fragments[0]).commit();

         myApplication= (MyApplication) getApplication();

    }

    //创建popupwindow :v(点击的按钮)
        public void initPopupWindow(View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.paixusousuo_item, null);
        final PopupWindow popupWindow = new PopupWindow(view, 200, 300);

        //设置数据源
        ListView lv = (ListView) view.findViewById(R.id.lv_xuanze);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.lv_item_xuanzesousuo, popContents);
        lv.setAdapter(arrayAdapter);
        //popupwindow点击外边消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //显示在
        popupWindow.showAsDropDown(v, 0, 5);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                  if (position == 0) {
                      tvXuanze.setText("推荐");
                    positionFlag = 0;
                  }else if (position == 1) {
                      tvXuanze.setText("全部");
                      positionFlag = 1;
                  } else if (position == 2) {
                      tvXuanze.setText("热聊");
                      positionFlag = 2;
                  }else if(position == 3){
                      tvXuanze.setText("资讯");
                      positionFlag=3;
                  }
            }
        });
    }
    public void getDateFromArticle(){
        Log.i("FindActivity", "FindActivity: getDateFromArticle:获取话题网络数据成功");
        String url=NetUtil.url+"QueryArticleServlet";
        RequestParams requestParams=new RequestParams(url);
        requestParams.addQueryStringParameter("articleName",ArticleName);
        requestParams.addQueryStringParameter("flag", flag + "");
        requestParams.addQueryStringParameter("pageNo", pageNo + "");
        requestParams.addQueryStringParameter("pageSize", pageSize + "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("FindActivity", "FindActivity: onSuccess"+result);
                Gson gson=new Gson();
                Type type = new TypeToken<List<ArticleTbl>>() {}.getType();
                List<ArticleTbl> articleTbls = new ArrayList<ArticleTbl>();
                articleTbls = gson.fromJson(result, type);
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




    @OnClick({R.id.tv_quxiao, R.id.ll_xuanze, R.id.tv_find})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_quxiao:


                break;
            case R.id.ll_xuanze:
                initPopupWindow(view);
                break;
            case R.id.tv_find:
//                findName=etFind.getText().toString().trim();
//                Log.i("FindActivity", "FindActivity: onClick"+findName);
//                myApplication.setFindName(findName);

                switch (positionFlag){
                    case 0:
                        newIndex=0;
                        break;
                    case 1:
                        newIndex=3;
                        break;
                    case 2:
                        newIndex=1;
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        findThemeFragment=new FindThemeFragment();
                        fragmentTransaction.replace(R.id.fl_find_detail,findThemeFragment);
                        String value=etFind.getText().toString().trim();
                        Bundle bundle=new Bundle();
                        bundle.putString("value",value);
                        findThemeFragment.setArguments(bundle);
                        fragmentTransaction.commit();

                        break;
                    case 3:
                        newIndex=2;
                        FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();
                        findArticleFragment=new FindArticleFragment();
                        fragmentTransaction1.replace(R.id.fl_find_detail,findArticleFragment);
                        String value1=etFind.getText().toString().trim();
                        Bundle bundle1=new Bundle();
                        bundle1.putString("value1",value1);
                        findArticleFragment.setArguments(bundle1);
                        fragmentTransaction1.commit();

                        break;
                }
                break;
        }
        //switchFragment();

    }

//    public void switchFragment(){
//        FragmentTransaction transaction;
//        //如果选择的项不是当前选中项，则替换；否则不操作
//        if(newIndex!=oldIndex){
//            transaction=getSupportFragmentManager().beginTransaction();
//            transaction.hide(fragments[oldIndex]);//隐藏显示项
//            //如果选中项中没有加过，则添加
//            if (!fragments[newIndex].isAdded()){
//                transaction.add(R.id.fl_find_detail,fragments[newIndex]);
//            }
//            //显示当前选择项
//            transaction.show(fragments[newIndex]).commit();
//
//        }
//    //当前选择项变成选中项选中项
//     oldIndex=newIndex;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }




}
