package cn.gem.mydoctor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;

import cn.gem.fragment.first;
import cn.gem.fragment.second;
import cn.gem.fragment.third;

public class bottontoolbarActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    private ArrayList<Fragment> fragments;
    int currentIndex=0;
    //标识前一次点击的按钮的index
    int preIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("111", "onCreate: 111");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottontoolbar);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.navigation_bar1);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.hostoil2 ,"首页").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.pass1, "论坛").setActiveColorResource(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.mipmap.my1, "我的").setActiveColorResource(R.color.colorPrimary))


                .setFirstSelectedPosition(0)
                .initialise();
        Log.i("111", "onCreate: 222");
        fragments = getFragments();
        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.search_edit_frame_bottontoolbar,fragments.get(0));
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(first.newInstance());
        fragments.add(second.newInstance());
        fragments.add(third.newInstance());

        return fragments;
    }


    @Override
    public void onTabSelected(int position) {
        currentIndex=position;

        //如果两次点击不是 同一个按钮
        if(currentIndex!=preIndex) {
            Log.i("MainActivity", "onTabClicked: currentIndex:"+currentIndex+",preIndex:"+preIndex);
            //fragment的切换
            toggleFragment(fragments.get(preIndex),fragments.get(currentIndex));
        }
        preIndex=currentIndex;

    }


    //控制fragment的显式和隐藏：

    public void toggleFragment(Fragment hideFragment, Fragment showFragment){

        //如果两次显式的是同一个fragment
        if(hideFragment!=showFragment) {
            Log.i("MainActiviy", "toggleFragment: 两次点击的不是同一个fragment");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.hide(hideFragment);

            if (!showFragment.isAdded()) {
                fragmentTransaction.add(R.id.search_edit_frame_bottontoolbar, showFragment);//第一次显式，先add
            }

            fragmentTransaction.show(showFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
