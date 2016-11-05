package cn.gem.entity1;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.Date;

import cn.gem.entity.DoctorsTbl;
import cn.gem.entity.UserTbl;

/**
 * 帖子话题表
 * @author sony
 *
 */
public class ThemeTbl implements Parcelable {
	private int themeId;
	private String themeName;
	private ModuleTbl moduleTbl;
	private UserTbl userTbl;
	private Timestamp themeTime;
	private String themeContent;
	private DoctorsTbl doctorsTbl;
	private int answerNum;
	private int lookNumber;
	private String themePhotoUrl;


	public ThemeTbl(ModuleTbl moduleTbl,UserTbl userTbl,String themeName,String themeContent, Timestamp themeTime) {
		this.themeName = themeName;
		this.themeTime = themeTime;
		this.themeContent=themeContent;
		this.userTbl=userTbl;
		this.moduleTbl=moduleTbl;
	}


	public int getLookNumber() {
		return lookNumber;
	}

	public String getThemePhotoUrl() {
		return themePhotoUrl;
	}

	public void setThemePhotoUrl(String themePhotoUrl) {
		this.themePhotoUrl = themePhotoUrl;
	}

	public void setLookNumber(int lookNumber) {
		this.lookNumber = lookNumber;
	}

	public int getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(int answerNum) {
		this.answerNum = answerNum;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public int getThemeId() {
		return themeId;
	}
	public void setThemeId(int themeId) {
		this.themeId = themeId;
	}

	public ModuleTbl getModuleTbl() {
		return moduleTbl;
	}

	public void setModuleTbl(ModuleTbl moduleTbl) {
		this.moduleTbl = moduleTbl;
	}

	public UserTbl getUserTbl() {
		return userTbl;
	}

	public void setUserTbl(UserTbl userTbl) {
		this.userTbl = userTbl;
	}


	public String getThemeContent() {
		return themeContent;
	}
	public void setThemeContent(String themeContent) {
		this.themeContent = themeContent;
	}

	public Timestamp getThemeTime() {
		return themeTime;
	}

	public void setThemeTime(Timestamp themeTime) {
		this.themeTime = themeTime;
	}

	public DoctorsTbl getDoctorsTbl() {
		return doctorsTbl;
	}

	public void setDoctorsTbl(DoctorsTbl doctorsTbl) {
		this.doctorsTbl = doctorsTbl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.themeId);
		dest.writeString(this.themeContent);
		dest.writeParcelable(this.moduleTbl, flags);
		dest.writeParcelable(this.userTbl, flags);
		dest.writeSerializable(this.themeTime);
		dest.writeString(this.themeName);
		dest.writeParcelable(this.doctorsTbl, flags);
		dest.writeInt(this.answerNum);
		dest.writeInt(this.lookNumber);
		dest.writeString(this.themePhotoUrl);

	}

	protected ThemeTbl(Parcel in) {
		this.themeId = in.readInt();
		this.themeContent = in.readString();
		this.moduleTbl = in.readParcelable(ModuleTbl.class.getClassLoader());
		this.userTbl = in.readParcelable(UserTbl.class.getClassLoader());
		this.themeTime = (Timestamp) in.readSerializable();
		this.themeName = in.readString();
		this.doctorsTbl = in.readParcelable(DoctorsTbl.class.getClassLoader());
		this.answerNum = in.readInt();
		this.lookNumber = in.readInt();
		this.themePhotoUrl = in.readString();

	}

	public static final Parcelable.Creator<ThemeTbl> CREATOR = new Parcelable.Creator<ThemeTbl>() {
		@Override
		public ThemeTbl createFromParcel(Parcel source) {
			return new ThemeTbl(source);
		}

		@Override
		public ThemeTbl[] newArray(int size) {
			return new ThemeTbl[size];
		}
	};

	@Override
	public String toString() {
		return "ThemeTbl{" +
				"themeId=" + themeId +
				", themeName='" + themeName + '\'' +
				", moduleTbl=" + moduleTbl +
				", userTbl=" + userTbl +
				", themeTime=" + themeTime +
				", themeContent='" + themeContent + '\'' +
				", doctorsTbl=" + doctorsTbl +
				", answerNum=" + answerNum +
				", lookNumber=" + lookNumber +

				'}';
	}
}
