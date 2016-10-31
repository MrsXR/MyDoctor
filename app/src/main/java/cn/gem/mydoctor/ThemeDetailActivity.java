package cn.gem.mydoctor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.application.MyApplication;
import cn.gem.entity1.ModuleTbl;
import cn.gem.entity.PraiseTbl;
import cn.gem.entity1.ThemeDetailTbl;
import cn.gem.entity1.ThemeTbl;
import cn.gem.util.CommonAdapter;
import cn.gem.util.IpChangeAddress;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;

public class ThemeDetailActivity extends AppCompatActivity {
    List<PraiseTbl> praiseTbls = new ArrayList<PraiseTbl>();
    ThemeTbl themeTbl;
    ModuleTbl moduleTbl;
    int themeId;
    int praiseNum;//点赞数量
    ThemeDetailTbl themeDetailTbl1;//所评论的对象
    ThemeDetailTbl themeDetailTbl2;//所删除的对象
    CommonAdapter<ThemeDetailTbl> themeDetailTblCommonAdapter;
    List<ThemeDetailTbl> themeDetailTbls = new ArrayList<ThemeDetailTbl>();//评论集合
    String itme[] = {"删除该条评论"};
    @InjectView(R.id.toolbar_detail)
    Toolbar toolbarDetail;
    @InjectView(R.id.tv_themename)
    TextView tvThemename;
    @InjectView(R.id.im_theme_readnumber1)
    ImageView imThemeReadnumber1;
    @InjectView(R.id.tv_look)
    TextView tvLook;
    @InjectView(R.id.iv_answertheme_number1)
    ImageView ivAnswerthemeNumber1;
    @InjectView(R.id.tv_pinglun)
    TextView tvPinglun;
    @InjectView(R.id.tv_module)
    TextView tvModule;
    @InjectView(R.id.rl_3)
    RelativeLayout rl3;
    @InjectView(R.id.rl_themename1)
    RelativeLayout rlThemename1;
    @InjectView(R.id.lv_theme_detail)
    ListView lvThemeDetail;
    @InjectView(R.id.iv_zan)
    ImageView ivZan;
    @InjectView(R.id.rl_dibu)
    LinearLayout rlDibu;
    @InjectView(R.id.iv_pinglun)
    ImageView ivPinglun;
    @InjectView(R.id.iv_shoucang)
    ImageView ivShoucang;
    @InjectView(R.id.ll_dibu)
    LinearLayout llDibu;
    @InjectView(R.id.tv_themecontent)
    TextView tvThemecontent;
    @InjectView(R.id.iv_photo1)
    ImageView ivPhoto1;
    @InjectView(R.id.iv_photo2)
    ImageView ivPhoto2;
    @InjectView(R.id.iv_photo3)
    ImageView ivPhoto3;
    @InjectView(R.id.hhhh)
    TextView hhhh;
    @InjectView(R.id.tv_zan)
    TextView tvZan;
    @InjectView(R.id.tv_dibupinglun)
    TextView tvDibupinglun;
    @InjectView(R.id.tv_fenxiang)
    TextView tvFenxiang;
    @InjectView(R.id.iv_zanis)
    ImageView ivZanis;
    @InjectView(R.id.et_huifulouzhu)
    EditText etHuifulouzhu;
    @InjectView(R.id.bt_fasong)
    Button btFasong;
    @InjectView(R.id.rl_edit_dibu)
    RelativeLayout rlEditDibu;
    @InjectView(R.id.et_huifu)
    EditText etHuifu;
    @InjectView(R.id.bt_fasong1)
    Button btFasong1;
    @InjectView(R.id.rl_edit_huifu)
    RelativeLayout rlEditHuifu;
    @InjectView(R.id.ll_baohan)
    LinearLayout llBaohan;
    @InjectView(R.id.user_photo)
    ImageView userPhoto;
    @InjectView(R.id.rl_user)
    RelativeLayout rlUser;
    @InjectView(R.id.tv_username)
    TextView tvUsername;
    @InjectView(R.id.ll_photo)
    LinearLayout llPhoto;
    @InjectView(R.id.slv_content)
    ScrollView slvContent;
    @InjectView(R.id.rl_content)
    RelativeLayout rlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_detail);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        themeTbl = bundle.getParcelable("themeTbl");
        themeId = themeTbl.getThemeId();
        //getData();
        //getDataFromPraise();
        moduleTbl = bundle.getParcelable("moduleTbl");
        //初始化界面
        initDate();
        toolbarDetail.setTitle("话题详情");
        setSupportActionBar(toolbarDetail);
        toolbarDetail.setNavigationIcon(R.mipmap.fanhui5);
        //设置导航图标点击事件
        toolbarDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //item点击事件
        toolbarDetail.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.fasong:
                        Intent intent1 = new Intent(ThemeDetailActivity.this, ExpressThemeActivity.class);
                        intent1.putExtra("moduleTbl", moduleTbl);
                        startActivity(intent1);
                        break;
                    case R.id.invite:
                        break;
                    case R.id.lookdoctor:
                        break;
                    case R.id.looklouzhu:
                        break;
                }
                return false;
            }
        });
    }

    //某个item被选中对应的事件：例如只看楼主只看医生
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick({R.id.iv_zan, R.id.iv_pinglun, R.id.iv_shoucang, R.id.tv_module, R.id.iv_zanis, R.id.bt_fasong, R.id.bt_fasong1,R.id.rl_content})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_zan:
                ivZan.setVisibility(View.GONE);
                ivZanis.setVisibility(View.VISIBLE);
                insertThemePraise();
                tvZan.setText(praiseNum + 1 + "");
                praiseNum++;
                break;
            case R.id.iv_zanis:
                ivZan.setVisibility(View.VISIBLE);
                ivZanis.setVisibility(View.GONE);
                deleteThemePraise();
                tvZan.setText(praiseNum - 1 + "");
                praiseNum--;
                break;
            case R.id.iv_pinglun:
                rlEditDibu.setVisibility(View.VISIBLE);
                llDibu.setVisibility(View.GONE);
                InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.showSoftInput(ivPinglun, InputMethodManager.RESULT_SHOWN);
                imm1.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                break;
            case R.id.iv_shoucang:
                break;
            case R.id.tv_module:
                break;
            case R.id.bt_fasong://回复楼主
                rlEditDibu.setVisibility(View.GONE);
                llDibu.setVisibility(View.VISIBLE);
                InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (TextUtils.isEmpty(etHuifulouzhu.getText().toString().trim())) {
                    Toast.makeText(ThemeDetailActivity.this, "评论不能为空", Toast.LENGTH_SHORT).show();
                    imm2.hideSoftInputFromWindow(etHuifulouzhu.getWindowToken(), 0);
                } else {
                    insertToThemeDetail();
                    updateAswerNum();
                    etHuifulouzhu.setText("");
                    imm2.hideSoftInputFromWindow(etHuifulouzhu.getWindowToken(), 0);
                }

                break;
            case R.id.bt_fasong1://回复别人评论
                rlEditHuifu.setVisibility(View.GONE);
                llDibu.setVisibility(View.VISIBLE);
                InputMethodManager imm3 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (TextUtils.isEmpty(etHuifu.getText().toString().trim())) {
                    Log.i("ThemeDetailActivity", "ThemeDetailActivity: onClick+++++++IF");
                    Toast.makeText(ThemeDetailActivity.this, "评论不能为空", Toast.LENGTH_SHORT).show();
                    imm3.hideSoftInputFromWindow(etHuifu.getWindowToken(), 0);
                } else {
                    insertThemeDetailDuoJi();
                    updateAswerNum();
                    etHuifu.setText("");
                    imm3.hideSoftInputFromWindow(etHuifu.getWindowToken(), 0);
                }
                break;
            case R.id.rl_content:

                break;
        }
    }

    //获取点赞表的数据
    public void getDataFromPraise() {
        String url = NetUtil.url + "QueryPraiseServlet";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("themeId", themeId + "");
        requestParams.addQueryStringParameter("userId", ((MyApplication) getApplication()).getUserTbl().getUserId() + "");
        Log.i("ThemeDetailActivity", "ThemeDetailActivity: UserId" + ((MyApplication) getApplication()).getUserTbl().getUserId());
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("ThemeDetailActivity", "ThemeDetailActivity: onSuccess:获取网络数据陈功" + result);
                Gson gson = new Gson();
                Type type = new TypeToken<List<PraiseTbl>>() {
                }.getType();
                praiseTbls = gson.fromJson(result, type);
                tvZan.setText(praiseTbls.size() + "");
                praiseNum = praiseTbls.size();
                for (PraiseTbl praise : praiseTbls) {
                    if (praise.getUserId() == ((MyApplication) getApplication()).getUserTbl().getUserId()) {
                        ivZan.setVisibility(View.GONE);
                        ivZanis.setVisibility(View.VISIBLE);
                    } else {
                        ivZan.setVisibility(View.VISIBLE);
                        ivZanis.setVisibility(View.GONE);
                    }
                }
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

    //点赞
    public void insertThemePraise() {
        String url = NetUtil.url + "InsertThemePraiseServlet";
        RequestParams requestParams = new RequestParams(url);
        PraiseTbl praiseTbl = new PraiseTbl(themeId, ((MyApplication) getApplication()).getUserTbl().getUserId());
        Gson gson = new Gson();
        String jsonThemePraiseInfo = gson.toJson(praiseTbl);
        requestParams.addBodyParameter("praiseInfo", jsonThemePraiseInfo);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
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

    //取消点赞
    public void deleteThemePraise() {
        String url = NetUtil.url + "DeleteThemePraiseServlet";
        RequestParams requestParams = new RequestParams(url);
        PraiseTbl praiseTbl = new PraiseTbl(themeId, ((MyApplication) getApplication()).getUserTbl().getUserId());
        Gson gson = new Gson();
        String jsonThemePraiseInfo = gson.toJson(praiseTbl);
        requestParams.addBodyParameter("praiseInfo", jsonThemePraiseInfo);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
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

    //初始化界面
    public void initDate() {
        tvUsername.setText(themeTbl.getUserTbl().getUserSname());
        tvThemename.setText(themeTbl.getThemeName());
        tvLook.setText(themeTbl.getLookNumber() + 1 + "");
        tvPinglun.setText(themeTbl.getAnswerNum() + "");
        tvThemecontent.setText(themeTbl.getThemeContent());
        tvDibupinglun.setText(themeTbl.getAnswerNum() + "");
        tvModule.setText(themeTbl.getModuleTbl().getModuleSname());
        String photoUrl1 =  IpChangeAddress.ipChangeAddress + themeTbl.getThemePhotoUrl1();
        ImageOptions imageOptions1 = new ImageOptions.Builder().setCrop(true).setSize(400, 400).build();
        x.image().bind(ivPhoto1, photoUrl1, imageOptions1);
        String photoUrl2 =  IpChangeAddress.ipChangeAddress+ themeTbl.getThemePhotoUrl2();
        ImageOptions imageOptions2 = new ImageOptions.Builder().setCrop(true).setSize(400, 400).build();
        x.image().bind(ivPhoto2, photoUrl2, imageOptions2);
        String photoUrl3 =  IpChangeAddress.ipChangeAddress+ themeTbl.getThemePhotoUrl3();
        ImageOptions imageOptions3 = new ImageOptions.Builder().setCrop(true).setSize(400, 400).build();
        x.image().bind(ivPhoto3, photoUrl3, imageOptions3);
        getDataFromPraise();
        getThemeDetail();

    }

    //发表一级评论(回复楼主)
    public void insertToThemeDetail() {
        String url = NetUtil.url + "InsertThemeDetailServlet";
        RequestParams requestParams = new RequestParams(url);
        ThemeDetailTbl themeDetailTbl = new ThemeDetailTbl(themeId, ((MyApplication) getApplication()).getUserTbl(), null, etHuifulouzhu.getText().toString(), new Timestamp(System.currentTimeMillis()), 1);
        Gson gson = new Gson();
        String jsonThemeDetailInfo = gson.toJson(themeDetailTbl);
        requestParams.addBodyParameter("themeDetailInfo", jsonThemeDetailInfo);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(ThemeDetailActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                getThemeDetail();
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

    //获取评论数据
    public void getThemeDetail() {
        String url = NetUtil.url + "QueryThemeDetailServlet";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("themeId", themeId + "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<ThemeDetailTbl>>() {
                }.getType();
                List<ThemeDetailTbl> newThemeDetail = new ArrayList<>();
                newThemeDetail = gson.fromJson(result, type);
                themeDetailTbls.clear();
                themeDetailTbls.addAll(newThemeDetail);
                if (themeDetailTblCommonAdapter == null) {
                    themeDetailTblCommonAdapter = new CommonAdapter<ThemeDetailTbl>(ThemeDetailActivity.this, themeDetailTbls, R.layout.theme_detail_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ThemeDetailTbl themeDetailTbl, int position) {
                            TextView tvName1 = viewHolder.getViewById(R.id.tv_name1);
                            tvName1.setText(themeDetailTbl.getUserTbl().getUserSname());
                            TextView tvParentName = viewHolder.getViewById(R.id.tv_name2);
                            TextView tvHuifu = viewHolder.getViewById(R.id.tv_dui);
                            if (themeDetailTbl.getFatherThemeDetail() == null) {
                                tvParentName.setText("");
                                tvHuifu.setVisibility(View.GONE);
                            } else {
                                tvHuifu.setVisibility(View.VISIBLE);
                                tvParentName.setText(themeDetailTbl.getFatherThemeDetail().getUserTbl().getUserSname());
                            }
                            TextView tvContent = viewHolder.getViewById(R.id.tv_themedetailcontent);
                            tvContent.setText(themeDetailTbl.getThemeDetailContent());
                        }
                    };
                    lvThemeDetail.setAdapter(themeDetailTblCommonAdapter);
                } else {
                    themeDetailTblCommonAdapter.notifyDataSetChanged();
                }

                lvThemeDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        rlEditHuifu.setVisibility(View.VISIBLE);
                        llDibu.setVisibility(View.GONE);
                        etHuifu.setHint("回复" + themeDetailTbls.get(position).getUserTbl().getUserSname());
                        themeDetailTbl1 = themeDetailTbls.get(position);
                        Log.i("ThemeDetailActivity", "ThemeDetailActivity: 点击。。。" + themeDetailTbls.get(position).getUserTbl().getUserSname());
                        InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm1.showSoftInput(etHuifu, InputMethodManager.RESULT_SHOWN);
                        imm1.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                });

                lvThemeDetail.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                        themeDetailTbl2 = themeDetailTbls.get(position);
                        new AlertDialog.Builder(ThemeDetailActivity.this).setItems(itme, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    if (themeDetailTbls.get(position).getThemeDetailIs() == 1 && themeDetailTbls.get(position).getUserTbl().getUserId() == ((MyApplication) getApplication()).getUserTbl().getUserId()) {
                                        Log.i("ThemeDetailActivity", "onClick: ppppppppppppppppppp");
                                        deleteThemeDetail();
                                    } else {
                                        Log.i("ThemeDetailActivity", "onClick: qqqqqqqqqqqqq");
                                        Toast.makeText(ThemeDetailActivity.this, "你不能删除此评论", Toast.LENGTH_SHORT).show();
                                    }
                                    Log.i("ThemeDetailActivity", "onClick:11111 "+themeDetailTbls.get(position).getUserTbl().getUserId());
                                    Log.i("ThemeDetailActivity", "onClick:2 222222"+((MyApplication) getApplication()).getUserTbl().getUserId());
                                }

                            }
                        }).show();
                        return true;
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
        });
    }

    public void insertThemeDetailDuoJi() {
        String url = NetUtil.url + "InsertThemeDetailDuoJiServlet";
        RequestParams requestParams = new RequestParams(url);
        ThemeDetailTbl themeDetailTbl = new ThemeDetailTbl(themeId, ((MyApplication) getApplication()).getUserTbl(), themeDetailTbl1, etHuifu.getText().toString(), new Timestamp(System.currentTimeMillis()), 1);
        Gson gson = new Gson();
        String jsonThemeDetailDuoJi = gson.toJson(themeDetailTbl);
        requestParams.addBodyParameter("themeDetailDuoJiInfo", jsonThemeDetailDuoJi);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(ThemeDetailActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                getThemeDetail();
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

    //修改评论数
    public void updateAswerNum() {
        String url = NetUtil.url + "InsertThemeServlet";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addBodyParameter("themeId", themeId + "");
        Log.i("ThemeDetailActivity", "ThemeDetailActivity: updateAswerNum:修改评论数" + requestParams);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("ThemeDetailActivity", "ThemeDetailActivity: onSuccess修改chenggong");
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

    public void deleteThemeDetail() {
        String url = NetUtil.url + "DeleteThemeDetailServlet";
        RequestParams requestParams = new RequestParams(url);
        ThemeDetailTbl themeDetailTbl = new ThemeDetailTbl(themeDetailTbl2.getThemeDetailId(), themeDetailTbl2.getUserTbl(), themeDetailTbl2.getThemeDetailIs());
        Log.i("ThemeDetailActivity", "ThemeDetailActivity: DELETE" + themeDetailTbl);
        Gson gson = new Gson();
        String jsonThemeDetail = gson.toJson(themeDetailTbl);
        requestParams.addBodyParameter("themeDetailInfo", jsonThemeDetail);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(ThemeDetailActivity.this, "删除评论成功", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        return super.onTouchEvent(event);
    }
}
