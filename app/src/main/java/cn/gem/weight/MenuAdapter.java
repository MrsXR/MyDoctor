/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.gem.weight;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import cn.gem.entity.UserRecordTbl;
import cn.gem.mydoctor.R;
import cn.gem.mydoctor.dangan_xiugaiActivity;
import cn.gem.util.CommonQuantity;
import cn.gem.util.IpChangeAddress;
import cn.gem.util.OnItemClickListener;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class MenuAdapter extends SwipeMenuAdapter<MenuAdapter.DefaultViewHolder> {


    List<UserRecordTbl> list ;
    Context context;

    private OnItemClickListener mOnItemClickListener;

    public MenuAdapter(List<UserRecordTbl> list, Context context) {
        this.list = list;
        this.context=context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.userrecord_itme, parent, false);
    }

    @Override
    public MenuAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView,context);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.DefaultViewHolder holder, int position) {
        holder.setData(list.get(position));
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        String age;
        String name;
        String num;
        String userrecordid;
        String string;
        Context context;
        TextView tv1;
        TextView tv2;
        ImageButton imagebutton;
        TextView tv3;
        ImageView imageView;

        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView,Context context) {
            super(itemView);
            this.context=context;
            itemView.setOnClickListener(this);

            //找控件
            tv1= (TextView) itemView.findViewById(R.id.dangan_list_item_tv_xingming);
            tv2= (TextView) itemView.findViewById(R.id.dangan_list_item_tv_shouji);
            imagebutton= (ImageButton) itemView.findViewById(R.id.dangan_xiugai_button);
            tv3= (TextView) itemView.findViewById(R.id.dangan_list_item_tv_shenfen);
            imageView= (ImageView) itemView.findViewById(R.id.dangan_list_item_tupian);


        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(final UserRecordTbl title) {
            name=title.getUserrecordName();
            age=title.getUserrecordAge()+"";
            num=title.getUserrecordPhone();
            userrecordid=title.getUserrecordId()+"";

            tv1.setText(name);
            tv2.setText(title.getUserrecordPhone());
            //修改
            imagebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,dangan_xiugaiActivity.class);
                    Bundle bundle=new Bundle();
                    intent.putExtra("flag", CommonQuantity.SECOND);
                    intent.putExtra("user_record_tbl",title);
                    context.startActivity(intent);
                }
            });


            switch (title.getUserrecordIdentity()){
                case 0:
                    string="本人";
                    break;
                case 1:
                    string="父母";
                    break;
                case 2:
                    string="子女";
                    break;
                case 3:
                    string="朋友";
                    break;
                case 4:
                    string="其他";
                    break;

            }
            tv3.setText(string);

            String str= IpChangeAddress.ipChangeAddress+title.getUserrecordPhoto();
            ImageOptions imageOptions = new ImageOptions.Builder()
                    .setSquare(true)
                    .setCrop(true).setSize(100,100).build();
            x.image().bind(imageView,str,imageOptions);


        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
