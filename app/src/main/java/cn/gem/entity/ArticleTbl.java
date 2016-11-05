package cn.gem.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

/**
 * 文章表
 * @author sony
 *
 */
public class ArticleTbl implements Parcelable {

	private int articleId;
	private String articleTitle;
	private String articleContext;
	private String articlePhoto;
	private int articleReadnumber;
    private Timestamp articleTime;
    private int collectId;
	private String articleFrom;


	public ArticleTbl( String articleTitle, int articleReadnumber,Timestamp articleTime) {
		this.articleTime = articleTime;
		this.articleReadnumber = articleReadnumber;
		this.articleTitle = articleTitle;
	}

	public String getArticleFrom() {
		return articleFrom;
	}

	public void setArticleFrom(String articleFrom) {
		this.articleFrom = articleFrom;
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


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.articleId);
		dest.writeString(this.articleTitle);
		dest.writeString(this.articleContext);
		dest.writeString(this.articlePhoto);
		dest.writeInt(this.articleReadnumber);
		dest.writeSerializable(this.articleTime);
		dest.writeInt(this.collectId);
		dest.writeString(this.articleFrom);
	}

	protected ArticleTbl(Parcel in) {
		this.articleId = in.readInt();
		this.articleTitle = in.readString();
		this.articleContext = in.readString();
		this.articlePhoto = in.readString();
		this.articleReadnumber = in.readInt();
		this.articleTime = (Timestamp) in.readSerializable();
		this.collectId = in.readInt();
		this.articleFrom = in.readString();
	}

	public static final Parcelable.Creator<ArticleTbl> CREATOR = new Parcelable.Creator<ArticleTbl>() {
		@Override
		public ArticleTbl createFromParcel(Parcel source) {
			return new ArticleTbl(source);
		}

		@Override
		public ArticleTbl[] newArray(int size) {
			return new ArticleTbl[size];
		}
	};
}
