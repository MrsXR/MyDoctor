package cn.gem.entity;

/**
 * Created by admin on 2016/10/19.
 */

public class UserRecordTbl {

    private int userrecordId;
    private int userId;
    private String userrecordPhoto;
    private String userrecordPhone;
    private String userrecordName;
    private int userrecordAge;
    private int userrecordSex;
    private float userrecordHeight;
    private float userrecordWeight;
    private int userrecordIdentity;
    private String userrecordCard;

    public UserRecordTbl(int userrecordId, int userId, String userrecordPhoto, String userrecordPhone, String userrecordName, int userrecordAge, int userrecordSex, float userrecordHeight, float userrecordWeight, int userrecordIdentity, String userrecordCard) {
        this.userrecordId = userrecordId;
        this.userId = userId;
        this.userrecordPhoto = userrecordPhoto;
        this.userrecordPhone = userrecordPhone;
        this.userrecordName = userrecordName;
        this.userrecordAge = userrecordAge;
        this.userrecordSex = userrecordSex;
        this.userrecordHeight = userrecordHeight;
        this.userrecordWeight = userrecordWeight;
        this.userrecordIdentity = userrecordIdentity;
        this.userrecordCard = userrecordCard;
    }

    public int getUserrecordId() {
        return userrecordId;
    }

    public void setUserrecordId(int userrecordId) {
        this.userrecordId = userrecordId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserrecordPhoto() {
        return userrecordPhoto;
    }

    public void setUserrecordPhoto(String userrecordPhoto) {
        this.userrecordPhoto = userrecordPhoto;
    }

    public String getUserrecordPhone() {
        return userrecordPhone;
    }

    public void setUserrecordPhone(String userrecordPhone) {
        this.userrecordPhone = userrecordPhone;
    }

    public String getUserrecordName() {
        return userrecordName;
    }

    public void setUserrecordName(String userrecordName) {
        this.userrecordName = userrecordName;
    }

    public int getUserrecordAge() {
        return userrecordAge;
    }

    public void setUserrecordAge(int userrecordAge) {
        this.userrecordAge = userrecordAge;
    }

    public int getUserrecordSex() {
        return userrecordSex;
    }

    public void setUserrecordSex(int userrecordSex) {
        this.userrecordSex = userrecordSex;
    }

    public float getUserrecordHeight() {
        return userrecordHeight;
    }

    public void setUserrecordHeight(float userrecordHeight) {
        this.userrecordHeight = userrecordHeight;
    }

    public float getUserrecordWeight() {
        return userrecordWeight;
    }

    public void setUserrecordWeight(float userrecordWeight) {
        this.userrecordWeight = userrecordWeight;
    }

    public int getUserrecordIdentity() {
        return userrecordIdentity;
    }

    public void setUserrecordIdentity(int userrecordIdentity) {
        this.userrecordIdentity = userrecordIdentity;
    }

    public String getUserrecordCard() {
        return userrecordCard;
    }

    public void setUserrecordCard(String userrecordCard) {
        this.userrecordCard = userrecordCard;
    }
}
