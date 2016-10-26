package cn.gem.entity;

import java.sql.Timestamp;

/**
 * 文章表
 * @author sony
 *
 */
public class ArticleTbl {

	private int articleId;
	private String articleTitle;
	private String articleContext;
	private String articlePhoto;
	private int articleReadnumber;
    private Timestamp articleTime;
    private int collectId;


	public ArticleTbl( String articleTitle, int articleReadnumber,Timestamp articleTime) {
		this.articleTime = articleTime;
		this.articleReadnumber = articleReadnumber;
		this.articleTitle = articleTitle;
	}

	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getArticleContext() {
		return articleContext;
	}
	public void setArticleContext(String articleContext) {
		this.articleContext = articleContext;
	}
	public String getArticlePhoto() {
		return articlePhoto;
	}
	public void setArticlePhoto(String articlePhoto) {
		this.articlePhoto = articlePhoto;
	}

	public int getArticleReadnumber() {
		return articleReadnumber;
	}

	public void setArticleReadnumber(int articleReadnumber) {
		this.articleReadnumber = articleReadnumber;
	}

	public Timestamp getArticleTime() {
		return articleTime;
	}

	public void setArticleTime(Timestamp articleTime) {
		this.articleTime = articleTime;
	}

	public int getCollectId() {
		return collectId;
	}
	public void setCollectId(int collectId) {
		this.collectId = collectId;
	}
    
    
}
