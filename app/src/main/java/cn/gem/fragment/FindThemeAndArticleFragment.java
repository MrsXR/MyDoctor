package cn.gem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.gem.mydoctor.R;

/**
 * Created by panwenpeng on 2016/10/17.
 */
public class FindThemeAndArticleFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_find_themeandarticle,null);
        Log.i("FindThemeAndArticleFra", "FindThemeAndArticleFragment: onCreateView:进入全部查询fragment");
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
}
