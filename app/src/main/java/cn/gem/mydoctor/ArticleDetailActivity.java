package cn.gem.mydoctor;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ArticleDetailActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar_articleDetail)
    Toolbar toolbarArticleDetail;
    @InjectView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.inject(this);

        String url="http://www.baikemy.com/jiankangkepu/23277760908545";
        

//        webView.loadUrl("http://www.baikemy.com/jiankangkepu/23277760908545");
//        webView.setWebViewClient(new WebViewClient());
//        webView.getSettings().setJavaScriptEnabled(false);

        // toolbarArticleDetail.setLogo(R.mipmap.fanhui1);
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
        getMenuInflater().inflate(R.menu.detailmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
