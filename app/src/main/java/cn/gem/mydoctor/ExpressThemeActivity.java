package cn.gem.mydoctor;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.application.MyApplication;
import cn.gem.entity1.ModuleTbl;
import cn.gem.entity1.ThemeTbl;
import cn.gem.util.CommonAdapter;
import cn.gem.util.MyGridView;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;


public class ExpressThemeActivity extends AppCompatActivity {


    public static final int MYREQUESECODE = 1;
    public static final int MYREQUESECODG = 5;
    Integer count = 0;
    List<String> fileList = new ArrayList<>();
    CommonAdapter<String> adapter;
    int moduleId = 1;
    ModuleTbl moduleTbl;
    @InjectView(R.id.iv_quxiao)
    TextView ivQuxiao;
    @InjectView(R.id.tv_expresstheme)
    TextView tvExpresstheme;
    @InjectView(R.id.tv_express)
    TextView tvExpress;
    @InjectView(R.id.rl_Express_theme)
    RelativeLayout rlExpressTheme;
    @InjectView(R.id.tv_hui)
    TextView tvHui;
    @InjectView(R.id.et_themetitle)
    EditText etThemetitle;
    @InjectView(R.id.ll_title_theme)
    LinearLayout llTitleTheme;
    @InjectView(R.id.tv_hui1)
    TextView tvHui1;
    @InjectView(R.id.et_themecontext)
    EditText etThemecontext;
    @InjectView(R.id.iv_addphoto)
    ImageView ivAddphoto;
    @InjectView(R.id.iv_place)
    ImageView ivPlace;
    @InjectView(R.id.tv_placeyourself)
    TextView tvPlaceyourself;
    @InjectView(R.id.gv_themePhoto)
    MyGridView gvThemePhoto;
    @InjectView(R.id.ll_weizhi)
    RelativeLayout llWeizhi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_express_theme);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        moduleId = bundle.getInt("moduleId");
        Log.i("ExpressThemeActivity", "ExpressThemeActivity: onCreate111" + moduleId);

    }

    public void insertTheme() {
        String url = NetUtil.url + "InsertThemeServlet";
        RequestParams requestParams = new RequestParams(url);
        moduleTbl = new ModuleTbl(moduleId);
        ThemeTbl themeTbl = new ThemeTbl(moduleTbl, ((MyApplication) getApplication()).getUserTbl1(), etThemetitle.getText().toString(), etThemecontext.getText().toString(), new Timestamp(System.currentTimeMillis()));
        Gson gson = new Gson();
        String jsonTheme = gson.toJson(themeTbl);
        requestParams.addBodyParameter("themeInfo", jsonTheme);
        //bianli
        if (fileList.size() > 0) {
            for (int i = 0; i < fileList.size(); i++) {
                Bitmap bitmap = BitmapFactory.decodeFile(fileList.get(i));
                File file = new File(Environment.getExternalStorageDirectory(), getPhotoFileName() + i + ".jpg");
                FileOutputStream fos = null;
                //图片按比例大小压缩
                try {
                    fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                requestParams.setMultipart(true);
                Log.i("ReleaseActivity", "onClick: ----" + "---" + file);
                requestParams.addBodyParameter("file" + i, file);
            }
        }
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

    //shipeiqi
    public void gridviewSetadapter() {
        Log.i("ExpressThemeActivity", "ExpressThemeActivity: gridviewSetadapter======");
        if (adapter == null) {
            adapter = new CommonAdapter<String>(this, fileList, R.layout.gridview_item) {
                @Override
                public void convert(ViewHolder viewHolder, String s, int position) {
                    ImageView imtupian = viewHolder.getViewById(R.id.image);
                    x.image().bind(imtupian, s);
                }
            };
            gvThemePhoto.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("ExpressThemeActivity", "ExpressThemeActivity: onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        //返回地图地址
//        if (requestCode==MYREQUESECODG&&resultCode==RESULT_OK){
//            String address=data.getStringExtra("address");
//            Log.i("ReleaseActivity", "onActivityResult: ----"+"---"+address);
//            share.setAddress(address);
//            tvaddress.setText(address);
//        }

        //返回的相册集合
        if (requestCode == MYREQUESECODE && resultCode == RESULT_OK) {
            Log.i("ExpressThemeActivity", "ExpressThemeActivity: onActivityResult" + fileList.size());
            List<String> newlist = new ArrayList<>();
            newlist = data.getStringArrayListExtra("image");
            fileList.clear();
            fileList.addAll(newlist);
            if (fileList != null) {
                gridviewSetadapter();

            }
        }
    }

    @OnClick({R.id.iv_quxiao, R.id.tv_express, R.id.iv_addphoto})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_quxiao:
                finish();
                break;
            case R.id.tv_express:
                insertTheme();
                finish();

                break;
            case R.id.iv_addphoto:
                Intent intent = new Intent(this, ImageListActivity.class);
                startActivityForResult(intent, MYREQUESECODE);
                break;
        }
    }


}


