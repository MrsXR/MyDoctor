package cn.gem.entity1;

import java.util.Date;

/**
 * 帖子话题详情表
 * @author sony
 *
 */
public class ThemeDetailTbl {

	private int themeDetailId;
	private int themeId;
	private String themePhoto;
	private int userId;
	private String themeDetailPhoto;
	private String themeDetailContent;
	private Date themeDetailTime;
	private int themeDetailUserId;
	private int themeDetailIs;

	public String getThemeDetailContent() {
		return themeDetailContent;
	}

	public void setThemeDetailContent(String themeDetailContent) {
		this.themeDetailContent = themeDetailContent;
	}

	public int getThemeDetailId() {
		return themeDetailId;
	}

	public void setThemeDetailId(int themeDetailId) {
		this.themeDetailId = themeDetailId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getThemePhoto() {
		return themePhoto;
	}

	public void setThemePhoto(String themePhoto) {
		this.themePhoto = themePhoto;
	}

	public int getThemeId() {
		return themeId;
	}

	public void setThemeId(int themeId) {
		this.themeId = themeId;
	}

	public int getThemeDetailUserId() {
		return themeDetailUserId;
	}

	public void setThemeDetailUserId(int themeDetailUserId) {
		this.themeDetailUserId = themeDetailUserId;
	}

	public Date getThemeDetailTime() {
		return themeDetailTime;
	}

	public void setThemeDetailTime(Date themeDetailTime) {
		this.themeDetailTime = themeDetailTime;
	}

	public String getThemeDetailPhoto() {
		return themeDetailPhoto;
	}

	public void setThemeDetailPhoto(String themeDetailPhoto) {
		this.themeDetailPhoto = themeDetailPhoto;
	}

	public int getThemeDetailIs() {
		return themeDetailIs;
	}

	public void setThemeDetailIs(int themeDetailIs) {
		this.themeDetailIs = themeDetailIs;
	}
}