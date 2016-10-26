package cn.gem.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.gem.entity.ThemeTbl;
import cn.gem.mydoctor.R;

/**
 * Created by panwenpeng on 2016/9/25.
 */
public class MyBaseAdapter extends BaseAdapter {
    List<ThemeTbl> themeTbls;
    Context context;
    public  MyBaseAdapter(List<ThemeTbl> themeTbls, Context context){
        this.themeTbls=themeTbls;
        this.context=context;
    }


    //Listview中有多少item
    @Override
    public int getCount() {
        return themeTbls.size();
    }
    //每个position位置的item对象
    @Override
    public Object getItem(int position) {
        return themeTbls.get(position);
    }
    //item唯一标识
    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     *
     * parent:listview
     * position:item位置
     *return 某个item对应的view
     * 没创建一个item getview就执行一次
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       //解析布局文件得到view
        View v=LayoutInflater.from(context).inflate(R.layout.base_item,null);
        //找到view控件

        TextView themeName= (TextView) v.findViewById(R.id.textView);
        TextView themeTime= (TextView) v.findViewById(R.id.textView2);
        TextView themeNum= (TextView) v.findViewById(R.id.textView3);
        //给控件赋值

         //获取position位置item显示的学生信息
        ThemeTbl themeTbl=themeTbls.get(position);
        themeName.setText(themeTbl.getThemeName());
        themeName.setText((CharSequence) themeTbl.getThemeTime());
        themeName.setText(themeTbl.getAnswerNum());

        return v;
    }
}
