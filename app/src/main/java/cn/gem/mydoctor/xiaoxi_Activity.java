package cn.gem.mydoctor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.fragment.Fragment_xiaoxi_1;
import cn.gem.fragment.Fragment_xiaoxi_2;

public class xiaoxi_Activity extends FragmentActivity {



    @InjectView(R.id.toolbar_xiaoxi)
    Toolbar toolbarXiaoxi;
    @InjectView(R.id.xiaoxi_fragment_tab1)
    TextView xiaoxiFragmentTab1;
    @InjectView(R.id.xiaoxi_fragment_tab2)
    TextView xiaoxiFragmentTab2;
    @InjectView(R.id.relativelayout_1)
    RelativeLayout relativelayout1;
    @InjectView(R.id.xiaoxi_viewpager)
    ViewPager xiaoxiViewpager;

      Fragment Fragment_xiaoxi_1;
      Fragment Fragment_xiaoxi_2;



    List<Fragment> fragmentList = new ArrayList<Fragment>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaoxi_);


        ButterKnife.inject(this);
        toolbarXiaoxi= (Toolbar) findViewById(R.id.toolbar_xiaoxi);
        toolbarXiaoxi.setTitle("");
        toolbarXiaoxi.setNavigationIcon(R.mipmap.fanghui_baise);
        initfo();

        toolbarXiaoxi.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void initfo() {
        fragmentList.add(new Fragment_xiaoxi_1());
        fragmentList.add(new Fragment_xiaoxi_2());

        xiaoxiViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount( ) {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }
        });
    }


    @OnClick({R.id.xiaoxi_fragment_tab1, R.id.xiaoxi_fragment_tab2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xiaoxi_fragment_tab1:

                xiaoxiViewpager.setCurrentItem(0,true);

                break;
            case R.id.xiaoxi_fragment_tab2:

                xiaoxiViewpager.setCurrentItem(1,true);

                break;
        }
    }




}
