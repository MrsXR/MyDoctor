package cn.gem.mydoctor;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.application.MyApplication;

import cn.gem.entity1.ModuleTbl;
import cn.gem.entity1.ThemeTbl;
import cn.gem.util.NetUtil;


public class ExpressThemeActivity extends AppCompatActivity {

    ModuleTbl moduleTbl;
    @InjectView(R.id.iv_quxiao)
    ImageView ivQuxiao;
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
    @InjectView(R.id.ll_context)
    LinearLayout llContext;
    @InjectView(R.id.iv_photo)
    ImageView ivPhoto;
    @InjectView(R.id.iv_photo1)
    ImageView ivPhoto1;
    @InjectView(R.id.iv_place)
    ImageView ivPlace;
    @InjectView(R.id.tv_placeyourself)
    TextView tvPlaceyourself;
    @InjectView(R.id.rg_dingwei)
    RadioGroup rgDingwei;
    @InjectView(R.id.ll_weizhi)
    RelativeLayout llWeizhi;
    private File file;
    private Uri imageUri;
    String items[] = {"相册选择", "拍照"};

    public static final int SELECT_PIC = 11;//相册选择
    public static final int TAKE_PHOTO = 12;//拍照
    public static final int CROP = 13;//caijian


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_theme);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        moduleTbl=intent.getParcelableExtra("moduleTbl");
        //判断SD卡  是否存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
            imageUri = Uri.fromFile(file);

        }
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }


    @OnClick({R.id.iv_quxiao, R.id.tv_express, R.id.iv_photo, R.id.rg_dingwei})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_quxiao:
                finish();
                break;
            case R.id.tv_express:
                if(etThemetitle.getText().toString().trim()==null){
                    Toast.makeText(ExpressThemeActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
                 }else {
                    uploadImage();
                    finish();
                }


                break;
            case R.id.iv_photo:
                new AlertDialog.Builder(this).setTitle("选择").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //相册选择
                                Intent intent = new Intent(Intent.ACTION_PICK, null);
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        "image/*");
                                startActivityForResult(intent, SELECT_PIC);
                                break;
                            case 1:
                                //拍照:
                                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                startActivityForResult(intent2, TAKE_PHOTO);
                                break;
                        }

                    }
                }).show();
                break;
            case R.id.rg_dingwei:
                break;
        }
    }
//

    public void crop(Uri uri) {
        //  intent.setType("image/*");
        //裁剪
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELECT_PIC:
                if (data != null) {
                    crop(data.getData());
                }
                break;
            case TAKE_PHOTO:
                crop(Uri.fromFile(file));
                break;
            case CROP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bitmap = extras.getParcelable("data");
                        showImage(bitmap);

                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //显示图片，上传服务器
    public void showImage(Bitmap bitmap) {

        ivPhoto1.setImageBitmap(bitmap);
        //iv显示图片
        saveImage(bitmap);//保存文件
        // uploadImage();//上传服务器
    }

    //将bitmap保存在文件中
    public void saveImage(Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
    }

    public void uploadImage() {
        String url = NetUtil.url + "InsertThemeServlet";
        RequestParams requestParams = new RequestParams(url);
        Log.i("ExpressThemeActivity", "ExpressThemeActivity: uploadImage" + url);
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file", file);
        ThemeTbl themeTbl = new ThemeTbl(moduleTbl, ((MyApplication) getApplication()).getUserTbl(), etThemetitle.getText().toString(), etThemecontext.getText().toString(), new Timestamp(System.currentTimeMillis()));
        Gson gson = new Gson();
        String ThemeJson = gson.toJson(themeTbl);
        requestParams.addBodyParameter("themeInfo", ThemeJson);
        Log.i("ExpressThemeActivity", "ExpressThemeActivity: uploadImage" + themeTbl);
        Log.i("ExpressThemeActivity", "ExpressThemeActivity: uploadImage" + requestParams);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("ExpressThemeActivity", "ExpressThemeActivity: onSuccess" + result);
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


