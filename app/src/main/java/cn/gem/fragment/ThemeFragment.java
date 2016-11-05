package cn.gem.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.CursorJoiner;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.entity.QueryThemeBean;
import cn.gem.entity.ThemeDetailTbl;
import cn.gem.entity1.ThemeTbl;
import cn.gem.mydoctor.ExpressThemeActivity;
import cn.gem.mydoctor.R;
import cn.gem.mydoctor.ThemeDetailActivity;
import cn.gem.util.CommonAdapter;
import cn.gem.util.FileTraversal;
import cn.gem.util.NetUtil;
import cn.gem.util.ViewHolder;
import cn.gem.weight.MyReflashList;

/**
 * Created by panwenpeng on 2016/10/14.
 */
public class ThemeFragment extends BaseFragment implements MyReflashList.OnRefreshUploadChangeListener {
    List<FileTraversal> fileTraversal;
    ImageView ivSend;
    MyReflashList lvTheme;
    List<ThemeDetailTbl> themeDetailTbls = new ArrayList<>();
    RadioGroup radioGroup;
    HorizontalScrollView horizontalScrollView;
    List<String> moduleItem = new ArrayList<>();
    @InjectView(R.id.iv_fabiao)
    ImageView ivFabiao;
    private int item_check_ID;
    int screenHalf;
    RadioButton rb;
    QueryThemeBean queryThemeBean;
    int themeId = 0;//话题ID 用来查询评论表计算评论数
    int moduleId = 1;//模块ID
    int flag = 5;
    Integer pageNo = 1;
    Integer pageSize = 5;
    int lookNumber;
    Handler handler = new Handler();
    CommonAdapter<ThemeTbl> themeTblCommonAdapter;
    List<ThemeTbl> themeTbls = new ArrayList<ThemeTbl>();
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_theme, null);
        radioGroup = (RadioGroup) v.findViewById(R.id.radio_group);
        horizontalScrollView = (HorizontalScrollView) v.findViewById(R.id.horizontalscrollview);
        lvTheme = (MyReflashList) v.findViewById(R.id.lv_theme);
        ivSend = (ImageView) v.findViewById(R.id.iv_add1);

        getData();
        ButterKnife.inject(this, v);
        return v;
    }


    @Override
    public void initView() {
        lvTheme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getData();
                lookNumber++;
                Log.i("ThemeFragment", "ThemeFragment: onItemClick:LOOKNUMBER" + lookNumber);
                Intent intent = new Intent(getActivity(), ThemeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("themeTbl", themeTbls.get(position));
                bundle.putParcelable("moduleTbl", themeTbls.get(position).getModuleTbl());
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

    }

    @Override
    public void initEvent() {


    }

    @Override
    public void initData() {
        //getData();
        moduleItem.add("疾病");
        moduleItem.add("保健");
        moduleItem.add("急救");
        moduleItem.add("饮食");
        moduleItem.add("心理");
        moduleItem.add("两性");
        moduleItem.add("美容");
        moduleItem.add("减肥");
        moduleItem.add("中医");
        moduleItem.add("育儿");
        for (int i = 0; i < moduleItem.size(); i++) {
            rb = new RadioButton(getActivity());
            //根据下标获取模块
            String itemArr = moduleItem.get(i);
            rb.setText(itemArr);
            rb.setTextSize(16);
            rb.setPadding(0, 15, 0, 15);
            rb.setGravity(Gravity.CENTER);
            //根据需要显示的初始标签个数，4个
            rb.setLayoutParams(new ViewGroup.LayoutParams((int) ((getScreenWidth(getActivity())) / 4.0), ViewGroup.LayoutParams.FILL_PARENT));
            rb.setBackgroundResource(R.drawable.radiobutton_bg_selector);
            //**原生radiobutton是有小圆点的，要去掉圆点而且最好按以下设置，设置为null的话在4.x.x版本上依然会出现**
            rb.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
            rb.setTextColor(getActivity().getResources().getColorStateList(R.color.text));
            //向radiogroup中添加radiobutton
            radioGroup.addView(rb);
            //设置初始check对象（第一个索引从0开始）
            ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
            //监听check对象
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, final int checkedId) {
                    final int RadiobuttonId = group.getCheckedRadioButtonId();
                    //获取radiobutton对象
                    final RadioButton bt = (RadioButton) group.findViewById(RadiobuttonId);
                    //获取单个对象中的位置
                    final int index = group.indexOfChild(bt);
                    //设置滚动位置，可使点击radiobutton时，将该radiobutton移动至第二位置
                    horizontalScrollView.smoothScrollTo(bt.getLeft() - (int) (getScreenWidth(getActivity()) / 4.0), 0);
                    switch (moduleItem.get(index)) {
                        case "疾病":
                            moduleId = 1;
                            getData();
                            break;
                        case "保健":
                            moduleId = 2;
                            getData();
                            break;
                        case "急救":
                            moduleId = 3;
                            getData();
                            break;
                        case "饮食":
                            moduleId = 4;
                            getData();
                            break;
                        case "心理":
                            moduleId = 5;
                            getData();
                            break;
                        case "两性":
                            moduleId = 6;
                            getData();
                            break;
                        case "美容":
                            moduleId = 7;
                            getData();
                            break;
                        case "减肥":
                            moduleId = 8;
                            getData();
                            break;
                        case "中医":
                            moduleId = 9;
                            getData();
                            break;
                        case "育儿":
                            moduleId = 10;
                            getData();
                            break;

                    }

                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    public void getData() {
        //初始化数据
        //xutils获取网络数据
        String url = NetUtil.url + "QueryThemeServlet";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("moduleId", moduleId + "");
        requestParams.addQueryStringParameter("flag", flag + "");
        requestParams.addQueryStringParameter("pageNo", pageNo + "");
        requestParams.addQueryStringParameter("pageSize", pageSize + "");
        Log.i("ThemeFragment", "ThemeFragment: 主页面初始化" + requestParams);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("ThemeFragment", "ThemeFragment: onSuccess------------" + result);
                //json转换成List<>
                Gson gson = new Gson();
                Type type = new TypeToken<List<ThemeTbl>>() {
                }.getType();
                List<ThemeTbl> newThemetbl = new ArrayList<ThemeTbl>();
                newThemetbl = gson.fromJson(result, type);
                themeTbls.clear();//清空数据
                themeTbls.addAll(newThemetbl);
                //设置listview的适配器
                if (themeTblCommonAdapter == null) {
                    themeTblCommonAdapter = new CommonAdapter<ThemeTbl>(getActivity(), themeTbls, R.layout.theme_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ThemeTbl themeTbl, int position) {
                            TextView tvThenmeName = viewHolder.getViewById(R.id.tv_username);
                            tvThenmeName.setText(themeTbl.getUserTbl().getUserSname());
                            ImageView userPhoto=viewHolder.getViewById(R.id.iv_userphoto);
                            ImageOptions imageOptions=new ImageOptions.Builder().setCircular(true).build();
                            String userImage=NetUtil.image+themeTbl.getUserTbl().getUserPhoto();
                            x.image().bind(userPhoto,userImage,imageOptions);
                            TextView tvThemeName = viewHolder.getViewById(R.id.tv_forum_title);
                            tvThemeName.setText(themeTbl.getThemeName());
                            TextView tvThemeTime = viewHolder.getViewById(R.id.tv_themetime);
                            String time = upsateTime(themeTbl.getThemeTime());
                            tvThemeTime.setText(time + "");
                            TextView tvThemeReadNum = viewHolder.getViewById(R.id.tv_theme_readnumber);
                            tvThemeReadNum.setText(themeTbl.getLookNumber() + "");
                            TextView tvAnswerNum = viewHolder.getViewById(R.id.tv_answertheme_number);
                            tvAnswerNum.setText(themeTbl.getAnswerNum() + "");
                            String themePhotoUrl=themeTbl.getThemePhotoUrl();
                            Log.i("ThemeFragment", "ThemeFragment: THEMEPHOTO"+themePhotoUrl);
                            String a[]=themePhotoUrl.split("%");
                            Log.i("ThemeFragment", "ThemeFragment: ACDDCSA"+a);
                        }
                    };
                    lvTheme.setAdapter(themeTblCommonAdapter);
                } else {
                    themeTblCommonAdapter.notifyDataSetChanged();
                }

                lvTheme.completeLoad();
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

    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
  //shijiangeshihua
    public String upsateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //Date dateNow = new Date(System.currentTimeMillis());
        String time = sdf.format(date);
        return time;
    }


    @OnClick(R.id.iv_fabiao)
    public void onClick() {
        Intent intent=new Intent(getActivity(), ExpressThemeActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("moduleId",moduleId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        pageNo=1;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                lvTheme.completeRefresh();
            }
        },1000);

    }

    @Override
    public void onPull() {
        pageNo++;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataJiazai();
            }
        },1000);




    }

    public void getDataJiazai() {
        //初始化数据
        //xutils获取网络数据
        String url = NetUtil.url + "QueryThemeServlet";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("moduleId", moduleId + "");
        requestParams.addQueryStringParameter("flag", flag + "");
        requestParams.addQueryStringParameter("pageNo", pageNo + "");
        requestParams.addQueryStringParameter("pageSize", pageSize + "");
        Log.i("ThemeFragment", "ThemeFragment: 主页面初始化" + requestParams);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("ThemeFragment", "ThemeFragment: onSuccess------------" + result);
                //json转换成List<>
                Gson gson = new Gson();
                Type type = new TypeToken<List<ThemeTbl>>() {
                }.getType();
                List<ThemeTbl> newThemetbl = new ArrayList<ThemeTbl>();
                newThemetbl = gson.fromJson(result, type);
            if (newThemetbl.size()==0){
                    pageNo--;
                    Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
                    lvTheme.completeLoad();
                    return;
                }
                 themeTbls.addAll(newThemetbl);
                //设置listview的适配器
                if (themeTblCommonAdapter == null) {
                    themeTblCommonAdapter = new CommonAdapter<ThemeTbl>(getActivity(), themeTbls, R.layout.theme_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ThemeTbl themeTbl, int position) {
                            TextView tvThenmeName = viewHolder.getViewById(R.id.tv_username);
                            tvThenmeName.setText(themeTbl.getUserTbl().getUserSname());
                            ImageView userPhoto=viewHolder.getViewById(R.id.iv_userphoto);
                            ImageOptions imageOptions=new ImageOptions.Builder().setCircular(true).build();
                            String userImage=NetUtil.image+themeTbl.getUserTbl().getUserPhoto();
                            x.image().bind(userPhoto,userImage,imageOptions);
                            TextView tvThemeName = viewHolder.getViewById(R.id.tv_forum_title);
                            tvThemeName.setText(themeTbl.getThemeName());
                            TextView tvThemeTime = viewHolder.getViewById(R.id.tv_themetime);
                            String time = upsateTime(themeTbl.getThemeTime());
                            tvThemeTime.setText(time + "");
                            TextView tvThemeReadNum = viewHolder.getViewById(R.id.tv_theme_readnumber);
                            tvThemeReadNum.setText(themeTbl.getLookNumber() + "");
                            TextView tvAnswerNum = viewHolder.getViewById(R.id.tv_answertheme_number);
                            tvAnswerNum.setText(themeTbl.getAnswerNum() + "");


                        }
                    };
                    lvTheme.setAdapter(themeTblCommonAdapter);
                } else {
                    themeTblCommonAdapter.notifyDataSetChanged();
                }

                lvTheme.completeLoad();
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
    public void onResume() {
        Log.i("ThemeFragment", "ThemeFragment: onResume");
        getData();
        super.onResume();
    }

}

