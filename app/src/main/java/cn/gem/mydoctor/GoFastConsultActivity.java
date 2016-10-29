package cn.gem.mydoctor;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.application.MyApplication;
import cn.gem.util.IpChangeAddress;
import cn.gem.util.MaxLengthWatcher;

public class GoFastConsultActivity extends AppCompatActivity {

    @InjectView(R.id.go_fast_consult_back)
    ImageButton goFastConsultBack;
    @InjectView(R.id.go_fast_consult_sname)
    TextView goFastConsultSname;
    @InjectView(R.id.go_fast_consult_r)
    RelativeLayout goFastConsultFile;
    @InjectView(R.id.go_fast_consult_edittext)
    EditText goFastConsultEdittext;
    @InjectView(R.id.go_fast_consult_add)
    ImageView goFastConsultAdd;
    @InjectView(R.id.go_fast_consult_agree)
    CheckBox goFastConsultAgree;
    @InjectView(R.id.go_fast_consult)
    Button goFastConsult;
    TextView textView;

    boolean isCheck=false;

    MyApplication myApplication= (MyApplication) getApplication();
    String userIllContent = null;
    private File file;
    private Uri imageUri;
    String items[] = {"相册选择", "拍照"};
    public static final int SELECT_PIC = 11;//选择相册
    public static final int TAKE_PHOTO = 12;//选择拍照
    public static final int CROP=13;
    int goConsult=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_fast_consult);
        ButterKnife.inject(this);
        textView= (TextView) findViewById(R.id.go_fast_consult_number);
        goFastConsultEdittext.addTextChangedListener(new MaxLengthWatcher(500,textView,this));

        goFastConsultSname.setText(myApplication.getUserTbl().getUserSname());
    }


    @OnClick({R.id.go_fast_consult_r, R.id.go_fast_consult_add, R.id.go_fast_consult,R.id.go_fast_consult_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_fast_consult_r:
                //选择就医人
                Intent intent=new Intent(this,dangan_Activity.class);
                startActivityForResult(intent,110);
                break;
            case R.id.go_fast_consult_add:
                //添加照片
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    //目录，文件名Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    file = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
                    imageUri = Uri.fromFile(file);
                    getSdPhoto();
                }
                break;

            case R.id.go_fast_consult:

                if(goFastConsultAgree.isChecked()){
                //添加到数据库
                    userIllContent=goFastConsultEdittext.getText().toString();
                    keepData();
                    //跳转付钱界面
                    Toast.makeText(GoFastConsultActivity.this, "咨询成功", Toast.LENGTH_SHORT).show();
                   // GoFastConsultActivity.this.finish();
                }
                break;
            case R.id.go_fast_consult_back:
                //返回主页
                finish();
                break;
        }
    }

    private void keepData(){

        String surl= IpChangeAddress.ipChangeAddress+"GoFastConsultDaoServlet";
        RequestParams requestParams =new RequestParams(surl);
        requestParams.addBodyParameter("userIllContent",userIllContent);
        requestParams.addBodyParameter("userId",myApplication.getUserTbl().getUserId()+"");//用户ID
        requestParams.addBodyParameter("goConsult",goConsult+"");
        requestParams.addBodyParameter("file",file);//照片

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


    private void getSdPhoto() {

        new AlertDialog.Builder(this).setTitle("选择").setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        //相册选择，不裁剪
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent, SELECT_PIC);

                        break;

                    case 1:
                        //拍照结束后裁剪
                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        startActivityForResult(intent2,TAKE_PHOTO);
                        break;
                }
            }
        }).show();

    }

    //对照片命名
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case SELECT_PIC:
                if (data != null) {
                    Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor =getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);  //获取照片路径
                    cursor.close();
                    Bitmap bitmap= BitmapFactory.decodeFile(picturePath);
                    showPhoto(bitmap);
                }
                break;

            case TAKE_PHOTO:
                getPhoto(Uri.fromFile(file));
                break;
            case CROP:
                if(data!=null){
                    Bundle b=data.getExtras();
                    Bitmap bitmap=b.getParcelable("data");
                    showPhoto(bitmap);
                }
                break;
        }

        if (resultCode==RESULT_OK) {
            Bundle bundle=data.getExtras();
            goFastConsultSname.setText( bundle.getString("userIllName"));//就医人姓名
            goConsult=bundle.getInt("userRecordId");//档案ID
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showPhoto(Bitmap bitmap) {

        FileOutputStream fos= null;
        try {
            fos = new FileOutputStream(file);
            saveFile(bitmap).compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //对照片
        goFastConsultAdd.setImageBitmap(bitmap);

    }

    //图片按比例大小压缩方法（根据Bitmap图片压缩）：
    public Bitmap saveFile(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 200f;//这里设置高度为800f
        float ww = 120f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩


    }

    //质量压缩方法：
    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
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
        startActivityForResult(intent, CROP);
    }

}
