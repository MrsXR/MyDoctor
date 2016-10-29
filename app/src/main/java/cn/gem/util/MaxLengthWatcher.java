package cn.gem.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sony on 2016/10/28.
 */
//输入文字的监听事件
public class MaxLengthWatcher implements TextWatcher {

    int maxLength;
    TextView textView;
    Context context;

    public MaxLengthWatcher(int maxLength, TextView tv, Context context) {
        this.maxLength=maxLength;
        this.textView=tv;
        this.context=context;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int k=maxLength-s.length();
        if(textView!=null) {
            textView.setText("还有" + "" + k + "个字");
        }
        if (k==0){
            Toast.makeText(context,"输入文字达到上限",Toast.LENGTH_LONG).show();
        }

    }
}
