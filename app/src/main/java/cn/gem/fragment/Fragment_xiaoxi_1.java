package cn.gem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.gem.mydoctor.R;


/**
 * Created by admin on 2016/10/10.
 */

public class Fragment_xiaoxi_1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.xiaofragment_1, null);

        return v;

    }
    public static Fragment_xiaoxi_1 newInstance() {

        Fragment_xiaoxi_1 fragment = new Fragment_xiaoxi_1();
        return fragment;
    }
}
