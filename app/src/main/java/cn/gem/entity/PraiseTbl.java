package cn.gem.entity;

public class PraiseTbl {
   private int praiseId;
   private int themeId;
   private int themeDetailId;
   private int userId;
	private int ArticleId;

	public PraiseTbl(int themeId, int userId) {
		this.themeId = themeId;
		this.userId = userId;
	}

	public PraiseTbl(int praiseId, int themeId, int themeDetailId, int userId, int articleId) {
	super();
	this.praiseId = praiseId;
	this.themeId = themeId;
	this.themeDetailId = themeDetailId;
	this.userId = userId;

	ArticleId = articleId;
}
public int getPraiseId() {
	return praiseId;
}
public void setPraiseId(int praiseId) {
	this.praiseId = praiseId;
}
public int getThemeId() {
	return themeId;
}
public void setThemeId(int themeId) {
	this.themeId = themeId;
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

public int getArticleId() {
	return ArticleId;
}
public void setArticleId(int articleId) {
	ArticleId = articleId;
}


	@Override
	public String toString() {
		return "PraiseTbl{" +
				"praiseId=" + praiseId +
				", themeId=" + themeId +
				", themeDetailId=" + themeDetailId +
				", userId=" + userId +

				", ArticleId=" + ArticleId +
				'}';
	}
}
