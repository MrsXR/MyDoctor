package cn.gem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
import cn.gem.entity.ArticleTbl;
import cn.gem.mydoctor.ArticleDetailActivity;
import cn.gem.mydoctor.R;
import cn.gem.util.CommonAdapter;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;


/**
 * Created by panwenpeng on 2016/10/4.
 */
public class ArticleFragment extends BaseFragment {
    int flag = 2;
    Integer pageNo = 1;
    Integer pageSize = 5;
    List<ArticleTbl> articleTbls = new ArrayList<>();
    CommonAdapter<ArticleTbl> articleTblCommonAdapter;
    @InjectView(R.id.lv_article)
    ListView lvArticle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article, null);
        ButterKnife.inject(this, v);
        getDate();
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

    public void getDate() {
        String url = NetUtil.url + "QueryArticleServlet";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("flag", flag + "");
        requestParams.addQueryStringParameter("pageNo", pageNo + "");
        requestParams.addQueryStringParameter("pageSize", pageSize + "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<ArticleTbl>>() {
                }.getType();
                List<ArticleTbl> newArticle = gson.fromJson(result, type);
                articleTbls.clear();
                articleTbls.addAll(newArticle);
                if (articleTblCommonAdapter == null) {
                    articleTblCommonAdapter = new CommonAdapter<ArticleTbl>(getActivity(), articleTbls, R.layout.article_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ArticleTbl articleTbl, int position) {
                            ImageView ivPhoto = viewHolder.getViewById(R.id.iv_article_photo);
                            ImageOptions imageOptions1 = new ImageOptions.Builder().setCrop(true).build();
                            String articleUrl = NetUtil.image + articleTbl.getArticlePhoto();
                            Log.i("ArticleFragment", "ArticleFragment: convertPHOTO" + articleUrl);
                            x.image().bind(ivPhoto, articleUrl, imageOptions1);
                            TextView tvTitle = viewHolder.getViewById(R.id.tv_article_title);
                            tvTitle.setText(articleTbl.getArticleTitle());
                            TextView tvTime = viewHolder.getViewById(R.id.article_time);
                            String time = upsateTime(articleTbl.getArticleTime());
                            tvTime.setText(time + "");
                            TextView tvArticleFrom = viewHolder.getViewById(R.id.article_from);
                            tvArticleFrom.setText(articleTbl.getArticleFrom());
                            TextView tvLookNum = viewHolder.getViewById(R.id.tv_article_looknum1);
                            tvLookNum.setText(articleTbl.getArticleReadnumber() + "");
                        }
                    };
                    lvArticle.setAdapter(articleTblCommonAdapter);
                } else {
                    articleTblCommonAdapter.notifyDataSetChanged();
                }
                lvArticle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent(getActivity(), ArticleDetailActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putParcelable("article",articleTbls.get(position));
                        Log.i("ArticleFragment", "ArticleFragment: 传过去的文章"+articleTbls.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
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

    public String upsateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);
        return time;
    }
}