package cn.gem.mydoctor;

import android.content.Context;
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
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.application.MyApplication;
import cn.gem.entity.DoctorInHospital;
import cn.gem.entity.OrderPrice;
import cn.gem.entity.OrderTbl;
import cn.gem.util.CommonGson;
import cn.gem.util.IpChangeAddress;
import cn.gem.util.MaxLengthWatcher;

public class OrderDoctorActivity extends AppCompatActivity {

    @InjectView(R.id.order_doctor_photo)
    ImageView orderDoctorPhoto;
    @InjectView(R.id.order_doctor_sname)
    TextView orderDoctorSname;
    @InjectView(R.id.order_doctor_position)
    TextView orderDoctorPosition;
    @InjectView(R.id.order_doctor_address)
    TextView orderDoctorAddress;
    @InjectView(R.id.order_data_showtime)
    TextView orderDataShowtime;
    @InjectView(R.id.order_user_sname)
    TextView orderUserSname;
    @InjectView(R.id.edit_text_phone)
    EditText editTextPhone;
    @InjectView(R.id.edit_text_ill)
    EditText editTextIll;
    @InjectView(R.id.order_ill_content)
    EditText orderIllContent;
    @InjectView(R.id.order_to_photo)
    ImageButton orderToPhoto;
    @InjectView(R.id.allow_order)
    CheckBox allowOrder;
    @InjectView(R.id.order_doctor_ill)
    Button orderDoctorIll;
    @InjectView(R.id.order_hospital_sname)
    TextView orderHospitalSname;
    @InjectView(R.id.order_to_photo_rlt)
    RelativeLayout orderToPhotoRlt;
    @InjectView(R.id.order_look_protocol)
    TextView orderLookProtocol;

    TextView textView;
    Toolbar toolbar;

    int doctorId = 0;
    DoctorInHospital doctorInHospital;
    String userPhone = null;
    String userIllSname = null;
    String userIllContent = null;

    OrderTbl orderTbl;
    String mYear = null;
    int orderDetailTblId = 0;

    public static final int SELECT_PIC = 11;//选择相册
    public static final int TAKE_PHOTO = 12;//选择拍照
    public static final int CROP = 13;
    String items[] = {"相册选择", "拍照"};


    private File file;
    private File file2;
    private Uri imageUri;
    private Uri imageUri2;
    boolean flag = false;
    MyApplication my = (MyApplication) getApplication();
    ImageView imageButtonOne = null;
    ImageView imageButtonTwo = null;

    int temp = 0;
    OrderPrice op = null;//获取订单的主键

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_doctor);
        ButterKnife.inject(this);

        textView = (TextView) findViewById(R.id.order_ill_content_number);
        editTextPhone.addTextChangedListener(new MaxLengthWatcher(11, null, this));
        editTextIll.addTextChangedListener(new MaxLengthWatcher(11, null, this));
        //字数限制
        orderIllContent.addTextChangedListener(new MaxLengthWatcher(300, textView, this));
        //选择拍照或者从照片获取
        imageButtonOne = (ImageView) findViewById(R.id.order_ill_photo_one);
        imageButtonTwo = (ImageView) findViewById(R.id.order_ill_photo_two);

        initToolbar();//toolbar的设置

        //用户信息,包括用户的姓名手机号码
        orderUserSname.setText(my.getUserTbl().getUserSname());

        //用户手机号码
        editTextPhone.setText(my.getUserTbl().getUserPhone());         //从MyApplication中获取


        Calendar cal = Calendar.getInstance();
        mYear = String.valueOf(cal.get(Calendar.YEAR));// 获取当前年份

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String sendDate = bundle.getString("sendDate");
        mYear += "-" + sendDate;//年-月-日

        orderDataShowtime.setText(mYear);

        doctorId = bundle.getInt("doctorId");//医生
        orderDetailTblId = bundle.getInt("orderDetailTblId");
    /*    doctorId = 1;
        orderDetailTblId = 1;*/
        getUserData(this);
    }

    void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.doctors_detail_toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.back6);
        setSupportActionBar(toolbar);

        //返回上一个界面
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getUserData(final Context context) {
        String stl = IpChangeAddress.ipChangeAddress + "OrderDoctorServlet";
        RequestParams requestParams = new RequestParams(stl);
        requestParams.addQueryStringParameter("doctorId", doctorId + "");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                Log.i("OrderDoctorActivity", "onSuccess: ---------------------------->>>>>");
                Gson gson = CommonGson.getGson();
                doctorInHospital = gson.fromJson(result, DoctorInHospital.class);
                getDoctorsData();//z展示医生的数据

                toOrderDoctor(context);
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

    @OnClick({R.id.order_ill_photo_one, R.id.order_to_photo_rlt, R.id.order_ill_photo_two,R.id.order_look_protocol})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_to_photo_rlt:
                if (flag == false) {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        //目录，文件名Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                        file = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
                        imageUri = Uri.fromFile(file);
                        getSdPhoto();
                    }
                }
                break;


            case R.id.order_ill_photo_one:
                if (imageButtonOne.isShown()) {

                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        //目录，文件名Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                        file = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
                        imageUri = Uri.fromFile(file);
                        getSdPhoto();
                    }

                }

                break;
            case R.id.order_ill_photo_two:

                if (imageButtonTwo.isShown()) {
                    flag = true;
                    //目录，文件名Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    file2 = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
                    imageUri2 = Uri.fromFile(file2);

                    getSdPhoto();

                }
                break;

            case R.id.order_look_protocol:
                Intent intent1=new Intent(this,UserProtocolActivity.class);
                startActivity(intent1);
                break;

        }
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
                        startActivityForResult(intent2, TAKE_PHOTO);
                        break;
                }
            }
        }).show();

    }

    //对照片命名
    private String getPhotoFileName() {
        ++temp;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + (temp) + ".png";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        File f = null;
        f = file;
        //最好用if语句 并且加上结果码
        if (flag == true) {
            f = file2;
        }

        switch (requestCode) {
            case SELECT_PIC:
                if (data != null) {
                    Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);  //获取照片路径
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                    showPhoto(bitmap);
                }
                break;

            case TAKE_PHOTO:
                getPhoto(Uri.fromFile(f));
                break;
            case CROP:
                if (data != null) {
                    Bundle b = data.getExtras();
                    Bitmap bitmap = b.getParcelable("data");
                    showPhoto(bitmap);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showPhoto(Bitmap bitmap) {

        File f = null;
        //最好用if语句 并且加上结果码
        if (flag == true) {
            f = file2;
        } else {
            f = file;
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            saveFile(bitmap).compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //对照片
        if (flag == false) {
            imageButtonOne.setVisibility(View.VISIBLE);
            imageButtonOne.setImageBitmap(saveFile(bitmap));
            imageButtonTwo.setVisibility(View.VISIBLE);
        } else if (flag == true) {
            imageButtonTwo.setImageBitmap(bitmap);
        }


    }

    //图片按比例大小压缩方法（根据Bitmap图片压缩）：
    public Bitmap saveFile(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
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
        float hh = 400f;//这里设置高度为800f
        float ww = 240f;//这里设置宽度为480f
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
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
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


    private void getOutContent() {

        //用户信息,包括用户的姓名手机号码
        orderUserSname.setText(my.getUserTbl().getUserSname());

        //用户手机号码
        userPhone = editTextPhone.getText().toString();

        //用户患病名称
        userIllSname = editTextIll.getText().toString();
        //用户患病内容
        userIllContent = orderIllContent.getText().toString();

    }

    //提交预约信息
    private void toOrderDoctor(final Context context) {

        //提交订单的点击事件
        orderDoctorIll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //提交信息，将信息插入数据库
                if (!TextUtils.isEmpty(editTextPhone.getText()) && !TextUtils.isEmpty(editTextIll.getText()) &&
                        !TextUtils.isEmpty(orderIllContent.getText()) && allowOrder.isChecked()) {
                    //获取text中的文本内容
                    getOutContent();
                    insertSql(context);//插入数据库，并且跳转页面

                } else {
                    if (TextUtils.isEmpty(editTextPhone.getText())) {
                        Toast.makeText(context, "请填写手机号码", Toast.LENGTH_SHORT).show();
                    }
                    if (TextUtils.isEmpty(editTextIll.getText())) {
                        Toast.makeText(context, "请填写疾病名称", Toast.LENGTH_SHORT).show();
                    }
                    if (TextUtils.isEmpty(orderIllContent.getText())) {
                        Toast.makeText(context, "请描述病情", Toast.LENGTH_SHORT).show();
                    }
                    if (!allowOrder.isChecked()) {
                        Toast.makeText(context, "请选择同意协议", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    private void insertSql(final Context context) {
        //获取当前时间
        Timestamp d = new Timestamp(System.currentTimeMillis());
        int userId = MyApplication.getUserTbl().getUserId();
        orderTbl = new OrderTbl(userId, doctorId, userPhone, userIllSname, userIllContent, mYear, d, 1, 0, orderDetailTblId);
        Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String data = g.toJson(orderTbl);

        String send = IpChangeAddress.ipChangeAddress + "OrderTblServlet";
        RequestParams requed = new RequestParams(send);
        requed.setMultipart(true);//上传照片一定要设置
        requed.addBodyParameter("orderTbl", data);
        requed.addBodyParameter("orderDetailTblId", orderDetailTblId + "");

        if (imageButtonOne.isShown()) {
            requed.addBodyParameter("file", file);
            if (flag == true) {
                requed.addBodyParameter("file2", file2);
            }
        }

        x.http().post(requed, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson = CommonGson.getGson();
                op = gson.fromJson(result, OrderPrice.class);

                //跳转到支付界面
                Intent intent = new Intent(context, OrderPayActivity.class);
                //intent.putExtra("",);---------------就诊人
                intent.putExtra("doctorInHospital", doctorInHospital);//医生在医院的信息
                intent.putExtra("orderId", op.getOrderId());//订单的ID
                intent.putExtra("userIllContent", userIllContent);
                intent.putExtra("orderDetailPrice", op.getPrice());
                startActivity(intent);
                finish();
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


    public void getDoctorsData() {
        //医生头像获取数据库得到头像
        if (doctorInHospital.getDoctorsPhoto() != null) {
            String photoUrl = IpChangeAddress.ipChangeAddress + doctorInHospital.getDoctorsPhoto();
            ImageOptions imageOptions = new ImageOptions.Builder()
                    .setCircular(true)
                    .setCrop(true).setSize(65, 65).build();

            x.image().bind(orderDoctorPhoto, photoUrl, imageOptions);
        }

        orderDoctorSname.setText(doctorInHospital.getDoctorsSname());
        orderDoctorPosition.setText(doctorInHospital.getSubjectSname());
        orderHospitalSname.setText(doctorInHospital.getDoctorsSname());
        orderDoctorAddress.setText(doctorInHospital.getHospitalAddress());

    }


}
