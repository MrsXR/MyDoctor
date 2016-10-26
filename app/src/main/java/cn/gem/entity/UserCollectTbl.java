package cn.gem.entity;
/*
 * 收藏
 */

import java.util.Date;

public class UserCollectTbl {
	private int userCollectId;
	private int userId;
	private int userCollectType;
	private Date userCollectTime;
	private int userCollectDetailId;
	
	
	public int getUserCollectId() {
		return userCollectId;
	}
	public void setUserCollectId(int userCollectId) {
		this.userCollectId = userCollectId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserCollectType() {
		return userCollectType;
	}
	public void setUserCollectType(int userCollectType) {
		this.userCollectType = userCollectType;
	}
	public Date getUserCollectTime() {
		return userCollectTime;
	}
	public void setUserCollectTime(Date userCollectTime) {
		this.userCollectTime = userCollectTime;
	}
	public int getUserCollectDetailId() {
		return userCollectDetailId;
	}
	public void setUserCollectDetailId(int userCollectDetailId) {
		this.userCollectDetailId = userCollectDetailId;
	}
	

}
