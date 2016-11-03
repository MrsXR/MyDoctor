package cn.gem.mydoctor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.entity.DepartmentsTbl;
import cn.gem.entity.DoctorsTbl;
import cn.gem.entity.SubjectTbl;
import cn.gem.util.CommonAdapter;
import cn.gem.util.CommonGson;
import cn.gem.util.IpChangeAddress;
import cn.gem.util.ListSubject;
import cn.gem.util.ViewHolder;

public class ExpertConsultActivity extends AppCompatActivity {

    @InjectView(R.id.expert_consult_back)
    ImageButton expertConsultBack;
    @InjectView(R.id.expert_consult_synthetical_r)
    RelativeLayout expertConsultSyntheticalR;
    @InjectView(R.id.expert_consult_subject_r)
    RelativeLayout expertConsultSubjectR;
    @InjectView(R.id.expert_consult_position_r)
    RelativeLayout expertConsultPositionR;
    @InjectView(R.id.change_d_listview)
    ListView changeDListview;


    List<String> popContents = new ArrayList<String>();
    List<DoctorsTbl> list=new ArrayList<>();//医生的集合

    //科室、科目
    CommonAdapter<DepartmentsTbl> rAdapter;
    CommonAdapter<SubjectTbl> sAdapter;
    List<DepartmentsTbl> departmentsTbls=new ArrayList<DepartmentsTbl>();
    List<SubjectTbl> subjectTbls=new ArrayList<SubjectTbl>();
    TextView textView;

    ListView listR;
    ListView listS;
    Integer subjectId = null;
    Integer synthetical=0;//综合排序
    Integer positionD=0;

    int pageNumber=1;//分页页数

    CommonAdapter<DoctorsTbl> commonAdapter;

    private static  final int SUBJECT=10;
    private static  final int POSITION=11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_consult);
        ButterKnife.inject(this);

        inintView();
        getData(this);
    }

    private  void inintView(){

        popContents.add("全部职位");
        popContents.add("主任医师");
        popContents.add("副主任医师");
        popContents.add("主治医师");
        popContents.add("普通医师");

        getColor(expertConsultSyntheticalR,expertConsultPositionR,expertConsultSubjectR);
    }


    @OnClick({R.id.expert_consult_back, R.id.expert_consult_synthetical_r, R.id.expert_consult_subject_r, R.id.expert_consult_position_r})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.expert_consult_back:
                //放回首页
                finish();
                break;
            case R.id.expert_consult_synthetical_r:
                //综合排序
                synthetical=0;
                positionD=0;
                getColor(expertConsultSyntheticalR,expertConsultPositionR,expertConsultSubjectR);
                getData(this);
                break;
            case R.id.expert_consult_subject_r:
                //科室排序
                getColor(expertConsultSubjectR,expertConsultPositionR,expertConsultSyntheticalR);
                getDepartments();
                break;
            case R.id.expert_consult_position_r:
                //综合排序+职位筛选
                getColor(expertConsultPositionR,expertConsultSubjectR,expertConsultSyntheticalR);
                getPopupWindow(this,POSITION);
                break;
        }
    }

    private void getColor(RelativeLayout button1, RelativeLayout button2, RelativeLayout button3){

        button1.setSelected(true);
        button1.setPressed(true);
        button1.setBackgroundColor(Color.parseColor("#FFFFFF"));

        button2.setSelected(false);
        button2.setPressed(false);
        button2.setBackgroundColor(Color.parseColor("#AADAF5"));

        button3.setSelected(false);
        button3.setPressed(false);
        button3.setBackgroundColor(Color.parseColor("#AADAF5"));
    }

    //获取数据库信息
    public void getData(final Context context) {
        String stl = IpChangeAddress.ipChangeAddress + "ListDoctorsServlet";
        RequestParams requestParams = new RequestParams(stl);

        if(subjectId!=null){
            requestParams.addQueryStringParameter("changeSubject", subjectId + "");//科室
        }

        if (synthetical!=null)
            requestParams.addQueryStringParameter("synthetical", synthetical + "");//综合排序

        if (positionD != 0) {
            requestParams.addQueryStringParameter("positionD", positionD + "");//综合排序+职位
        }

        requestParams.addQueryStringParameter("pageNumber", pageNumber + "");//分页医生
        x.http().get(requestParams, new Callback.CacheCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //获取数据成功在ListView上面显示
                List<DoctorsTbl> listNew=new ArrayList<>();
                Gson gson= CommonGson.getGson();

                Type type=new TypeToken<List<DoctorsTbl>>(){}.getType();
                listNew=gson.fromJson(result,type);
                list.clear();
                list.addAll(listNew);//保证引用不变
                //适配器
                if(commonAdapter==null){
                    commonAdapter=new CommonAdapter<DoctorsTbl>(context,list,R.layout.change_doctor_listview) {
                        @Override
                        public void convert(ViewHolder viewHolder, DoctorsTbl doctorsTbl, int position) {
                            ImageView imageViewPhoto=viewHolder.getViewById(R.id.imageView);
                            TextView textViewName=viewHolder.getViewById(R.id.change_doctor_name);
                            TextView textViewPosition=viewHolder.getViewById(R.id.change_doctor_position);
                            TextView textViewR=viewHolder.getViewById(R.id.change_doctor_textView5);//推荐指数
                            TextView textViewBrief=viewHolder.getViewById(R.id.change_doctor_brief);


                            //获取图片
                            getImage(doctorsTbl.getDoctorsPhoto(),imageViewPhoto);
                            textViewName.setText(doctorsTbl.getDoctorsSname());
                            textViewPosition.setText(getPositionName(doctorsTbl.getDoctorsPosition()));
                            textViewR.setText(doctorsTbl.getDoctorsRecommend()+"");
                            textViewBrief.setText(doctorsTbl.getDoctorsBrief());
                        }
                    };
                    changeDListview.setAdapter(commonAdapter);
                }else {
                    commonAdapter.notifyDataSetInvalidated();
                }

                //ListView的点击事件
                changeDListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent(ExpertConsultActivity.this,DoctorsActivity.class);
                        DoctorsTbl doctor=list.get(position);
                        intent.putExtra("doctorOne",doctor);
                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });

    }

    //再次访问服务器获取图片
    public void getImage(String position,ImageView imageView){
        String photoUrl=IpChangeAddress.ipChangeAddress+position;
        ImageOptions imageOptions=new ImageOptions.Builder()
                .setCircular(true)
                .setCrop(true).setSize(65,65).build();

        x.image().bind(imageView,photoUrl,imageOptions);
    }

    //判断医生的职位
    public  String getPositionName(int k){
        String positionName=null;
        switch (k){
            case 1:
                positionName="主任医师" ;
                break;
            case 2:
                positionName="副主任医师" ;
                break;
            case 3:
                positionName="主治医师" ;
                break;
            case 4:
                positionName="普通医师" ;
                break;
        }

        return positionName;
    }



    public void getPopupWindow(final Context context,final int k){

        if(k==SUBJECT){

            View v= LayoutInflater.from(this).inflate(R.layout.hospital_brief_orders,null);
            final PopupWindow popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, 900);
            //科室
            listR= (ListView) v.findViewById(R.id.hospital_brief_departments);
            //科目
            listS= (ListView) v.findViewById(R.id.hospital_brief_subject);

            //科室的适配器
            rAdapter=new CommonAdapter<DepartmentsTbl>(this,departmentsTbls,R.layout.hospital_brief_common_text) {
                @Override
                public void convert(ViewHolder viewHolder, DepartmentsTbl departmentsTbl, int position) {
                    textView=viewHolder.getViewById(R.id.hospital_brief_common_item);
                    textView.setText(departmentsTbl.getDepartmentsSname());
                }
            };
            listR.setAdapter(rAdapter);

            getSubject(0);//显示默认科室

            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.showAsDropDown(findViewById(R.id.expert_consult_toolbar_temp2));  //显示在v的下面

            //点击科室显示对应的科目信息
            listR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    getSubject(position);
                }
            });

            //科目点击信息
            listS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //科目对应的ID
                    subjectId=subjectTbls.get(position).getSubjectId();
                    //再次访问数据库 获取医生信息
                    getData(ExpertConsultActivity.this);
                    popupWindow.dismiss();
                }
            });


        }

        if(k==POSITION) {
            //设置pupopwindow
            View view1 = LayoutInflater.from(this).inflate(R.layout.common_array_adapter, null);
            final PopupWindow popupWindow = new PopupWindow(view1, ViewGroup.LayoutParams.MATCH_PARENT, 900);

            //listview设置数据源
            ListView listView = (ListView) view1.findViewById(R.id.common_arrayadapter_listView);
            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.common_arrayadapter_text, R.id.common_array_text, popContents);

            listView.setAdapter(arrayAdapter);

            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.showAsDropDown(findViewById(R.id.expert_consult_toolbar_temp2));  //显示在v的下面

            //点击事件
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    positionD = position;
                    synthetical = null;
                    //重新获取数据
                    getData(context);
                    popupWindow.dismiss();
                }
            });
        }

    }


    //获取科室信息
    public  void getDepartments(){
                departmentsTbls=new ListSubject().getDepartmentsTbls();
                //赋值
                getPopupWindow(ExpertConsultActivity.this,SUBJECT);
    }


    //科室的信息显示
    public void getSubject(int k){
        subjectTbls=departmentsTbls.get(k).getSubjectTbl();
        //科目
        sAdapter=new CommonAdapter<SubjectTbl>(this,subjectTbls,R.layout.hospital_brief_common_white_text) {
            @Override
            public void convert(ViewHolder viewHolder, SubjectTbl subjectTbl, int position) {
                textView=viewHolder.getViewById(R.id.hospital_brief_common_item1);
                textView.setText(subjectTbl.getSubjectSname());
            }
        };
        listS.setAdapter(sAdapter);
    }


}
