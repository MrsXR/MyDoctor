package cn.gem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import cn.gem.application.MyApplication;
import cn.gem.entity.ArticleTbl;
import cn.gem.mydoctor.R;
import cn.gem.util.CommonAdapter;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;

/**
 * Created by panwenpeng on 2016/10/18.
 */
public class FindArticleFragment extends BaseFragment {

    int flag = 2;
    Integer pageSize = 5;
    Integer pageNo = 1;
    CommonAdapter<ArticleTbl> commonAdapter;
    List<ArticleTbl> articleTbls = new ArrayList<>();
    @InjectView(R.id.lv_find_article)
    ListView lvFindArticle;
    String articleTitle;
    MyApplication myApplication;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_article, null);
        Log.i("FindArticleFragment", "FindArticleFragment: onCreateView:进入   articlefragent");
//        myApplication= (MyApplication) getActivity().getApplication();
//        articleTitle=myApplication.getFindName();
        ButterKnife.inject(this, v);
        Bundle bundle = getArguments();
        if (bundle != null) {
            articleTitle = bundle.getString("value1");
            Log.i("FindThemeFragment", "FindThemeFragment: onCreateView:theme=" + articleTitle);
            getDataFromArticle();
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
    public void getDataFromArticle(){
        String url= NetUtil.url+"QueryArticleServlet";
        RequestParams requestParams=new RequestParams(url);
        requestParams.addQueryStringParameter("articleTitle",articleTitle);
        requestParams.addQueryStringParameter("flag",flag+"");
        requestParams.addQueryStringParameter("pageNo",pageNo+"");
        requestParams.addQueryStringParameter("pageSize",pageSize+"");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                Type type=new TypeToken<List<ArticleTbl>>(){}.getType();
                List<ArticleTbl> newArticle=new ArrayList<ArticleTbl>();
                newArticle=gson.fromJson(result,type);
                articleTbls.clear();
                articleTbls.addAll(newArticle);
                if (commonAdapter==null){
                    commonAdapter=new CommonAdapter<ArticleTbl>(getActivity(),articleTbls,R.layout.article_item1) {

                        @Override
                        public void convert(ViewHolder viewHolder, ArticleTbl articleTbl, int position) {
                            TextView tvTitle=viewHolder.getViewById(R.id.tv_title1);
                            tvTitle.setText(articleTbl.getArticleTitle());
                            TextView tvArticleTime=viewHolder.getViewById(R.id.tv_article_fabiaotime);
                            tvArticleTime.setText(articleTbl.getArticleTime()+"");
                            TextView tvReadnumber=viewHolder.getViewById(R.id.tv_readnumber1);
                            tvReadnumber.setText(articleTbl.getArticleReadnumber()+"");

                        }
                    };
                    lvFindArticle.setAdapter(commonAdapter);
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden==false){
            commonAdapter=null;
            myApplication.getFindName();
            getDataFromArticle();

        }
    }
}
