package cn.gem.mydoctor;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class jifen_Activity extends Activity {



    @InjectView(R.id.button_jifen)
    Button buttonJifen;
    @InjectView(R.id.toolbar_jifen)
    Toolbar toolbarJifen;
    @InjectView(R.id.activity_jifen_)
    RelativeLayout activityJifen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jifen_);
        ButterKnife.inject(this);
         toolbarJifen= (Toolbar) findViewById(R.id.toolbar_jifen);
         toolbarJifen.setTitle("");
        toolbarJifen.setNavigationIcon(R.mipmap.fanghui_baise);
        toolbarJifen.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.button_jifen)
    public void onClick() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.duihua,
                (ViewGroup) findViewById(R.id.dialog));

        new AlertDialog.Builder(this).setTitle("添加积分兑换码").setView(layout)
                .setPositiveButton("确定", null).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        })
                .setNegativeButton("取消", null).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }).show();


    }
}
