package cn.gem.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.gem.entity.DepartmentsTbl;
import cn.gem.entity.SubjectTbl;
import cn.gem.mydoctor.ChangeDoctorActivity;
import cn.gem.mydoctor.R;
import cn.gem.util.CommonAdapter;
import cn.gem.util.CommonGson;
import cn.gem.util.ViewHolder;


/**
 * Created by sony on 2016/10/6.
 */
public class HospitalBriefOrders extends Fragment {

    List<DepartmentsTbl> departmentsTbls=new ArrayList<DepartmentsTbl>();
    List<SubjectTbl> subjectTbls=new ArrayList<SubjectTbl>();

    CommonAdapter<DepartmentsTbl> rAdapter;
    CommonAdapter<SubjectTbl> sAdapter;
    TextView textView;
    ListView listR;
    ListView listS;

    int hospitalId=0;
    int selectedPosition = 0;// 选中的位置

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.hospital_brief_orders,null);
        //科室
        listR= (ListView) v.findViewById(R.id.hospital_brief_departments);
        //科目
        listS= (ListView) v.findViewById(R.id.hospital_brief_subject);

        Bundle bundle=getArguments();
        Gson g= CommonGson.getGson();
        departmentsTbls=g.fromJson(bundle.getString("hospitalBrief"),new TypeToken<List<DepartmentsTbl>>(){}.getType());
        hospitalId=bundle.getInt("hospitalId");

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //科室的适配器

        if(departmentsTbls.size()>0) {
            rAdapter = new CommonAdapter<DepartmentsTbl>(getContext(), departmentsTbls, R.layout.hospital_brief_common_text) {

                @Override
                public void convert(ViewHolder viewHolder, DepartmentsTbl departmentsTbl, int position) {
                    textView = viewHolder.getViewById(R.id.hospital_brief_common_item);
                    textView.setText(departmentsTbl.getDepartmentsSname());

                    if (selectedPosition == position) {
                        textView.setSelected(true);
                        textView.setPressed(true);
                        textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    } else {
                        textView.setSelected(false);
                        textView.setPressed(false);
                        textView.setBackgroundColor(Color.parseColor("#AADAF5"));

                    }
                    if(selectedPosition==0&&position==0){
                        textView.setSelected(true);
                        textView.setPressed(true);
                        textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }

                }
            };
            listR.setAdapter(rAdapter);

            getSubject(0);//显示默认科室

            //点击科室显示对应的科目信息
            listR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    getSubject(position);
                    selectedPosition=position;
                    rAdapter.notifyDataSetInvalidated();
                }
            });

            //科目点击信息
            listS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //跳转到对应科室的医生界面
                    Log.i("Hospital", "onItemClick:------S " + subjectTbls.get(position).getSubjectId());
                    Intent intent = new Intent(getContext(), ChangeDoctorActivity.class);
                    intent.putExtra("changeSubject", subjectTbls.get(position).getSubjectId() + "");
                    intent.putExtra("hospitalId", hospitalId + "");
                    startActivity(intent);
                }
            });
        }
    }


    //科室的信息显示
    public void getSubject(int k) {
        if (departmentsTbls.get(k).getSubjectTbl().size()>0) {
            subjectTbls = departmentsTbls.get(k).getSubjectTbl();
            //科目
            sAdapter = new CommonAdapter<SubjectTbl>(getContext(), subjectTbls, R.layout.hospital_brief_common_white_text) {
                @Override
                public void convert(ViewHolder viewHolder, SubjectTbl subjectTbl, int position) {
                    textView = viewHolder.getViewById(R.id.hospital_brief_common_item1);
                    textView.setText(subjectTbl.getSubjectSname());
                }
            };
            listS.setAdapter(sAdapter);
        }
    }
}
