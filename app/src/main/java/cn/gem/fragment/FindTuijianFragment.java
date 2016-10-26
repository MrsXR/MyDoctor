package cn.gem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import cn.gem.entity.ArticleTbl;
import cn.gem.entity.ThemeTbl;
import cn.gem.mydoctor.R;
import cn.gem.util.CommonAdapter;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;

/**
 * Created by panwenpeng on 2016/10/17.
 */
public class FindTuijianFragment extends BaseFragment {


    CommonAdapter<ThemeTbl> themeTblCommonAdapter;
    CommonAdapter<ArticleTbl> articleTblCommonAdapter;
    List<ThemeTbl> themeTbls = new ArrayList<ThemeTbl>();
    List<ArticleTbl> articleTbls = new ArrayList<ArticleTbl>();
    List<String> listTheme = new ArrayList<String>();//存放帖子信息
    List<String> listArticle = new ArrayList<String>();//存放文章心虚
    int flag =2;//排序标记
    Integer pageNo=1;//
    Integer pageSize=2;//一个模块显示三条记录，查找推荐页

    ListView lvFindarticle;
    ListView lvFindtheme;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_tuijian, null);
        Log.i("FindTuijianFragment", "FindTuijianFragment: onCreateView");
        //ButterKnife.inject(this, v);
        //bug：用插件闪退，自己找控件赋值两个也闪退，赋值一个可以
        //lvFindarticle= (ListView) v.findViewById(R.id.lv_findarticle);
        lvFindtheme= (ListView) v.findViewById(R.id.lv_findtheme);
        getDateFromTheme();
        getdataFromArticle();
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
    //获取话题数据
    public void getDateFromTheme() {
        Log.i("FindTuijianFragment", "FindTuijianFragment: getDate:获取话题网络成功");
        String url = NetUtil.url + "QueryThemeServlet";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("flag", flag + "");//排序标记
        requestParams.addQueryStringParameter("pageNo",pageNo+"");
        requestParams.addQueryStringParameter("pageSize",pageSize+"");
        Log.i("FindTuijianFragment", "FindTuijianFragment: getDateFromTheme"+pageNo+"---"+pageSize);
        Log.i("FindTuijianFragment", "FindTuijianFragment: getDateFromTheme" + requestParams);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<ThemeTbl>>() {
                }.getType();
                List<ThemeTbl> newThemeTbl = new ArrayList<ThemeTbl>();
                newThemeTbl = gson.fromJson(result, type);
                Log.i("FindTuijianFragment", "获取话题: onSuccess"+newThemeTbl);
                themeTbls.clear();
                themeTbls.addAll(newThemeTbl);
                if (themeTblCommonAdapter == null) {
                    Log.i("FindTuijianFragment", "FindTuijianFragment: onSuccess:进入if方法");
                    themeTblCommonAdapter = new CommonAdapter<ThemeTbl>(getActivity(), themeTbls, R.layout.theme_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ThemeTbl themeTbl, int position) {
                            TextView tvThenmeName = viewHolder.getViewById(R.id.tv_username);
                            tvThenmeName.setText(themeTbl.getUserTbl().getUserSname());
                            Log.i("FindTuijianFragment", "FindTuijianFragment: "+tvThenmeName);
                            TextView tvThemeName = viewHolder.getViewById(R.id.tv_forum_title);
                            tvThemeName.setText(themeTbl.getThemeName());
                            TextView tvThemeTime = viewHolder.getViewById(R.id.tv_themetime);
                            tvThemeTime.setText(themeTbl.getThemeTime() + "");
                            TextView tvThemeReadNum = viewHolder.getViewById(R.id.tv_theme_readnumber);
                            tvThemeReadNum.setText(themeTbl.getLookNumber() + "");
                            TextView tvAnswerNum = viewHolder.getViewById(R.id.tv_answertheme_number);
                            tvAnswerNum.setText(themeTbl.getAnswerNum() + "");
                        }
                    };
                    lvFindtheme.setAdapter(themeTblCommonAdapter);
                } else {
                    themeTblCommonAdapter.notifyDataSetChanged();
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

    public void getdataFromArticle() {
        Log.i("FindTuijianFragment", "FindTuijianFragment: getdataFromArticle：获取文章网络成功");
        String url = NetUtil.url + "QueryArticleServlet";
        RequestParams requestParams = new RequestParams(url);
        //因为是推荐热门、不需要搜索，直接按阅读量返回最热门的三条记录
        requestParams.addQueryStringParameter("flag", flag + "");
        requestParams.addQueryStringParameter("pageNo",pageNo+"");
        requestParams.addQueryStringParameter("pageSize",pageSize+"");
        Log.i("FindTuijianFragment", "FindTuijianFragment: getdataFromArticle:文章"+pageNo+"---"+pageSize);
        Log.i("FindTuijianFragment", "FindTuijianFragment: getdataFromArticle" + requestParams);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<ThemeTbl>>() {
                }.getType();
                List<ArticleTbl> newArticle = new ArrayList<ArticleTbl>();
                newArticle = gson.fromJson(result, type);
                Log.i("FindTuijianFragment", "获取文章: onSuccess"+newArticle);
                articleTbls.clear();
                articleTbls.addAll(newArticle);
                if (articleTblCommonAdapter == null) {
                    articleTblCommonAdapter = new CommonAdapter<ArticleTbl>(getActivity(), articleTbls, R.layout.article_item1) {
                        @Override
                        public void convert(ViewHolder viewHolder, ArticleTbl articleTbl, int position) {
                            TextView tvArticleTitle = viewHolder.getViewById(R.id.tv_title1);
                            tvArticleTitle.setText(articleTbl.getArticleTitle());
                            TextView tvArticleTime = viewHolder.getViewById(R.id.tv_article_time1);
                            tvArticleTime.setText(articleTbl.getArticleTime() + "");
                            TextView readNum = viewHolder.getViewById(R.id.tv_readnumber1);
                            readNum.setText(articleTbl.getArticleReadnumber() + "");
                        }
                    };
                   lvFindarticle.setAdapter(articleTblCommonAdapter);
                } else {
                    articleTblCommonAdapter.notifyDataSetChanged();
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


}
