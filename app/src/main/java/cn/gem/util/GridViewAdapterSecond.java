package cn.gem.util;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Map;

import cn.gem.mydoctor.R;

/**
 * Created by sony on 2016/10/23.
 */
public class GridViewAdapterSecond extends BaseAdapter {
    private Context mContext;

    private int mBottomLineColor = Color.parseColor("#17abe3");
    Map<Integer,Integer> mp=null;

    public GridViewAdapterSecond(Context mContext, Map<Integer,Integer> mp){
        this.mContext=mContext;
        this.mp=mp;

    }


    @Override
    public int getCount() {

        return 24;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //初始化界面
        TextView imageView;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_view_text, null);
            imageView= (TextView) convertView.findViewById(R.id.gridView_text);
            if (position == 0) {
                imageView.setText("上午");
            }
            if (position == 8) {
                imageView.setText("下午");
            }
            if (position == 16) {
                imageView.setText("晚上");
            }


            for (Map.Entry<Integer, Integer> entry : mp.entrySet()) {
                if (entry.getKey()==position){
                    imageView.setText("点击预约");
                    imageView.setTextColor(mBottomLineColor);
                }
            }

        }else{
            imageView = (TextView) convertView;
        }

        return imageView;
    }

}
