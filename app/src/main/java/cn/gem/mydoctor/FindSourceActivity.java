package cn.gem.mydoctor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import cn.gem.entity.AllMsg;
import cn.gem.entity.ArticleTbl;
import cn.gem.entity.DoctorsTbl;
import cn.gem.entity.HospitalTbl;
import cn.gem.mydoctor.R;
import cn.gem.util.CommonAdapter;
import cn.gem.util.ViewHolder;
import cn.gem.weight.NoScrollListview;


import static cn.gem.util.NetUtil.url;


public class FindSourceActivity extends Activity {
    private NoScrollListview hspLv;
    private NoScrollListview docLv;
    private NoScrollListview artLv;
    private EditText editText;
    private String text;
    private Button mButtonFind;
    private LinearLayout mLLHsp;
    private ImageButton mButtonBack;
    private TextView tvHsp;
    private TextView tvDoc;
    private TextView tvArt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_source);

        tvHsp=(TextView)findViewById(R.id.tv_hsp);
        hspLv=(NoScrollListview) findViewById(R.id.lv_hsp);
        docLv=(NoScrollListview) findViewById(R.id.lv_doc);
        artLv=(NoScrollListview) findViewById(R.id.lv_art);
        mButtonFind=(Button)findViewById(R.id.bt_find);
        editText=(EditText)findViewById(R.id.editText);
        mButtonBack=(ImageButton)findViewById(R.id.ib_back);
        mButtonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text=editText.getText().toString();
                if(text==null||text.equals("")){
                    Toast.makeText(FindSourceActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(FindSourceActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                    getDataFromNet();
                }


            }
        });
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
        public void getDataFromNet(){
        RequestParams requestParams=new RequestParams(url+"/QuerryMsgServlet");
        requestParams.addQueryStringParameter("content",text);
        x.http().get(requestParams, new Callback.CacheCallback<String>() {




        @Override
        public void onSuccess(String result) {
            Gson gson = new Gson();
            Type type = new TypeToken<AllMsg>() {
            }.getType();

           AllMsg msgs=gson.fromJson(result, type);
            final List<ArticleTbl> artList=msgs.getArtList();
            final List<DoctorsTbl> docList=msgs.getDocList();
            final List<HospitalTbl> hspList=msgs.getHspList();
            if(hspList!=null){
//                tvHsp.setVisibility(View.VISIBLE);
                mLLHsp=(LinearLayout)findViewById(R.id.ll_hsp);
                mLLHsp.setVisibility(View.VISIBLE);

                hspLv.setAdapter(new CommonAdapter<HospitalTbl>(FindSourceActivity.this,hspList,R.layout.lv_item_hsp) {
                    @Override
                    public void convert(ViewHolder viewHolder, HospitalTbl hsp, int position) {
                        ((TextView)viewHolder.getViewById(R.id.tv_hsp)).setText(hspList.get(position).getHospitalSname());
                    }
                });
                hspLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent();




                    }
                });

            }else {
                hspLv.setVisibility(View.GONE);
            }
           if(docList.size()>0){
              tvDoc=(TextView)findViewById(R.id.tv_doc);
               tvDoc.setVisibility(View.VISIBLE);
               docLv.setAdapter(new CommonAdapter<DoctorsTbl>(FindSourceActivity.this,docList,R.layout.lv_item_doc) {
                   @Override
                   public void convert(ViewHolder viewHolder, DoctorsTbl doctorsTbl, int position) {
//                       ((ImageView)viewHolder.getViewById(R.id.iv_doc_photo)).setImageURI(docList.get(position).getDoctorsPhoto());
                       ImageView imageView=(ImageView)findViewById(R.id.iv_doc_photo);
                       Bitmap bitmap = getHttpBitmap(docList.get(position).getDoctorsPhoto());
                       imageView.setImageBitmap(bitmap);

                       ((TextView)viewHolder.getViewById(R.id.tv_doc_name)).setText(docList.get(position).getDoctorsSname());

                   }


               });
           }else {
               docLv.setVisibility(View.GONE);
           }

            if(artList.size()>0){
                tvArt=(TextView)findViewById(R.id.tv_art);
                tvArt.setVisibility(View.VISIBLE);
                artLv.setAdapter(new CommonAdapter<ArticleTbl>(FindSourceActivity.this,artList,R.layout.lv_item_art) {
                    @Override
                    public void convert(ViewHolder viewHolder, ArticleTbl articleTbl, int position) {
                        ((TextView)viewHolder.getViewById(R.id.item_tv_art)).setText(artList.get(position).getArticleTitle());

                    }
                });

            }else {
                artLv.setVisibility(View.GONE);
            }



        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            Log.i("aaaaaaaaaaa", "onError: "+"去他妈的");

        }

        @Override
        public void onCancelled(CancelledException cex) {

        }
            @Override
            public boolean onCache(String result) {
                return false;
            }

        @Override
        public void onFinished() {

        }


    });



}
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);

            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();

            conn.setConnectTimeout(1000);

            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

}



