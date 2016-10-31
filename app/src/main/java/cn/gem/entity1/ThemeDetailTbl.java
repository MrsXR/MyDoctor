package cn.gem.entity1;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

import cn.gem.entity.UserTbl;

/**
 * 帖子评论表
 * 
 * @author sony
 *
 */
public class ThemeDetailTbl implements Parcelable {

	private int themeDetailId;
	private int themeId;
	private UserTbl userTbl;
	private ThemeDetailTbl fatherThemeDetail;
	private String themeDetailPhoto;
	private String themeDetailContent;
	private Timestamp themeDetailTime;
	private int themeDetailIs;

	public ThemeDetailTbl(int themeDetailId, UserTbl userTbl, int themeDetailIs) {
		this.themeDetailId = themeDetailId;
		this.userTbl = userTbl;
		this.themeDetailIs = themeDetailIs;
	}

	public ThemeDetailTbl(int themeDetailId, int themeId) {
		super();
		this.themeDetailId = themeDetailId;
		this.themeId=themeId;
	}



	public ThemeDetailTbl(int themeDetailId, int themeId, UserTbl userTbl, String themeDetailPhoto,
			String themeDetailContent, Timestamp themeDetailTime, int themeDetailIs) {
		super();
		this.themeDetailId = themeDetailId;
		this.themeId = themeId;
		this.userTbl = userTbl;
		this.themeDetailPhoto = themeDetailPhoto;
		this.themeDetailContent = themeDetailContent;
		this.themeDetailTime = themeDetailTime;
		this.themeDetailIs = themeDetailIs;
	}



	public ThemeDetailTbl( int themeId, UserTbl userTbl,ThemeDetailTbl fatherThemeDetail, String themeDetailPhoto,
			String themeDetailContent, Timestamp themeDetailTime, int themeDetailIs) {
		super();
		this.themeDetailId = themeDetailId;
		this.themeId = themeId;
		this.userTbl = userTbl;
		this.fatherThemeDetail=fatherThemeDetail;
		this.themeDetailPhoto = themeDetailPhoto;
		this.themeDetailContent = themeDetailContent;
		this.themeDetailTime = themeDetailTime;
		this.themeDetailIs = themeDetailIs;
	}



	public ThemeDetailTbl(int themeDetailId, int themeId, UserTbl userTbl,ThemeDetailTbl fatherThemeDetail, String themeDetailPhoto,
			String themeDetailContent, Timestamp themeDetailTime, int themeDetailIs) {
		super();
		this.themeDetailId = themeDetailId;
		this.themeId = themeId;
		this.userTbl = userTbl;
		this.fatherThemeDetail = fatherThemeDetail;
		this.themeDetailPhoto = themeDetailPhoto;
		this.themeDetailContent = themeDetailContent;
		this.themeDetailTime = themeDetailTime;
		this.themeDetailIs = themeDetailIs;
	}



	public ThemeDetailTbl(int themeId, UserTbl userTbl,ThemeDetailTbl fatherThemeDetail, String themeDetailContent,
			Timestamp themeDetailTime, int themeDetailIs) {
		super();
		this.themeId = themeId;
		this.userTbl = userTbl;
		this.fatherThemeDetail =fatherThemeDetail;
		this.themeDetailContent = themeDetailContent;
		this.themeDetailTime = themeDetailTime;
		this.themeDetailIs = themeDetailIs;
	}



	public String getThemeDetailContent() {
		return themeDetailContent;
	}



	public void setThemeDetailContent(String themeDetailContent) {
		this.themeDetailContent = themeDetailContent;
	}



	public ThemeDetailTbl() {
		super();

	}



	public int getThemeDetailId() {
		return themeDetailId;
	}

	public void setThemeDetailId(int themeDetailId) {
		this.themeDetailId = themeDetailId;
	}

	public int getThemeId() {
		return themeId;
	}

	public void setThemeId(int themeId) {
		this.themeId = themeId;
	}

	public UserTbl getUserTbl() {
		return userTbl;
	}

	public void setUserTbl(UserTbl userTbl) {
		this.userTbl = userTbl;
	}

	public String getThemeDetailPhoto() {
		return themeDetailPhoto;
	}

	public void setThemeDetailPhoto(String themeDetailPhoto) {
		this.themeDetailPhoto = themeDetailPhoto;
	}
   public Timestamp getThemeDetailTime() {
		return themeDetailTime;
	}

	public void setThemeDetailTime(Timestamp themeDetailTime) {
		this.themeDetailTime = themeDetailTime;
	}








	public ThemeDetailTbl getFatherThemeDetail() {
		return fatherThemeDetail;
	}



	public void setFatherThemeDetail(ThemeDetailTbl fatherThemeDetail) {
		this.fatherThemeDetail = fatherThemeDetail;
	}



	public int getThemeDetailIs() {
		return themeDetailIs;
	}

	public void setThemeDetailIs(int themeDetailIs) {
		this.themeDetailIs = themeDetailIs;
	}



	@Override
	public String toString() {
		return "ThemeDetailTbl [themeDetailId=" + themeDetailId + ", themeId=" + themeId + ", userTbl=" + userTbl
				+ ", fatherThemeDetail=" + fatherThemeDetail + ", themeDetailPhoto=" + themeDetailPhoto + ", themeDetailContent="
				+ themeDetailContent + ", themeDetailTime=" + themeDetailTime + ", themeDetailIs=" + themeDetailIs
				+ "]";
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.themeDetailId);
		dest.writeInt(this.themeId);
		dest.writeParcelable(this.userTbl, flags);
		dest.writeParcelable(this.fatherThemeDetail, flags);
		dest.writeString(this.themeDetailPhoto);
		dest.writeString(this.themeDetailContent);
		dest.writeSerializable(this.themeDetailTime);
		dest.writeInt(this.themeDetailIs);
	}

	protected ThemeDetailTbl(Parcel in) {
		this.themeDetailId = in.readInt();
		this.themeId = in.readInt();
		this.userTbl = in.readParcelable(UserTbl.class.getClassLoader());
		this.fatherThemeDetail = in.readParcelable(ThemeDetailTbl.class.getClassLoader());
		this.themeDetailPhoto = in.readString();
		this.themeDetailContent = in.readString();
		this.themeDetailTime = (Timestamp) in.readSerializable();
		this.themeDetailIs = in.readInt();
	}

	public static final Creator<ThemeDetailTbl> CREATOR = new Creator<ThemeDetailTbl>() {
		@Override
		public ThemeDetailTbl createFromParcel(Parcel source) {
			return new ThemeDetailTbl(source);
		}

		@Override
		public ThemeDetailTbl[] newArray(int size) {
			return new ThemeDetailTbl[size];
		}
	};
}
