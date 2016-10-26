package cn.gem.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.mydoctor.FindActivity;
import cn.gem.mydoctor.HintActivity;
import cn.gem.mydoctor.R;


/**
 * Created by WangChang on 2016/5/15.
 */
public class FirstCharFragment extends Fragment {
    Fragment[] fragments;
    ThemeFragment themeFragment;//热聊
    ArticleFragment articleFragment;//资讯
    //radiobutton的数组，一开始第一个被选中
    RadioButton[] rbns;

    int oldIndex;//用户看到的item
    int newIndex;//用户即将看到的
    @InjectView(R.id.rb_zixun1)
    RadioButton rbZixun1;
    @InjectView(R.id.rb_reliao1)
    RadioButton rbReliao1;
    @InjectView(R.id.rg_title_theme)
    RadioGroup rgTitleTheme;
    @InjectView(R.id.iv_hint)
    ImageView ivHint;
    @InjectView(R.id.iv_find)
    ImageView ivFind;
    @InjectView(R.id.RL_title_theme)
    RelativeLayout RLTitleTheme;
    @InjectView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_theme, container, false);
        //初始化fragment
        themeFragment = new ThemeFragment();
        articleFragment = new ArticleFragment();
        //所有fragment的数组
        fragments = new Fragment[]{themeFragment, articleFragment};
        //按钮数组
        rbns = new RadioButton[2];
        rbns[0] = (RadioButton) v.findViewById(R.id.rb_reliao1);
        rbns[1] = (RadioButton) v.findViewById(R.id.rb_zixun1);
        //界面初始化显示的第一个fragment，添加第一个fragment
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragments[0]).commit();

        rbns[0].setChecked(true);
        ButterKnife.inject(this, v);

        return v;
    }

//    public void onTabClicked(View view) {
//        switch (view.getId()) {
//            case R.id.rb_reliao1:
//                newIndex = 0;//选中第一项
//                break;
//            case R.id.rb_zixun1:
//                newIndex = 1;//选中第二项
//                break;
//
//        }
//        switchFragment();
//    }

    public void switchFragment() {
        FragmentTransaction transaction;
        if (newIndex != oldIndex) {
            transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.hide(fragments[oldIndex]);//隐藏当前显示项

            if (!fragments[newIndex].isAdded()) {
                transaction.add(R.id.fragment_container, fragments[newIndex]);
            }

            transaction.show(fragments[newIndex]).commit();
        }
        rbns[oldIndex].setChecked(false);

        rbns[newIndex].setChecked(true);
        oldIndex = newIndex;
    }

    public static FirstCharFragment newInstance() {

        FirstCharFragment fragment = new FirstCharFragment();
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.iv_hint, R.id.iv_find,R.id.rb_zixun1, R.id.rb_reliao1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_hint:
                Intent intent = new Intent(getActivity(), HintActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_find:
                Intent intent1 = new Intent(getActivity(), FindActivity.class);
                startActivity(intent1);
                break;
            case R.id.rb_zixun1:
                newIndex = 1;//选中第一项
                switchFragment();
                Log.i("FirstCharFragment", "FirstCharFragment: onClick"+fragments[newIndex]);
                break;
            case R.id.rb_reliao1:
                newIndex=0; //选中第二项
                switchFragment();
                Log.i("FirstCharFragment", "FirstCharFragment: onClick"+fragments[newIndex]);
                 break;
        }
    }


    }

