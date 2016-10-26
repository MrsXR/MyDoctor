package cn.gem.mydoctor;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.fragment.AAAAA;
import cn.gem.fragment.BBBBB;
import cn.gem.fragment.CCCCC;


public class shoucang_Activity extends FragmentActivity {



    @InjectView(R.id.toolbar_shoucang)
    Toolbar toolbarShoucang;
    @InjectView(R.id.shoucang_fragment_tab1)
    TextView shoucangFragmentTab1;
    @InjectView(R.id.shoucang_fragment_tab2)
    TextView shoucangFragmentTab2;
    @InjectView(R.id.shoucang_fragment_tab3)
    TextView shoucangFragmentTab3;
    @InjectView(R.id.liner_xiaoxi)
    LinearLayout linerXiaoxi;
    @InjectView(R.id.shoucang_viewpager)
    ViewPager shoucangViewpager;
    @InjectView(R.id.activity_shoucang_)
    LinearLayout activityShoucang;


    Fragment AAAAA;
    Fragment BBBBB;
    Fragment CCCCC;


    List<Fragment> fragmentList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoucang_);
        ButterKnife.inject(this);

        initfo();

       toolbarShoucang= (Toolbar) findViewById(R.id.toolbar_shoucang);
        toolbarShoucang.setTitle("");
        toolbarShoucang.setNavigationIcon(R.mipmap.fanghui_baise);
        toolbarShoucang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void initfo() {


       fragmentList.add(new AAAAA());
        fragmentList.add(new BBBBB());
        fragmentList.add(new CCCCC());




        shoucangViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

        });

        shoucangViewpager.setOffscreenPageLimit(3);

    }

    @OnClick({R.id.shoucang_fragment_tab1, R.id.shoucang_fragment_tab2, R.id.shoucang_fragment_tab3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shoucang_fragment_tab1:
                shoucangViewpager.setCurrentItem(0,true);
                break;
            case R.id.shoucang_fragment_tab2:
                shoucangViewpager.setCurrentItem(1,true);

                break;
            case R.id.shoucang_fragment_tab3:
                shoucangViewpager.setCurrentItem(2,true);

                break;
        }
    }

}
