package cn.gem.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import butterknife.OnClick;
import cn.gem.entity.ThemeTbl;
import cn.gem.mydoctor.R;
import cn.gem.mydoctor.baoxian_Activity;
import cn.gem.mydoctor.dangan_Activity;
import cn.gem.mydoctor.fuwu_Activity;
import cn.gem.mydoctor.jifen_Activity;
import cn.gem.mydoctor.kefu_Activity;
import cn.gem.mydoctor.login_mydoctorActivity;
import cn.gem.mydoctor.qianbao_Activity;
import cn.gem.mydoctor.shezhi_Activity;
import cn.gem.mydoctor.shoucang_Activity;
import cn.gem.mydoctor.xiaoxi_Activity;
import cn.gem.mydoctor.yuyue_Activity;
import cn.gem.mydoctor.zixun_Activity;
import cn.gem.util.CommonAdapter;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;
import cn.gem.weight.newlistview;

/**
 * Created by WangChang on 2016/5/15.
 */
public class FirstMyFragment extends Fragment {

    @InjectView(R.id.imageButton8)
    ImageButton imageButton8;
    @InjectView(R.id.imageButton9)
    ImageButton imageButton9;
    @InjectView(R.id.imageButton10)
    ImageButton imageButton10;
    @InjectView(R.id.imageButton13)
    ImageButton imageButton13;
    @InjectView(R.id.imageButton11)
    ImageButton imageButton11;
    @InjectView(R.id.linearlayout1)
    LinearLayout linearlayout1;
    @InjectView(R.id.linearlayout2)
    LinearLayout linearlayout2;
    @InjectView(R.id.linearlayout3)
    LinearLayout linearlayout3;
    @InjectView(R.id.linearlayout4)
    LinearLayout linearlayout4;
    @InjectView(R.id.linearlayout5)
    LinearLayout linearlayout5;
    @InjectView(R.id.linearlayout6)
    LinearLayout linearlayout6;
    @InjectView(R.id.linearlayout7)
    LinearLayout linearlayout7;

    int id=1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_toolbar, container, false);
        ButterKnife.inject(this, view);
        return view;




    }


    public static third newInstance() {

        third fragment = new third();
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @OnClick({R.id.imageButton8, R.id.imageButton9, R.id.imageButton10, R.id.imageButton13, R.id.imageButton11, R.id.linearlayout1, R.id.linearlayout2, R.id.linearlayout3, R.id.linearlayout4, R.id.linearlayout5, R.id.linearlayout6, R.id.linearlayout7})
    public void onClick(View view) {
        int id=new NetUtil().getUser().getUserId();
        switch (view.getId()) {
            case R.id.imageButton8:
                Intent intent=new Intent(getActivity(),login_mydoctorActivity.class);
                startActivity(intent);
                break;
            case R.id.imageButton9:

                Intent intent1=new Intent(getActivity(),zixun_Activity.class);
                startActivity(intent1);

                break;
            case R.id.imageButton10:

                Intent intent2=new Intent(getActivity(),shoucang_Activity.class);
                startActivity(intent2);

                break;
            case R.id.imageButton13:

                Intent intent3=new Intent(getActivity(),xiaoxi_Activity.class);
                startActivity(intent3);

                break;
            case R.id.imageButton11:

                Intent intent4=new Intent(getActivity(),fuwu_Activity.class);
                startActivity(intent4);

                break;
            case R.id.linearlayout1:

                Intent intent5=new Intent(getActivity(),yuyue_Activity.class);
                startActivity(intent5);

                break;
            case R.id.linearlayout2:
                Intent intent6=new Intent(getActivity(),jifen_Activity.class);
                startActivity(intent6);

                break;
            case R.id.linearlayout3:

                Intent intent7=new Intent(getActivity(),qianbao_Activity.class);
                startActivity(intent7);

                break;
            case R.id.linearlayout4:

                Intent intent8=new Intent(getActivity(),dangan_Activity.class);
                startActivity(intent8);

                break;
            case R.id.linearlayout5:

                Intent intent9=new Intent(getActivity(),baoxian_Activity.class);
                startActivity(intent9);


                break;
            case R.id.linearlayout6:

                Intent intent10=new Intent(getActivity(),kefu_Activity.class);
                startActivity(intent10);


                break;
            case R.id.linearlayout7:

                Intent intent11=new Intent(getActivity(),shezhi_Activity.class);
                startActivity(intent11);

                break;
        }
    }


}
