package cn.gem.mydoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.weight.login_edittext;


public class zaixianzixun_Activity extends AppCompatActivity implements View.OnClickListener{

    @InjectView(R.id.image_zixu_fanhui)
    ImageButton imageZixuFanhui;
    @InjectView(R.id.zaixianzixun_toolbar)
    Toolbar zaixianzixunToolbar;
    @InjectView(R.id.zaixianzixun_textview1)
    TextView zaixianzixunTextview1;
    @InjectView(R.id.linearlayout1)
    LinearLayout linearlayout1;
    @InjectView(R.id.textview_jifen)
    TextView textviewJifen;
    @InjectView(R.id.linearlayout2)
    LinearLayout linearlayout2;
    @InjectView(R.id.textview_huanzhe)
    TextView textviewHuanzhe;
    @InjectView(R.id.linearlayout_huanzhe)
    LinearLayout linearlayoutHuanzhe;
    @InjectView(R.id.textview_dizhi)
    TextView textviewDizhi;
    @InjectView(R.id.linearlayout_shichang)
    LinearLayout linearlayoutShichang;
    @InjectView(R.id.textview_miaoshu)
    TextView textviewMiaoshu;
    @InjectView(R.id.linearlayout_maioshu)
    LinearLayout linearlayoutMaioshu;
    @InjectView(R.id.textview_miaoshu1)
    login_edittext textviewMiaoshu1;
    @InjectView(R.id.linearlayout_huanzhe1)
    LinearLayout linearlayoutHuanzhe1;
    @InjectView(R.id.textview_bingqin)
    TextView textviewBingqin;
    @InjectView(R.id.linearlayout_bingqin)
    LinearLayout linearlayoutBingqin;
    @InjectView(R.id.textview_bingqin1)
    login_edittext textviewBingqin1;
    @InjectView(R.id.linearlayout_bingqing1)
    LinearLayout linearlayoutBingqing1;
    @InjectView(R.id.textview_bingqinxiangxi)
    login_edittext textviewBingqinxiangxi;
    @InjectView(R.id.linearlayout_bingqing14)
    LinearLayout linearlayoutBingqing14;
    @InjectView(R.id.imagebutton_shezhi)
    ImageButton imagebuttonShezhi;
    @InjectView(R.id.textview_shezhi)
    TextView textviewShezhi;
    @InjectView(R.id.linearlayout_chuanzhaopian)
    LinearLayout linearlayoutChuanzhaopian;
    @InjectView(R.id.textview_tishi)
    TextView textviewTishi;
    @InjectView(R.id.linearlayout_tishi)
    LinearLayout linearlayoutTishi;
    @InjectView(R.id.checkbox)
    CheckBox checkbox;
    @InjectView(R.id.button_tijiao)
    Button buttonTijiao;
    @InjectView(R.id.linearlayout_button)
    LinearLayout linearlayoutButton;
    @InjectView(R.id.linearlayout)
    LinearLayout linearlayout;
    @InjectView(R.id.activity_zaixianzixun_)
    RelativeLayout activityZaixianzixun;
    private PopupWindow mPopWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zaixianzixun_);
        ButterKnife.inject(this);
        showpopupwindows();

    }

    public void showpopupwindows(){
        View contentView = LayoutInflater.from(zaixianzixun_Activity.this).inflate(R.layout.popupwindow_itme, null);

               mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                mPopWindow.setContentView(contentView);

        TextView tv1 = (TextView)contentView.findViewById(R.id.pop_computer);
        TextView tv2 = (TextView)contentView.findViewById(R.id.pop_financial);
        TextView tv3 = (TextView)contentView.findViewById(R.id.pop_manage);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        //显示PopupWindow
        View rootview = LayoutInflater.from(zaixianzixun_Activity.this).inflate(R.layout.activity_zaixianzixun_, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }

    @OnClick({R.id.image_zixu_fanhui, R.id.linearlayout_huanzhe, R.id.linearlayout_shichang, R.id.linearlayout_chuanzhaopian, R.id.button_tijiao,R.id.pop_computer,R.id.pop_financial,R.id.pop_manage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_zixu_fanhui:
                finish();
                break;
            case R.id.linearlayout_huanzhe:
                Intent intent=new Intent(this,dangan_Activity.class);
                startActivity(intent);
                break;
            case R.id.linearlayout_shichang:

                break;
            case R.id.linearlayout_chuanzhaopian:
                   //传照片
                break;
            case R.id.button_tijiao:
                   //提交
                break;
            case R.id.pop_computer:
                  //第一个
                break;
            case R.id.pop_financial:
                //第二个
                break;
            case R.id.pop_manage:
                //第三个
                break;
        }
    }
}
