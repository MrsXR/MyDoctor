package cn.gem.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 帖子话题表
 * @author sony
 *
 */
public class ThemeTbl implements Parcelable {
	private int themeId;
	private String themeName;
	private int moduleId;
	private UserTbl userTbl;
	private Date themeTime;
	private String themeContent;
	private int doctorsId;
	private int answerNum;
	private int lookNumber;
    private String themePhoto;
	private ModuleTbl moduleTbl;

	private String themePhotoUrl;

	public String getThemePhotoUrl() {
		return themePhotoUrl;
	}

	public void setThemePhotoUrl(String themePhotoUrl) {
		this.themePhotoUrl = themePhotoUrl;
	}

	public ModuleTbl getModuleTbl() {
		return moduleTbl;
	}

	public void setModuleTbl(ModuleTbl moduleTbl) {
		this.moduleTbl = moduleTbl;
	}

	public ThemeTbl(UserTbl userTbl, String themeName, String themeContent, Date themeTime) {
		this.themeName = themeName;
		this.themeTime = themeTime;

		this.themeContent=themeContent;
		this.userTbl=userTbl;
	}
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

	public String getThemePhoto() {
		return themePhoto;
	}

	public void setThemePhoto(String themePhoto) {
		this.themePhoto = themePhoto;
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
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public UserTbl getUserTbl() {
		return userTbl;
	}

	public void setUserTbl(UserTbl userTbl) {
		this.userTbl = userTbl;
	}

	public Date getThemeTime() {
		return themeTime;
	}
	public void setThemeTime(Date themeTime) {
		this.themeTime = themeTime;
	}
	public String getThemeContent() {
		return themeContent;
	}
	public void setThemeContent(String themeContent) {
		this.themeContent = themeContent;
	}
	public int getDoctorsId() {
		return doctorsId;
	}
	public void setDoctorsId(int doctorsId) {
		this.doctorsId = doctorsId;
	}

	private int userId;
	private String userSname;
	private String moduleSname;
	private int lookNum;


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserSname() {
		return userSname;
	}

	public void setUserSname(String userSname) {
		this.userSname = userSname;
	}

	public String getModuleSname() {
		return moduleSname;
	}

	public void setModuleSname(String moduleSname) {
		this.moduleSname = moduleSname;
	}

	public int getLookNum() {
		return lookNum;
	}

	public void setLookNum(int lookNum) {
		this.lookNum = lookNum;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.themeId);
		dest.writeString(this.themeName);
		dest.writeInt(this.moduleId);
		dest.writeParcelable(this.userTbl, flags);
		dest.writeLong(this.themeTime != null ? this.themeTime.getTime() : -1);
		dest.writeString(this.themeContent);
		dest.writeInt(this.doctorsId);
		dest.writeInt(this.answerNum);
		dest.writeInt(this.lookNumber);
		dest.writeString(this.themePhoto);
		dest.writeParcelable(this.moduleTbl, flags);
		dest.writeString(this.themePhotoUrl);
		dest.writeInt(this.userId);
		dest.writeString(this.userSname);
		dest.writeString(this.moduleSname);
		dest.writeInt(this.lookNum);
	}

	protected ThemeTbl(Parcel in) {
		this.themeId = in.readInt();
		this.themeName = in.readString();
		this.moduleId = in.readInt();
		this.userTbl = in.readParcelable(UserTbl.class.getClassLoader());
		long tmpThemeTime = in.readLong();
		this.themeTime = tmpThemeTime == -1 ? null : new Date(tmpThemeTime);
		this.themeContent = in.readString();
		this.doctorsId = in.readInt();
		this.answerNum = in.readInt();
		this.lookNumber = in.readInt();
		this.themePhoto = in.readString();
		this.moduleTbl = in.readParcelable(ModuleTbl.class.getClassLoader());
		this.themePhotoUrl = in.readString();
		this.userId = in.readInt();
		this.userSname = in.readString();
		this.moduleSname = in.readString();
		this.lookNum = in.readInt();
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
}
