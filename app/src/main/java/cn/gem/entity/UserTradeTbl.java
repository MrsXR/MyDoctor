package cn.gem.entity;

import java.sql.Timestamp;

/**
 * Created by admin on 2016/10/19.
 */

public class UserTradeTbl {

    private int usertradeId;
    private String usertradeProject;
    private Timestamp usertradeDate;
    private float usertradePrice;
    private int userId;
    private double userBalance;

    private int num;

    public UserTradeTbl(String usertradeProject, Timestamp usertradeDate, float usertradePrice) {
        this.usertradeProject = usertradeProject;
        this.usertradeDate = usertradeDate;
        this.usertradePrice = usertradePrice;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getUsertradeDate() {
        return usertradeDate;
    }

    public void setUsertradeDate(Timestamp usertradeDate) {
        this.usertradeDate = usertradeDate;
    }

    public float getUsertradePrice() {
        return usertradePrice;
    }

    public void setUsertradePrice(float usertradePrice) {
        this.usertradePrice = usertradePrice;
    }

    public String getUsertradeProject() {
        return usertradeProject;
    }

    public void setUsertradeProject(String usertradeProject) {
        this.usertradeProject = usertradeProject;
    }

    public int getUsertradeId() {
        return usertradeId;
    }

    public void setUsertradeId(int usertradeId) {
        this.usertradeId = usertradeId;
    }
}
