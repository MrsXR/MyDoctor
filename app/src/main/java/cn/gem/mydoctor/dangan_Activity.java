package cn.gem.mydoctor;


import android.content.ContentQueryMap;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;

import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.gem.entity.UserRecordTbl;
import cn.gem.util.CommonQuantity;
import cn.gem.util.NetUtil;
import cn.gem.util.OnItemClickListener;
import cn.gem.weight.MenuAdapter;


public class dangan_Activity extends AppCompatActivity{


    @InjectView(R.id.imageButton_dangan_tianjia)
    ImageButton imageButtonDangan;
    @InjectView(R.id.image_dangan_fanghui)
    ImageButton imageDangan;
    @InjectView(R.id.dangan_toolbar)
    Toolbar danganToolbar;

    List<UserRecordTbl> list = new ArrayList<UserRecordTbl>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MenuAdapter mMenuAdapter;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    int pageNo=0;
    int pageSize=7;
    int isConsult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangan_);
        ButterKnife.inject(this);


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.dangan_newlistview);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。

        // 添加滚动监听。
        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

        inituy();

        //从快速咨询跳转进来
        if(getIntent().getIntExtra("isConsult",0)==CommonQuantity.CONSULTFAST){
            isConsult=getIntent().getIntExtra("isConsult",0);
        }

    }

    private void initSwipeMenuRecyclerView(){
        mMenuAdapter = new MenuAdapter(list,dangan_Activity.this);//================传数据!!!!!!
        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(mMenuAdapter);
    }


    @OnClick({R.id.image_dangan_fanghui, R.id.imageButton_dangan_tianjia})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_dangan_fanghui:
                finish();
                break;
            case R.id.imageButton_dangan_tianjia:
                Intent intent = new Intent(this, dangan_xiugaiActivity.class);
                intent.putExtra("flag", CommonQuantity.FIRST);
                startActivityForResult(intent,115);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==CommonQuantity.FIRST){
            inituy();
        }else if(resultCode==CommonQuantity.SECOND){
            inituy();
        }


    }

    /**
     * 刷新监听。
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mSwipeMenuRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };


    /**
     * 加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (!recyclerView.canScrollVertically(1)) {
            if (dy!=0) {// 手指不能向上滑动了
                    // TODO 这里有个注意的地方，如果你刚进来时没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。
                    if(list!=null&&list.size()!=0){
                    Toast.makeText(dangan_Activity.this, "正在加载更多！", Toast.LENGTH_SHORT).show();
                    pageNo++;
                    get();

         }
        }
            }
        }
    };


    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(dangan_Activity.this)
                        .setBackgroundDrawable(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

                SwipeMenuItem addItem = new SwipeMenuItem(dangan_Activity.this)
                        .setBackgroundDrawable(R.drawable.selector_green)
                        .setText("编辑")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。
            }
        }
    };

    //item监听事件
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            if(isConsult==CommonQuantity.CONSULTFAST){
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putParcelable("UserRecordTbl",list.get(position));
                intent.putExtras(bundle);
                setResult(CommonQuantity.CONSULTFAST,intent);
                finish();
            }
        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView#RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

           if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                //Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
            // TODO 推荐调用Adapter.notifyItemRemoved(position)，也可以Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                initof(adapterPosition);//删除
            }
            if(menuPosition==1){
                Intent intent=new Intent(dangan_Activity.this,dangan_xiugaiActivity.class);
                intent.putExtra("flag", CommonQuantity.SECOND);
                intent.putExtra("user_record_tbl",list.get(adapterPosition));
                dangan_Activity.this.startActivity(intent);
            }
        }
    };



    public void inituy() {
        RequestParams requestParams = new RequestParams(NetUtil.url+"user_record_show_servlet");

        requestParams.addQueryStringParameter("userid",new NetUtil().getUser().getUserId()+"");
        requestParams.addQueryStringParameter("pageNo", 0 + "");
        requestParams.addQueryStringParameter("pageSize", pageSize + "");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();

                Type type = new TypeToken<List<UserRecordTbl>>() {}.getType();

                List<UserRecordTbl> new_list=gson.fromJson(result,type);

                list.clear();
                list.addAll(new_list);

                initSwipeMenuRecyclerView();

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


    public void get() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "user_record_show_servlet");

        requestParams.addQueryStringParameter("userid", new NetUtil().getUser().getUserId() + "");
        requestParams.addQueryStringParameter("pageNo", pageNo + "");
        requestParams.addQueryStringParameter("pageSize", pageSize + "");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<UserRecordTbl>>() {
                }.getType();
                List<UserRecordTbl> new_list = gson.fromJson(result, type);

                if(new_list.size()==0){
                    Toast.makeText(dangan_Activity.this, "没有更多数据！", Toast.LENGTH_SHORT).show();
                }else {
                    list.addAll(new_list);
                    mMenuAdapter.notifyDataSetChanged();

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


    //删除
    public void initof(final int userrecordid) {

        RequestParams r = new RequestParams(NetUtil.url + "user_record_drop_servlet");

        r.addQueryStringParameter("userrecordid", list.get(userrecordid).getUserrecordId()+"");

        x.http().post(r, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                list.remove(userrecordid);
                mMenuAdapter.notifyItemRemoved(userrecordid);
                Toast.makeText(dangan_Activity.this, "删除成功", Toast.LENGTH_SHORT).show();
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


