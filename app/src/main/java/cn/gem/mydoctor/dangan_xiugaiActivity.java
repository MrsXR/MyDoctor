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

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.application.MyApplication;
import cn.gem.entity.UserRecordTbl;
import cn.gem.util.CommonQuantity;
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
    int user_identity=0;
    private Uri imageUri;
    private File file;

    AlertDialog alertDialog;
    int flag=0;
    MyApplication myApplication= (MyApplication) getApplication();

    UserRecordTbl user_record_tbl;
    boolean ischange=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangan_xiugai);
        ButterKnife.inject(this);

        //判断sd卡是否存在，存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //目录，文件名Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            file = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
            imageUri = Uri.fromFile(file);
        }

        Intent intent = getIntent();

        flag = intent.getIntExtra("flag", 0);
        if (flag==CommonQuantity.SECOND) {
            user_record_tbl = intent.getParcelableExtra("user_record_tbl");

            danganxiugaiEdittextXingming.setText(user_record_tbl.getUserrecordName());
            danganxiugaiEdittextNianlin.setText(user_record_tbl.getUserrecordAge() + "");
            danganxiugaiEdittextShouji.setText(user_record_tbl.getUserrecordPhone());
            danganxiugaiEdittextXinbie.setText(init(user_record_tbl.getUserrecordSex(), 1));
            danganxiugaiEdittextShenfenzheng.setText(user_record_tbl.getUserrecordCard());
            danganxiugaiEdittextShengfen.setText(init(user_record_tbl.getUserrecordIdentity(), 0));
            danganxiugaiEdittextShenggao.setText(user_record_tbl.getUserrecordHeight() + "");
            danganxiugaiEdittextTizhong.setText(user_record_tbl.getUserrecordWeight() + "");
        }
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }

    public void initio() {

        int userId=myApplication.getUserTbl().getUserId();
        String username = danganxiugaiEdittextXingming.getText() + "";
        String usershouji = danganxiugaiEdittextShouji.getText() + "";
        int userage = Integer.parseInt(danganxiugaiEdittextNianlin.getText() + "");
        int usersex=0;
        if(danganxiugaiEdittextXinbie.getText().equals("女")){
            usersex=1;
        }

        int usershenfen =user_identity ;
        float usershenggao = Float.parseFloat(danganxiugaiEdittextShenggao.getText().toString());
        float usertizhong = Float.parseFloat(danganxiugaiEdittextTizhong.getText().toString());
        String usercard = danganxiugaiEdittextShenfenzheng.getText() + "";
        UserRecordTbl userRecordTbl=new UserRecordTbl(userId,null,usershouji,username,userage,usersex,
                usershenggao,usertizhong,usershenfen,usercard);

        Gson gson=new Gson();
        String sendData=gson.toJson(userRecordTbl);

       Log.i("dangan_xiugaiActivity", "initio: "+sendData);

        RequestParams requestParams =null;
        if(flag==CommonQuantity.SECOND) {
            //修改档案
            requestParams=new RequestParams(NetUtil.url + "user_record_update_servlet");
            requestParams.setMultipart(true);//上传照片一定要设置

            requestParams.addBodyParameter("userrecordId",user_record_tbl.getUserrecordId()+"");
            requestParams.addBodyParameter("sendData",sendData);

            if(ischange) {
                requestParams.addBodyParameter("userphoto", file);
                Log.i("dangan_xiugaiActivity", "initio: ---------------------------"+file);
            }

        }else if(flag==CommonQuantity.FIRST){

            //添加档案
            requestParams = new RequestParams(NetUtil.url + "user_record_tianjia_servlet_photo");
            requestParams.setMultipart(true);//上传照片一定要设置

            requestParams.addBodyParameter("sendData",sendData);

            if(ischange){
                requestParams.addBodyParameter("userphoto",file);
            }
        }

        Log.i("dangan_xiugaiActivity", "initio: "+requestParams.toString());

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(flag==CommonQuantity.SECOND) {
                    Toast.makeText(dangan_xiugaiActivity.this, "修改成功", Toast.LENGTH_SHORT).show();

                    //修改刷新界面

                    finish();
                }else if(flag==CommonQuantity.FIRST){
                    Toast.makeText(dangan_xiugaiActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    //添加完刷新界面
                    finish();
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


    private String init(int k,int flag){
        String string=null;
        switch (k){
            case 0:
                if(flag==0){
                string="本人";
                } else {
                    string="男";
                }
                break;
            case 1:
                if(flag==0){
                    string="父母";
                } else {
                    string="女";
                }
                break;
            case 2:
                string="子女";
                break;
            case 3:
                string="朋友";
                break;
            case 4:
                string="其他";
                break;

        }
        return string;
    }

    public void initof() {
        RequestParams r = new RequestParams(NetUtil.url + "user_record_drop_servlet");

        r.addQueryStringParameter("userrecordid", userrecordid+"");

        x.http().post(r, new Callback.CommonCallback<String>() {
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
                ischange=true;
                alertDialog= new AlertDialog.Builder(this).setTitle("头像选择").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[]{"从手机相册中选择", "相机"}, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                imageUri = Uri.fromFile(file);
                              if(which==0){
                                  //相册选择，不裁剪
                                  Intent intent = new Intent(Intent.ACTION_PICK, null);
                                  intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                          "image/*");
                                  intent.putExtra("return-data", true);
                                  startActivityForResult(intent, SELECT_PIC);

                              } else {//拍照
                                  //拍照
                                  Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                  intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                  startActivityForResult(intent2,TAKE_PHOTO);
                                }
                            }
                        }).show();
                break;
            case R.id.danganxiugai_liner_xinbie:
                new AlertDialog.Builder(this).setTitle("选择性别").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[]{"男", "女"}, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    user_sex = 0;
                                    danganxiugaiEdittextXinbie.setText("男");
                                } else {
                                    user_sex = 1;
                                    danganxiugaiEdittextXinbie.setText("女");
                                }
                                dialog.dismiss();
                            }
                        }).show();
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
                                        danganxiugaiEdittextShengfen.setText("本人");
                                        break;
                                    case 1:
                                        user_identity = 1;
                                        danganxiugaiEdittextShengfen.setText("父母");
                                        break;
                                    case 2:
                                        user_identity = 2;
                                        danganxiugaiEdittextShengfen.setText("子女");
                                        break;
                                    case 3:
                                        user_identity = 3;
                                        danganxiugaiEdittextShengfen.setText("亲属");
                                        break;
                                    case 4:
                                        user_identity = 4;
                                        danganxiugaiEdittextShengfen.setText("朋友");
                                        break;

                                }
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.danganxiugai_edittext_button:
                initof();
                break;
        }
    }

    //裁剪
    public void getPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
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
        intent.putExtra("return-data", true);
        //裁剪
        startActivityForResult(intent, CROP_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == SELECT_PIC) {
            if(data!=null){
                getPhoto(data.getData());
            }
        } else if (requestCode == TAKE_PHOTO) {
            getPhoto(Uri.fromFile(file));
        } else if (requestCode == CROP_PHOTO) {//获取裁剪后的结果
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Bitmap bm = bundle.getParcelable("data");//  bundle.putParceable("data",bm);

                try {
                    FileOutputStream fos=new FileOutputStream(file);
                    bm.compress(Bitmap.CompressFormat.JPEG,50,fos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                danganxiugaiImageviewTouxiang.setImageBitmap(bm);

                alertDialog.dismiss();//关闭对话框
            }
        }
    }
}
