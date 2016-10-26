package cn.gem.mydoctor;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.util.NetUtil;

import static cn.gem.mydoctor.dangantianjia_Activity.CROP_PHOTO;
import static cn.gem.mydoctor.dangantianjia_Activity.SELECT_PIC;
import static cn.gem.mydoctor.dangantianjia_Activity.TAKE_PHOTO;


public class dangan_xiugaiActivity extends AppCompatActivity {

    @InjectView(R.id.image_yuyue)
    ImageButton imageYuyue;
    @InjectView(R.id.bianjidanganxinxi_button)
    Button bianjidanganxinxiButton;
    @InjectView(R.id.toolbar_dangantianjia)
    Toolbar toolbarDangantianjia;
    @InjectView(R.id.danganxiugai_imageview_touxiang)
    ImageView danganxiugaiImageviewTouxiang;
    @InjectView(R.id.danganxiugai_liner_touxiang)
    LinearLayout danganxiugaiLinerTouxiang;
    @InjectView(R.id.danganxiugai_edittext_xingming)
    EditText danganxiugaiEdittextXingming;
    @InjectView(R.id.danganxiugai_liner_xingming)
    LinearLayout danganxiugaiLinerXingming;
    @InjectView(R.id.danganxiugai_edittext_shouji)
    EditText danganxiugaiEdittextShouji;
    @InjectView(R.id.danganxiugai_liner_shouji)
    LinearLayout danganxiugaiLinerShouji;
    @InjectView(R.id.danganxiugai_edittext_nianlin)
    EditText danganxiugaiEdittextNianlin;
    @InjectView(R.id.danganxiugai_liner_nianlin)
    LinearLayout danganxiugaiLinerNianlin;
    @InjectView(R.id.danganxiugai_edittext_xinbie)
    TextView danganxiugaiEdittextXinbie;
    @InjectView(R.id.danganxiugai_liner_xinbie)
    LinearLayout danganxiugaiLinerXinbie;
    @InjectView(R.id.danganxiugai_edittext_shenfenzheng)
    EditText danganxiugaiEdittextShenfenzheng;
    @InjectView(R.id.danganxiugai_shenfenzheng)
    LinearLayout danganxiugaiShenfenzheng;
    @InjectView(R.id.danganxiugai_edittext_shengfen)
    TextView danganxiugaiEdittextShengfen;
    @InjectView(R.id.danganxiugai_liner_shenfen)
    LinearLayout danganxiugaiLinerShenfen;
    @InjectView(R.id.danganxiugai_edittext_shenggao)
    EditText danganxiugaiEdittextShenggao;
    @InjectView(R.id.danganxiugai_liner_shenggao)
    LinearLayout danganxiugaiLinerShenggao;
    @InjectView(R.id.danganxiugai_edittext_tizhong)
    EditText danganxiugaiEdittextTizhong;
    @InjectView(R.id.danganxiugai_liner_tizhong)
    LinearLayout danganxiugaiLinerTizhong;
    @InjectView(R.id.danganxiugai_edittext_button)
    Button danganxiugaiEdittextButton;
    @InjectView(R.id.activity_dangan_xiugai)
    RelativeLayout activityDanganXiugai;

    int user_sex;
    int userrecordid;
    private Uri imageUri;
    int user_identity;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangan_xiugai);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String num = intent.getStringExtra("num");
        String age = intent.getStringExtra("age");

        userrecordid = Integer.parseInt(intent.getStringExtra("userrecordid"));

        Log.i("aaa", "onCreate: "+userrecordid);

        danganxiugaiEdittextXingming.setText(name);
        danganxiugaiEdittextNianlin.setText(age);
        danganxiugaiEdittextShouji.setText(num);
    }

    public void initio() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "user_record_update_servlet");
        //String userid = new NetUtil().getUser().getUserid() + "";


        String username = danganxiugaiEdittextXingming.getText() + "";
        String usertouxiang = null;
        String usershouji = danganxiugaiEdittextShouji.getText() + "";
        String userage = danganxiugaiEdittextNianlin.getText() + "";
        String usercard = danganxiugaiEdittextShenfenzheng.getText() + "";
        String usersex = user_sex+"";
        String usershenfen =user_identity + "";
        String usershenggao = danganxiugaiEdittextShenggao.getText() + "";
        String usertizhong = danganxiugaiEdittextTizhong.getText() + "";


        requestParams.addQueryStringParameter("userrecordid",userrecordid+"");
        requestParams.addQueryStringParameter("username", username);
        requestParams.addQueryStringParameter("usertouxiang", usertouxiang);
        requestParams.addQueryStringParameter("usershouji", usershouji);
        requestParams.addQueryStringParameter("userage", userage);
        requestParams.addQueryStringParameter("usercard", usercard);
        requestParams.addQueryStringParameter("usersex", usersex);
        requestParams.addQueryStringParameter("usershenfen", usershenfen);
        requestParams.addQueryStringParameter("usershenggao", usershenggao);
        requestParams.addQueryStringParameter("usertizhong", usertizhong);

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(dangan_xiugaiActivity.this, "修改成功", Toast.LENGTH_SHORT).show();

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

    /*
        @OnClick({R.id.bianjidanganxinxi_button, R.id.danganxiugai_edittext_button})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bianjidanganxinxi_button:
                    initio();
                    break;
                case R.id.danganxiugai_edittext_button:
                    initof();
                    break;
            }
        }
    */
    public void initof() {
        RequestParams r = new RequestParams(NetUtil.url + "user_record_drop_servlet");
        r.addQueryStringParameter("userrecordid", userrecordid+"");
        x.http().get(r, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(dangan_xiugaiActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
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


    @OnClick({R.id.bianjidanganxinxi_button, R.id.danganxiugai_liner_touxiang, R.id.danganxiugai_liner_xingming, R.id.danganxiugai_liner_shouji, R.id.danganxiugai_liner_nianlin, R.id.danganxiugai_liner_xinbie, R.id.danganxiugai_shenfenzheng, R.id.danganxiugai_liner_shenfen, R.id.danganxiugai_liner_shenggao, R.id.danganxiugai_liner_tizhong, R.id.danganxiugai_edittext_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bianjidanganxinxi_button:
                initio();
                break;
            case R.id.danganxiugai_liner_touxiang:
                new AlertDialog.Builder(this).setTitle("头像选择").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[]{"从手机相册中选择", "相机"}, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                File file = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                                imageUri = Uri.fromFile(file);
                                if (which == 0) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_PICK);
                                    intent.setType("image/*");
                                    //裁剪
                                    intent.putExtra("crop", "true");
                                    //宽高比例
                                    intent.putExtra("aspectX", 1);
                                    intent.putExtra("aspectY", 1);
                                    //定义宽和高
                                    intent.putExtra("outputX", 300);
                                    intent.putExtra("outputY", 300);
                                    //图片是否缩放
                                    intent.putExtra("scale", true);
                                    //是否要返回值
                                    intent.putExtra("return-data", false);
                                    //把图片存放到imageUri
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                    //图片输出格式
                                    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                                    intent.putExtra("noFaceDetection", true); // no face detection
                                    startActivityForResult(intent, SELECT_PIC);
                                } else {//拍照
                                    // api guide: cemera
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                    startActivityForResult(intent, TAKE_PHOTO);
                                }
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.danganxiugai_liner_xingming:
                break;
            case R.id.danganxiugai_liner_shouji:
                break;
            case R.id.danganxiugai_liner_nianlin:
                break;
            case R.id.danganxiugai_liner_xinbie:
                new AlertDialog.Builder(this).setTitle("选择性别").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[]{"男", "女"}, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    user_sex = 0;
                                } else {
                                    user_sex = 1;
                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.danganxiugai_shenfenzheng:
                break;
            case R.id.danganxiugai_liner_shenfen:
                new AlertDialog.Builder(this).setTitle("选择身份").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[]{"本人", "父母", "子女", "亲属", "朋友"}, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        user_identity = 0;
                                        break;
                                    case 1:
                                        user_identity = 1;
                                        break;
                                    case 2:
                                        user_identity = 2;
                                        break;
                                    case 3:
                                        user_identity = 3;
                                        break;
                                    case 4:
                                        user_identity = 4;
                                        break;

                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.danganxiugai_liner_shenggao:
                break;
            case R.id.danganxiugai_liner_tizhong:
                break;
            case R.id.danganxiugai_edittext_button:
                initof();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.i("MainActivity", "select pic error!");
            return;
        }
        if (requestCode == SELECT_PIC) {
            if (imageUri != null) {
                InputStream is = null;
                try {
                    //读取图片到io流
                    is = getContentResolver().openInputStream(imageUri);
                    //内存中的图片
                    Bitmap bm = BitmapFactory.decodeStream(is);
                    iv.setImageBitmap(bm);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == TAKE_PHOTO) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(imageUri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, CROP_PHOTO);//启动裁剪
        } else if (requestCode == CROP_PHOTO) {//获取裁剪后的结果
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Bitmap bm = bundle.getParcelable("data");//  bundle.putParceable("data",bm);
//				bm.compress(CompressFormat.JPEG, 100, new FileOutputStream());
                iv.setImageBitmap(bm);
            }
        }
    }
}
