package cn.gem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.gem.mydoctor.R;

/**
 * 医院简历
 * Created by sony on 2016/10/6.
 */
public class HospitalOneBrief extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.common_array_adapter,null);
        ListView listView= (ListView) v.findViewById(R.id.common_arrayadapter_listView);

        //TextView textView= (TextView) v.findViewById(R.id.hospital_one_brief_text);
        Bundle bundle=getArguments();
        List list=new ArrayList();
        list.add(bundle.getString("hospitalBrief"));
        ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(),R.layout.hospital_one_brief,R.id.hospital_one_brief_text,list);
        listView.setAdapter(arrayAdapter);

        return v;
    }

}
