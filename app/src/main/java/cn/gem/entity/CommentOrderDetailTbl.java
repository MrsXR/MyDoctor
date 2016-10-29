package cn.gem.entity;

import java.sql.Timestamp;


/**
 * 评论表
 * @author sony
 *
 */
public class CommentOrderDetailTbl {
	private int commentOrderDetailId;
	private int orderId;
	private int userId;
	private int doctorsId;
	private String commentOrderDetailContent;
	private int commentOrderDetailType;
	private int commentOrderDetailAttitude;
	private int commentOrderDetailTreat;
	private Timestamp commentOrderDetailTime;
	private String orderIllSname;
	private String userSname;

	public CommentOrderDetailTbl(int orderId, int doctorsId, int userId, String commentOrderDetailContent, int commentOrderDetailType, int commentOrderDetailAttitude, int commentOrderDetailTreat, Timestamp commentOrderDetailTime, String orderIllSname, String userSname) {
		this.orderId = orderId;
		this.doctorsId = doctorsId;
		this.userId = userId;
		this.commentOrderDetailContent = commentOrderDetailContent;
		this.commentOrderDetailType = commentOrderDetailType;
		this.commentOrderDetailAttitude = commentOrderDetailAttitude;
		this.commentOrderDetailTreat = commentOrderDetailTreat;
		this.commentOrderDetailTime = commentOrderDetailTime;
		this.orderIllSname = orderIllSname;
		this.userSname = userSname;
	}

	public String getUserSname() {
		return userSname;
	}

	public void setUserSname(String userSname) {
		this.userSname = userSname;
	}

	public String getOrderIllSname() {
		return orderIllSname;
	}
	public void setOrderIllSname(String orderIllSname) {
		this.orderIllSname = orderIllSname;
	}
	public CommentOrderDetailTbl(int commentOrderDetailId, int orderId, int userId, int doctorsId,
								 String commentOrderDetailContent, int commentOrderDetailType, int commentOrderDetailAttitude,
								 int commentOrderDetailTreat, Timestamp commentOrderDetailTime) {
		super();
		this.commentOrderDetailId = commentOrderDetailId;
		this.orderId = orderId;
		this.userId = userId;
		this.doctorsId = doctorsId;
		this.commentOrderDetailContent = commentOrderDetailContent;
		this.commentOrderDetailType = commentOrderDetailType;
		this.commentOrderDetailAttitude = commentOrderDetailAttitude;
		this.commentOrderDetailTreat = commentOrderDetailTreat;
		this.commentOrderDetailTime = commentOrderDetailTime;
	}
	public int getDoctorsId() {
		return doctorsId;
	}
	public void setDoctorsId(int doctorsId) {
		this.doctorsId = doctorsId;
	}
	public int getCommentOrderDetailAttitude() {
		return commentOrderDetailAttitude;
	}
	public void setCommentOrderDetailAttitude(int commentOrderDetailAttitude) {
		this.commentOrderDetailAttitude = commentOrderDetailAttitude;
	}
	public int getCommentOrderDetailTreat() {
		return commentOrderDetailTreat;
	}
	public void setCommentOrderDetailTreat(int commentOrderDetailTreat) {
		this.commentOrderDetailTreat = commentOrderDetailTreat;
	}

	public Timestamp getCommentOrderDetailTime() {
		return commentOrderDetailTime;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getDoctorId() {
		return doctorsId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorsId = doctorId;
	}
	public void setCommentOrderDetailTime(Timestamp commentOrderDetailTime) {
		this.commentOrderDetailTime = commentOrderDetailTime;
	}
	public int getCommentOrderDetailId() {
		return commentOrderDetailId;
	}
	public void setCommentOrderDetailId(int commentOrderDetailId) {
		this.commentOrderDetailId = commentOrderDetailId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getCommentOrderDetailContent() {
		return commentOrderDetailContent;
	}
	public void setCommentOrderDetailContent(String commentOrderDetailContent) {
		this.commentOrderDetailContent = commentOrderDetailContent;
	}
	public int getCommentOrderDetailType() {
		return commentOrderDetailType;
	}
	public void setCommentOrderDetailType(int commentOrderDetailType) {
		this.commentOrderDetailType = commentOrderDetailType;
	}


}
