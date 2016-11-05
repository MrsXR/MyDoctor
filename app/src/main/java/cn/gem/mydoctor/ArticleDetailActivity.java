package cn.gem.mydoctor;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.gem.entity.ArticleTbl;
import cn.gem.weight.NoScrollListview;

public class ArticleDetailActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar_articleDetail)
    Toolbar toolbarArticleDetail;
    @InjectView(R.id.tv_articleTitle)
    TextView tvArticleTitle;
    @InjectView(R.id.tv_articletime)
    TextView tvArticletime;
    @InjectView(R.id.tv_articleFrom)
    TextView tvArticleFrom;
    @InjectView(R.id.article_content)
    TextView articleContent;
    @InjectView(R.id.tv_thesame)
    TextView tvThesame;
    @InjectView(R.id.lv_thesame)
    NoScrollListview lvThesame;
    ArticleTbl articleTbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.inject(this);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        articleTbl=bundle.getParcelable("article");
        Log.i("ArticleDetailActivity", "ArticleDetailActivity: 接受的文章"+articleTbl);
        initView();
        toolbarArticleDetail.setTitle("文章详情");
        setSupportActionBar(toolbarArticleDetail);
        toolbarArticleDetail.setNavigationIcon(R.mipmap.fanhui1);
        toolbarArticleDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        toolbarArticleDetail.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.articledetail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    public void initView(){
        tvArticleTitle.setText(articleTbl.getArticleTitle());
        tvArticletime.setText(articleTbl.getArticleTime()+"");
        tvArticleFrom.setText("来自:"+articleTbl.getArticleFrom());
        articleContent.setText(articleTbl.getArticleContext());
    }

}
