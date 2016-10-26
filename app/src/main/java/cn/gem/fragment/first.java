package cn.gem.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.gem.mydoctor.R;


/**
 * Created by admin on 2016/9/20.
 */

public class first extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment,container,false);
                return view;
    }

    public static first newInstance() {
        first fragment = new first();
        return fragment;
    }
}
