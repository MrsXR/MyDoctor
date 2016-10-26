package cn.gem.entity;

import java.sql.Timestamp;

/**
 * 帖子评论表
 * 
 * @author sony
 *
 */
public class ThemeDetailTbl {

	private int themeDetailId;
	private int themeId;
	private UserTbl userTbl;
	private ThemeDetailTbl fatherThemeDetail;
	private String themeDetailPhoto;
	private String themeDetailContent;
	private Timestamp themeDetailTime;
	private int themeDetailIs;
	
	


	public ThemeDetailTbl(int themeDetailId,int themeId) {
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







}
