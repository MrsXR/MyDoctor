package cn.gem.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.gem.mydoctor.R;


/**
 * Created by admin on 2016/10/13.
 */

public class newlistview extends ListView implements AbsListView.OnScrollListener {



  /*  @InjectView(R.id.iv_refresher)
    ImageView ivRefresher;
    @InjectView(R.id.pb_refresher)
    ProgressBar pbRefresher;
    @InjectView(R.id.tv_refreshertext)
    TextView tvRefreshertext;
    @InjectView(R.id.tv_refreshtime)
    TextView tvRefreshtime;
*/

    ImageView ivRefresher;
    ProgressBar pbRefresher;
    TextView tvRefreshertext;
    TextView tvRefreshtime;

    View view;//头部的view
    private int state;//控件的状态
    private int hight;//view控件的高度
    private float downy;//初始状态的Y轴位置
    private float movey;//移动以后的，view正在加载的位置
    private static final int chushi=0;//初始状态
    private static final int zhunbei=1;//准备刷新状态
    private static final int zhengzai=2;//正在刷新状态
    private RotateAnimation upAnimation;//箭头向上
    private RotateAnimation downAnimation;//箭头向下
    private boolean flag=false;
    private Onlister onlister;
    private int firstviewitme;

    //下拉加载的view
    private View footview;
    private ProgressBar footPb;
    private TextView footTv;
    //是否下拉
    private boolean loading=false;

    public newlistview(Context context) {
        this(context, null);
    }

    public newlistview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public newlistview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initof(context);
        initAnimation(context);
        initto(context);
        this.setOnScrollListener(this);

    }

    //下拉刷新，上拉加载设置
    public void initof(Context context) {


        //找到控件
        view = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_head, null);

        ivRefresher= (ImageView) view.findViewById(R.id.iv_refresher);
        pbRefresher=(ProgressBar) view.findViewById(R.id.pb_refresher);
        tvRefreshertext = (TextView) view.findViewById(R.id.tv_refreshertext);
        tvRefreshtime = (TextView) view.findViewById(R.id.tv_refreshtime);

        //获取控件高度
        view.measure(0,0);
        hight=view.getMeasuredHeight();

        view.setPadding(0,-hight,0,0);

        //将view添加到listview头部view+
        addHeaderView(view);

    }

    //手指滑动状态  action_down，action_move，action_up
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN://按下去的状态(按下去)
                downy=ev.getY();
                flag=false;
                break;
            case MotionEvent.ACTION_MOVE://准备加载的状态（也是过渡）（移动）
                //这个if方法只是作为一个判定机制，用于在准备加载过程中出现正在加载的转态
                if(state==zhunbei){

                    return false;
                }

                movey=ev.getY();

                if(firstviewitme==0 && movey-downy>0){
                    flag=true;
                    float highthadding=-hight+(movey-downy);
                    //头部已经完全滑动出来 且 状态为初始化状态
                    if(highthadding>=0&&state==chushi){
                        state=zhunbei;
                        changeview();//调用方法改变头部图片动画
                    }
                    if(highthadding<0&&state==zhunbei){
                        state=chushi;
                        changeview();
                    }
                    view.setPadding(0,(int)highthadding,0,0);
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP://正在加载的状态（抬起）
               //如果状态为准备刷新
                if(state==zhunbei){
                    state=zhengzai;//修改状态
                    changeview();//调用不同状态的方法
                   if(onlister!=null){

                    onlister.ondown();

                    }
                    view.setPadding(0,0,0,0);


                }else if(state==chushi){
                    //如果状态等于初始化状态，设置头部隐藏
                    view.setPadding(0,-hight,0,0);
                }

                break;
        }


        return super.onTouchEvent(ev);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if(getLastVisiblePosition()==getCount()-1&&loading==false) {  //  OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
            if(scrollState== OnScrollListener.SCROLL_STATE_TOUCH_SCROLL||scrollState== OnScrollListener.SCROLL_STATE_IDLE){
                //界面改变&&加载数据&&界面改变
                //改变状态
                loading=true;//正在加载
                //改变界面
                changeloading();
                if(onlister!=null){
                    onlister.onup();//加载更多数据
                }


            }

        }


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstviewitme=firstVisibleItem;
    }

    public interface Onlister{

        void ondown();//下拉刷新
         void onup();//上拉加载
    }

    public void setOnlister(Onlister onlister){
        this.onlister=onlister;
    }

    public void initAnimation(Context context){
        //0-》180：选择中心点在自身的原点
        upAnimation=new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setFillAfter(true);
        upAnimation.setDuration(5000);
        downAnimation=new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setFillAfter(true);
        downAnimation.setDuration(5000);
    }


    //根据state状态来改变不同控件的界面设计
    public void changeview() {
        switch (state) {
            case chushi:
                pbRefresher.setVisibility(View.INVISIBLE);
                ivRefresher.setVisibility(View.VISIBLE);
                ivRefresher.startAnimation(downAnimation);//设置箭头朝下转
                tvRefreshertext.setText("下拉刷新");
                tvRefreshtime.setVisibility(View.INVISIBLE);
                break;
            case zhunbei:
                pbRefresher.setVisibility(View.INVISIBLE);
                ivRefresher.setVisibility(View.VISIBLE);
                ivRefresher.startAnimation(upAnimation);////设置箭头朝下
                tvRefreshertext.setText("释放刷新");
                tvRefreshtime.setVisibility(View.INVISIBLE);
                break;
            case zhengzai:
                pbRefresher.setVisibility(View.VISIBLE);
                ivRefresher.setVisibility(View.INVISIBLE);
                ivRefresher.clearAnimation();//清除imagview的动画
                tvRefreshertext.setText("正在刷新");
                tvRefreshtime.setVisibility(View.VISIBLE);
                tvRefreshtime.setText(Time());//刷新时间
                break;
        }
    }

        public String Time(){

        String time=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());

        return time;
    }



    public void  initto(Context context){

        footview = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_footer, null);

        footPb= (ProgressBar) footview.findViewById(R.id.footer_progressbar);
        footTv= (TextView) footview.findViewById(R.id.footer_hint_textview);
        addFooterView(footview);
    }



    //判断显示哪个界面

    public void changeloading(){
        if(loading){
            footPb.setVisibility(VISIBLE);
            footTv.setVisibility(GONE);
        }else{
            footPb.setVisibility(GONE);
            footTv.setVisibility(VISIBLE);

        }
    }

    public void fanghui(){
        view.setPadding(0,-hight,0,0);
        state=chushi;
        changeview();
    }

    public void completeLoad(){
        loading=false;//加载完成
        changeloading();

    }



}


