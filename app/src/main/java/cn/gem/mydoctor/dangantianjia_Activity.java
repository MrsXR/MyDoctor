package cn.gem.mydoctor;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.entity.UserRecordTbl;
import cn.gem.util.NetUtil;
import cn.gem.weight.PickerView;

public class dangantianjia_Activity extends Activity implements View.OnClickListener {


    public PickerView pickerView;
    public String fenshu;

    public static final int SELECT_PIC = 11;
    public static final int TAKE_PHOTO = 12;
    public static final int CROP_PHOTO = 13;

    UserRecordTbl user;
    @InjectView(R.id.tianjiadanan_touxiang)
    ImageView tianjiadananTouxiang;
    @InjectView(R.id.liner_touxiang)
    LinearLayout linerTouxiang;
    @InjectView(R.id.tianjiadanan_xingming)
    EditText tianjiadananXingming;
    @InjectView(R.id.liner_xingming)
    LinearLayout linerXingming;
    @InjectView(R.id.tianjiadanan_shouji)
    EditText tianjiadananShouji;
    @InjectView(R.id.liner_shouji)
    LinearLayout linerShouji;
    @InjectView(R.id.tianjiadanan_nianlin)
    EditText tianjiadananNianlin;
    @InjectView(R.id.liner_nianlin)
    LinearLayout linerNianlin;
    @InjectView(R.id.tianjiadanan_xiebie)
    TextView tianjiadananXiebie;
    @InjectView(R.id.liner_xinbie)
    LinearLayout linerXinbie;
    @InjectView(R.id.tianjiadanan_shenfenzheng)
    EditText tianjiadananShenfenzheng;
    @InjectView(R.id.liner_shenfenzheng)
    LinearLayout linerShenfenzheng;
    @InjectView(R.id.tianjiadanan_shengfen)
    TextView tianjiadananShengfen;
    @InjectView(R.id.liner_shenfen)
    LinearLayout linerShenfen;
    @InjectView(R.id.tianjiadanan_shenggao)
    EditText tianjiadananShenggao;
    @InjectView(R.id.liner_shenggao)
    LinearLayout linerShenggao;
    @InjectView(R.id.tianjiadanan_tizhong)
    EditText tianjiadananTizhong;
    @InjectView(R.id.liner_tizhong)
    LinearLayout linerTizhong;
    @InjectView(R.id.dangantianjia_button)
    Button dangantianjiaButton;
    @InjectView(R.id.toolbar_dangantianjia)
    Toolbar toolbarDangantianjia;
    @InjectView(R.id.activity_dangantianjia_)
    RelativeLayout activityDangantianjia;


    private Uri imageUri;
    private ImageView iv;
    int user_sex;
    int user_identity;
    File file;
    String pathurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("dangantianjia_Activity", "onCreate: 1111111");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangantianjia_);
        ButterKnife.inject(this);
         toolbarDangantianjia= (Toolbar) findViewById(R.id.toolbar_dangantianjia);
         toolbarDangantianjia.setTitle("");
        toolbarDangantianjia.setNavigationIcon(R.mipmap.fanghui_baise);
        toolbarDangantianjia.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getPhotoFileName() { //675676767,png
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        pathurl=sdf.format(date) + ".png";
        return sdf.format(date) + ".png";
    }


    @OnClick({R.id.liner_touxiang, R.id.liner_xingming, R.id.liner_shouji, R.id.liner_nianlin, R.id.liner_xinbie, R.id.liner_shenfenzheng, R.id.liner_shenfen, R.id.liner_shenggao, R.id.liner_tizhong, R.id.activity_dangantianjia_})
    public void onClick(View view) {
        Log.i("222", "onClick: ");
        switch (view.getId()) {
            case R.id.liner_touxiang:
                new AlertDialog.Builder(this).setTitle("头像选择").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[]{"从手机相册中选择", "相机"}, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                 file = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
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
                                    intent.putExtra("outputX", 100);
                                    intent.putExtra("outputY", 100);
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
            case R.id.liner_xinbie:

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
            case R.id.liner_shenfenzheng:
                break;
            case R.id.liner_shenfen:
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
            case R.id.liner_shenggao:
                break;
            case R.id.liner_tizhong:
                break;
            case R.id.activity_dangantianjia_:
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
                    tianjiadananTouxiang.setImageBitmap(bm);
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
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, CROP_PHOTO);//启动裁剪
        } else if (requestCode == CROP_PHOTO) {//获取裁剪后的结果
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Bitmap bm = bundle.getParcelable("data");//  bundle.putParceable("data",bm);
//				bm.compress(CompressFormat.JPEG, 100, new FileOutputStream());
                tianjiadananTouxiang.setImageBitmap(bm);
            }
        }
    }

    @OnClick(R.id.dangantianjia_button)
    public void onClick() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "user_record_tianjia_servlet_photo");//user_record_tianjia_servlet

        requestParams.addBodyParameter("userid", new NetUtil().getUser().getUserId() + "");
        requestParams.addBodyParameter("username", tianjiadananXingming.getText() + "");
        requestParams.addBodyParameter("userphone", tianjiadananShouji.getText() + "");
        requestParams.addBodyParameter("userage", tianjiadananNianlin.getText() + "");
        requestParams.addBodyParameter("usersex", user_sex + "");
        requestParams.addBodyParameter("usershenffenzheng", tianjiadananShenfenzheng.getText() + "");
        requestParams.addBodyParameter("useridentity", user_identity + "");
        requestParams.addBodyParameter("userheight", tianjiadananShenggao.getText() + "");
        requestParams.addBodyParameter("userweight", tianjiadananTizhong.getText() + "");
        requestParams.setMultipart(true);//上传照片一定要设置
        if(file!=null){
            requestParams.addBodyParameter("userphoto",file);
        }



        requestParams.addBodyParameter("pathurl",pathurl);
        Log.i("dangan", "onClick: "+file+"---"+pathurl);
        // user=new user_record_tbl(new NetUtil().getUser().getUserid(),null,tianjiadananShouji.getText()+"",tianjiadananXingming.getText()+"",Integer.parseInt(tianjiadananNianlin.getText()+""),user_sex,Float.parseFloat(tianjiadananShenggao.getText()+""),Float.parseFloat(tianjiadananTizhong.getText()+""),Integer.parseInt(tianjiadananShenfenzheng.getText()+""),user_identity+"");

    x.http().post(requestParams, new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {

        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            Log.i("dangantianjia_Activity", "onError: "+ex);
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


