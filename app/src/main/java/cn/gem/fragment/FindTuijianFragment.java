package cn.gem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.gem.entity1.ArticleTbl;
import cn.gem.entity1.ThemeTbl;
import cn.gem.mydoctor.R;
import cn.gem.util.CommonAdapter;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;
import cn.gem.weight.NoScrollListview;

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
    int flag = 2;//排序标记
    Integer pageNo = 1;//
    Integer pageSize = 3;//一个模块显示三条记录，查找推荐页


    @InjectView(R.id.tv_hottheme)
    TextView tvHottheme;
    @InjectView(R.id.lv_findtheme)
    NoScrollListview lvFindtheme;
    @InjectView(R.id.rl_hottheme)
    LinearLayout rlHottheme;
    @InjectView(R.id.tv_hotarticle)
    TextView tvHotarticle;
    @InjectView(R.id.lv_findarticle)
    NoScrollListview lvFindarticle;
    @InjectView(R.id.rl_hotarticle)
    LinearLayout rlHotarticle;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_tuijian, null);
        getDateFromTheme();
        getdataFromArticle();
        ButterKnife.inject(this, v);
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
        requestParams.addQueryStringParameter("pageNo", pageNo + "");
        requestParams.addQueryStringParameter("pageSize", pageSize + "");
        Log.i("FindTuijianFragment", "FindTuijianFragment: getDateFromTheme" + pageNo + "---" + pageSize);
        Log.i("FindTuijianFragment", "FindTuijianFragment: getDateFromTheme" + requestParams);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<ThemeTbl>>() {
                }.getType();
                List<ThemeTbl> newThemeTbl = new ArrayList<ThemeTbl>();
                newThemeTbl = gson.fromJson(result, type);
                Log.i("FindTuijianFragment", "获取话题: onSuccess" + newThemeTbl);
                themeTbls.clear();
                themeTbls.addAll(newThemeTbl);
                if (themeTblCommonAdapter == null) {
                    Log.i("FindTuijianFragment", "FindTuijianFragment: onSuccess:进入if方法");
                    themeTblCommonAdapter = new CommonAdapter<ThemeTbl>(getActivity(), themeTbls, R.layout.theme_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ThemeTbl themeTbl, int position) {
                            TextView tvThenmeName = viewHolder.getViewById(R.id.tv_username);
                            tvThenmeName.setText(themeTbl.getUserTbl().getUserSname());
                            Log.i("FindTuijianFragment", "FindTuijianFragment: " + tvThenmeName);
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
        requestParams.addQueryStringParameter("pageNo", pageNo + "");
        requestParams.addQueryStringParameter("pageSize", pageSize + "");
        Log.i("FindTuijianFragment", "FindTuijianFragment: getdataFromArticle:文章" + pageNo + "---" + pageSize);
        Log.i("FindTuijianFragment", "FindTuijianFragment: getdataFromArticle" + requestParams);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<ArticleTbl>>() {
                }.getType();
                List<ArticleTbl> newArticle = new ArrayList<ArticleTbl>();
                newArticle = gson.fromJson(result, type);
                Log.i("FindTuijianFragment", "获取文章: onSuccess" + newArticle);
                articleTbls.clear();
                articleTbls.addAll(newArticle);
                if (articleTblCommonAdapter == null) {
                    articleTblCommonAdapter = new CommonAdapter<ArticleTbl>(getActivity(), articleTbls, R.layout.article_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ArticleTbl articleTbl, int position) {
                          ImageView ivPhoto = viewHolder.getViewById(R.id.iv_article_photo);
                            ImageOptions imageOptions1 = new ImageOptions.Builder().setCrop(true).build();
                            String articleUrl=NetUtil.image+articleTbl.getArticlePhoto();
                            x.image().bind(ivPhoto, articleUrl, imageOptions1);
                            TextView tvTitle = viewHolder.getViewById(R.id.tv_article_title);
                            tvTitle.setText(articleTbl.getArticleTitle());
//                            TextView tvContent = viewHolder.getViewById(R.id.tv_content);
//                            tvContent.setText(articleTbl.getArticleContext());
                            TextView tvArticleFrom=viewHolder.getViewById(R.id.article_from);
                            tvArticleFrom.setText(articleTbl.getArticleFrom());
                            TextView tvTime = viewHolder.getViewById(R.id.article_time);
                            String time=upsateTime(articleTbl.getArticleTime());
                            tvTime.setText(time + "");
                            TextView tvLookNum = viewHolder.getViewById(R.id.tv_article_looknum1);
                            tvLookNum.setText(articleTbl.getArticleReadnumber() + "");
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


    public String upsateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);
        return time;
    }
}
