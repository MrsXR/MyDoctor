package cn.gem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.concurrent.ScheduledExecutorService;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.application.MyApplication;
import cn.gem.mydoctor.ExpertConsultActivity;
import cn.gem.mydoctor.GetLocationActivity;
import cn.gem.mydoctor.GoFastConsultActivity;
import cn.gem.mydoctor.HospitalBriefActivity;
import cn.gem.mydoctor.OrderFirstMainActivity;
import cn.gem.mydoctor.R;
import cn.gem.util.IpChangeAddress;
import cn.gem.weight.SlideShowView;

/**
 * Created by WangChang on 2016/5/15.
 */
public class FirstHomeFragment extends Fragment {

    SlideShowView slideShowView;
    @InjectView(R.id.home_go_order_doctor)
    RelativeLayout homeGoOrderDoctor;
    @InjectView(R.id.home_go_fastD)
    RelativeLayout homeGoFastD;
    @InjectView(R.id.search_to_content)
    RelativeLayout searchToContent;
    @InjectView(R.id.home_consult)
    RelativeLayout homeConsult;
    @InjectView(R.id.search_content_s)
    RelativeLayout searchContentS;
    @InjectView(R.id.home_my_hospital)
    RelativeLayout homeMyHospital;
    private ScheduledExecutorService scheduledExecutorService;

    MyApplication myApplication;

    public static FirstHomeFragment newInstance() {

        FirstHomeFragment fragment = new FirstHomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout_x, container, false);
        slideShowView = (SlideShowView) view.findViewById(R.id.slideshowView);
        ButterKnife.inject(this, view);

        myApplication= (MyApplication) getContext().getApplicationContext();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.home_go_order_doctor, R.id.home_go_fastD, R.id.search_to_content, R.id.home_consult, R.id.search_content_s, R.id.home_my_hospital})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_go_order_doctor:
                // 预约医生
                Intent intent =new Intent(getContext(),OrderFirstMainActivity.class);
                startActivity(intent);
                break;
            case R.id.home_go_fastD:
                //快速咨询
                Intent intent2=new Intent(getContext(),GoFastConsultActivity.class);
                startActivity(intent2);
                break;
            case R.id.search_to_content:
                //搜索

                break;
            case R.id.home_consult:
                //专家咨询
                Intent intent13=new Intent(getContext(),ExpertConsultActivity.class);
                startActivity(intent13);
                break;
            case R.id.search_content_s:
                //附近的医院
                Intent intent1=new Intent(getContext(),GetLocationActivity.class);
                startActivity(intent1);
                break;
            case R.id.home_my_hospital:
                //我的医院
                getHospital();
                
                break;
        }
    }

    private void getHospital(){

        int userId=myApplication.getUserTbl().getUserId();
        String sql= IpChangeAddress.ipChangeAddress+"HospitalUpdateServlet";

        RequestParams requestParams=new RequestParams(sql);
        requestParams.addQueryStringParameter("flag",1+"");
        requestParams.addQueryStringParameter("userId",userId+"");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                int hospitalId=Integer.parseInt(result);
                Intent intent=new Intent(getContext(), HospitalBriefActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("hospitalId",hospitalId);
                intent.putExtras(bundle);
                startActivity(intent);
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
        });
    }

}
