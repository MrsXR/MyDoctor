package cn.gem.mydoctor;
/**
 * 选择医生
 * 1：主任医师；2：副主任医师；3：主治医师；4：普通医师
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import cn.gem.entity.DoctorsTbl;
import cn.gem.util.CommonAdapter;
import cn.gem.util.CommonGson;
import cn.gem.util.IpChangeAddress;
import cn.gem.util.ViewHolder;


public class ChangeDoctorActivity extends AppCompatActivity {

    @InjectView(R.id.change_doctor_subject)
    TextView changeDoctorSubject;
    @InjectView(R.id.change_doctor_back)
    ImageButton changeDoctorBack;
    @InjectView(R.id.change_d_listview)
    ListView changeDListview;
    @InjectView(R.id.change_d_synthetical)
    Button changeDSynthetical;
    @InjectView(R.id.change_doctor_position_r)
    RelativeLayout changeDoctorPositionR;


    List<String> popContents = new ArrayList<String>();
    List<DoctorsTbl> list=new ArrayList<>();//医生的集合

    int changeSubject = 0;
    int hospitalId = 0;
    Integer synthetical=0;//综合排序
    Integer positionD=0;

    int pageNumber=1;//分页页数

    CommonAdapter<DoctorsTbl> commonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_doctor);
        ButterKnife.inject(this);

        popContents.add("全部职位");
        popContents.add("主任医师");
        popContents.add("副主任医师");
        popContents.add("主治医师");
        popContents.add("普通医师");


        //获取科室的id
        changeSubject = Integer.parseInt(getIntent().getStringExtra("changeSubject"));
        hospitalId = Integer.parseInt(getIntent().getStringExtra("hospitalId"));
        getData(this);

        changeDoctorBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //获取数据库信息
    public void getData(final Context context) {
        String stl = IpChangeAddress.ipChangeAddress + "ListDoctorsServlet";
        RequestParams requestParams = new RequestParams(stl);
        requestParams.addQueryStringParameter("hospitalId", hospitalId + "");//科目
        requestParams.addQueryStringParameter("changeSubject", changeSubject + "");//科室
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
                        Intent intent=new Intent(ChangeDoctorActivity.this,DoctorsActivity.class);
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

    @OnClick({R.id.change_d_synthetical, R.id.change_d_position,R.id.change_h_down})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_d_synthetical:
                //综合排序
                synthetical=0;
                positionD=0;
                getData(this);
                break;
            case R.id.change_d_position:
                //综合排序+职位筛选
                getPopupWindow(this);

                break;
            case R.id.change_h_down:
               getPopupWindow(this);
                break;
        }
    }

    public void getPopupWindow(final Context context){
        //设置pupopwindow
        View view1= LayoutInflater.from(this).inflate(R.layout.common_array_adapter,null);
        final PopupWindow popupWindow=new PopupWindow(view1,ViewGroup.LayoutParams.MATCH_PARENT, 700);

        //listview设置数据源
        ListView listView= (ListView) view1.findViewById(R.id.common_arrayadapter_listView);
        ArrayAdapter arrayAdapter=new ArrayAdapter<String>(this,R.layout.common_arrayadapter_text,R.id.common_array_text,popContents);

        listView.setAdapter(arrayAdapter);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(findViewById(R.id.change_doctor_position_r));  //显示在v的下面

        //点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionD=position;
                synthetical=null;
                //重新获取数据
                getData(context);
                popupWindow.dismiss();
            }
        });


    }
}
