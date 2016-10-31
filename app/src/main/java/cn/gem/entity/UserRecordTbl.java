package cn.gem.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2016/10/19.
 */

public class UserRecordTbl implements Parcelable {

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

    public UserRecordTbl(int userId, String userrecordPhoto, String userrecordPhone, String userrecordName, int userrecordAge, int userrecordSex, float userrecordHeight, float userrecordWeight, int userrecordIdentity, String userrecordCard) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userrecordId);
        dest.writeInt(this.userId);
        dest.writeString(this.userrecordPhoto);
        dest.writeString(this.userrecordPhone);
        dest.writeString(this.userrecordName);
        dest.writeInt(this.userrecordAge);
        dest.writeInt(this.userrecordSex);
        dest.writeFloat(this.userrecordHeight);
        dest.writeFloat(this.userrecordWeight);
        dest.writeInt(this.userrecordIdentity);
        dest.writeString(this.userrecordCard);
    }

    protected UserRecordTbl(Parcel in) {
        this.userrecordId = in.readInt();
        this.userId = in.readInt();
        this.userrecordPhoto = in.readString();
        this.userrecordPhone = in.readString();
        this.userrecordName = in.readString();
        this.userrecordAge = in.readInt();
        this.userrecordSex = in.readInt();
        this.userrecordHeight = in.readFloat();
        this.userrecordWeight = in.readFloat();
        this.userrecordIdentity = in.readInt();
        this.userrecordCard = in.readString();
    }

    public static final Parcelable.Creator<UserRecordTbl> CREATOR = new Parcelable.Creator<UserRecordTbl>() {
        @Override
        public UserRecordTbl createFromParcel(Parcel source) {
            return new UserRecordTbl(source);
        }

        @Override
        public UserRecordTbl[] newArray(int size) {
            return new UserRecordTbl[size];
        }
    };
}
