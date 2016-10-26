package cn.gem.util;
/**
 * 预约界面的ListView
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import cn.gem.application.MyApplication;
import cn.gem.entity.HospitalTbl;
import cn.gem.mydoctor.HospitalBriefActivity;
import cn.gem.mydoctor.R;


/**
 * Created by sony on 2016/9/22.
 */
public class OrderFirstBaseAdapter extends BaseAdapter {

    List<HospitalTbl> objects;
    Context context;
    Bitmap bitmap =null;
    Intent intent=null;
    Bundle bundle=null;

    MyApplication myApplication;

    public OrderFirstBaseAdapter(Context context,List<HospitalTbl> objects) {
        this.context=context;
        this.objects=objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=null;
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.order_first_list_main, null);
            viewHolder = new ViewHolder();

            ImageView orderPhoto = (ImageView) convertView.findViewById(R.id.order_hospital_imageView);
            TextView orderContent = (TextView) convertView.findViewById(R.id.order_first_listView_content);
            //将view的控件对象保存在viewHolder中、
            viewHolder.orderPhoto=orderPhoto;
            viewHolder.orderContent=orderContent;

            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        HospitalTbl hospitalTbl=objects.get(position);
        //读取图片
        if(hospitalTbl.getHospitalPhoto()!=null){
            getImage(hospitalTbl.getHospitalPhoto(),viewHolder.orderPhoto);
        }

        viewHolder.orderContent.setText(hospitalTbl.getHospitalSname());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //记录最后一次访问的医院
                updateHospitalId(objects.get(position).getHospitalId());

                intent=new Intent(context,HospitalBriefActivity.class);
                bundle=new Bundle();
                bundle.putInt("hospitalId",objects.get(position).getHospitalId());
                intent.putExtras(bundle);//传递选择的医院ID
                context.startActivity(intent);


            }
        });
        return convertView;
    }

   public class ViewHolder{
        ImageView orderPhoto;
        TextView orderContent;

    }

    //再次访问服务器获取图片
    public void getImage(String position,ImageView imageView){
        String photoUrl=IpChangeAddress.ipChangeAddress+position;
        ImageOptions imageOptions=new ImageOptions.Builder()
                .setCircular(true)
                .setCrop(true).build();

        x.image().bind(imageView,photoUrl,imageOptions);
    }


    private void updateHospitalId(int hospitalId){
        myApplication= (MyApplication) context.getApplicationContext();
        int userId=myApplication.getUserTbl().getUserId();

        String stl=IpChangeAddress.ipChangeAddress+"HospitalUpdateServlet";
        RequestParams requestParams=new RequestParams(stl);
        requestParams.addQueryStringParameter("flag",2+"");
        requestParams.addQueryStringParameter("userId",userId+"");
        requestParams.addQueryStringParameter("hospitalId",hospitalId+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

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
