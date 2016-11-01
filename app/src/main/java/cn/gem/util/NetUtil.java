package cn.gem.util;

import cn.gem.entity.UserTbl;

/**
 * Created by dliu on 2016/9/30.
 */
public class NetUtil {

    public static final String url="http://192.168.191.1:8080/MyDoctor/";

    public static final String image="http://192.168.191.1:8080/image/";


    public UserTbl getUser() {
        return u;
    }

    public void setUser(UserTbl u) {
        this.u = u;
    }

    UserTbl u=new UserTbl(1,"mydoctor");


}
