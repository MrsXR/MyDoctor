package cn.gem.application;

import android.app.Application;


import android.util.Log;




import org.xutils.x;

import cn.gem.entity.UserTbl;

/**
 * Created by dliu on 2016/9/30.
 *
 * 1、MyApplication初始化
 *
 */
public class MyApplication extends Application {
    private int themeId;

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    private String findName;

    public String getFindName() {
        return findName;
    }

    public void setFindName(String findName) {
        this.findName = findName;
    }

    private UserTbl userTbl1=new UserTbl(1,"鹏袜子");

    public UserTbl getUserTbl1() {
        return userTbl1;
    }

    public void setUserTbl1(UserTbl userTbl1) {
        this.userTbl1 = userTbl1;
    }

    private static UserTbl userTbl=new UserTbl(1,"林奇","18860418843","342222199501020043");

    public static UserTbl getUserTbl() {
        return userTbl;
    }

    public void setUserTbl(UserTbl userTbl) {
        this.userTbl = userTbl;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyApplication", "onCreate: ");
        x.Ext.init(this);
    }

}