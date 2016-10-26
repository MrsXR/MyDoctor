package cn.gem.mydoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HintActivity extends AppCompatActivity {


    @InjectView(R.id.toolbar_themehint)
    Toolbar toolbarThemehint;
    @InjectView(R.id.rb_answerme)
    RadioButton rbAnswerme;
    @InjectView(R.id.rb_pinglunme)
    RadioButton rbPinglunme;
    @InjectView(R.id.rb_guanyume)
    RadioButton rbGuanyume;
    @InjectView(R.id.ll_me)
    LinearLayout llMe;
    @InjectView(R.id.img2)
    ImageView img2;
    @InjectView(R.id.ll_im2)
    LinearLayout llIm2;
    @InjectView(R.id.lv_themehint)
    ListView lvThemehint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        ButterKnife.inject(this);
        toolbarThemehint.setTitle("论坛提醒");
        setSupportActionBar(toolbarThemehint);
        toolbarThemehint.setNavigationIcon(R.mipmap.fanhui1);

        toolbarThemehint.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


    @OnClick({R.id.rb_answerme, R.id.rb_pinglunme, R.id.rb_guanyume})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_answerme:
                break;
            case R.id.rb_pinglunme:
                break;
            case R.id.rb_guanyume:
                break;
        }
    }
}
