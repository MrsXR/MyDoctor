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
	private Timestamp commentOrderDetailTime;
	
	
	
	
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
