
package cn.gem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import cn.gem.application.MyApplication;
import cn.gem.entity.ThemeTbl;
import cn.gem.mydoctor.FindActivity;
import cn.gem.mydoctor.R;
import cn.gem.util.CommonAdapter;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;

/**
 * Created by panwenpeng on 2016/10/17.
 */
public class FindThemeFragment extends BaseFragment {
    @InjectView(R.id.lv_find_theme)
    ListView lvFindTheme;
    int flag=2;
    Integer pageNo=1;
    Integer pageSize=5;

    CommonAdapter<ThemeTbl> commonAdapter;
    List<ThemeTbl> themeTbls=new ArrayList<ThemeTbl>();
    MyApplication myApplication;
    String themeName;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("FindThemeFragment", "FindThemeFragment: onCreateView+进入findthemefragment");
        View v = inflater.inflate(R.layout.fragment_find_theme, null);
       // myApplication= (MyApplication) getActivity().getApplication();
       // themeName=myApplication.getFindName();
       // Log.i("FindThemeFragment", "FindThemeFragment: onCreateView"+themeName);
        ButterKnife.inject(this, v);
       // getData();
       Bundle bundle = getArguments();
        if (bundle != null) {
           themeName = bundle.getString("value");
            Log.i("FindThemeFragment", "FindThemeFragment: onCreateView:theme=" + themeName);
           getData();
        }
        return v;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    public void getData(){
        String url= NetUtil.url+"QueryThemeServlet";
        RequestParams requestParams=new RequestParams(url);
        requestParams.addQueryStringParameter("themeName",themeName);
        Log.i("FindThemeFragment", "FindThemeFragment: getdata:"+themeName);
        requestParams.addQueryStringParameter("flag",flag+"");
        requestParams.addQueryStringParameter("pageNo",pageNo+"");
        requestParams.addQueryStringParameter("pageSize",pageSize+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson=new Gson();
                Type type=new TypeToken<List<ThemeTbl>>(){}.getType();
                List<ThemeTbl> newThemeTbl=new ArrayList<ThemeTbl>();
                newThemeTbl= gson.fromJson(result,type);
                if (newThemeTbl==null){
                    Toast.makeText(getActivity(), "没有搜索到数据", Toast.LENGTH_SHORT).show();
                    Log.i("FindThemeFragment", "FindThemeFragment: onSuccess:弹出提示框");
                }
                themeTbls.clear();
                themeTbls.addAll(newThemeTbl);
                if (commonAdapter==null){
                    commonAdapter=new CommonAdapter<ThemeTbl>(getActivity(),themeTbls,R.layout.theme_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ThemeTbl themeTbl, int position) {
                            TextView tvThenmeName=viewHolder.getViewById(R.id.tv_username);
                            tvThenmeName.setText(themeTbl.getUserTbl().getUserSname());
                            TextView tvThemeName=viewHolder.getViewById(R.id.tv_forum_title);
                            tvThemeName.setText(themeTbl.getThemeName());
                            //TextView tvThemeContent=viewHolder.getViewById(R.id.tv_forum_content);
                            //tvThemeContent.setText(themeTbl.getThemeContent());
                            //TextView tvModuleName=viewHolder.getViewById(R.id.tv_module);
                            //tvModuleName.setText(themeTbl.getModuleId());
                            TextView tvThemeTime=viewHolder.getViewById(R.id.tv_themetime);
                            tvThemeTime.setText(themeTbl.getThemeTime()+"");
                            TextView tvThemeReadNum=viewHolder.getViewById(R.id.tv_theme_readnumber);
                            tvThemeReadNum.setText(themeTbl.getLookNumber()+"");
                            TextView tvAnswerNum=viewHolder.getViewById(R.id.tv_answertheme_number);
                            tvAnswerNum.setText(themeTbl.getAnswerNum()+"");

                        }
                    };
                    lvFindTheme.setAdapter(commonAdapter);
                }
               commonAdapter.notifyDataSetChanged();
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

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        Log.i("FindThemeFragment", "FindThemeFragment: setUserVisibleHint");
//        if (isVisibleToUser){
//            commonAdapter=null;
//            getData();
//        }
//    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i("FindThemeFragment", "FindThemeFragment: onHiddenChanged----");
        if (hidden==false){
            commonAdapter=null;
            themeName=myApplication.getFindName();
            getData();
        }
    }
}
