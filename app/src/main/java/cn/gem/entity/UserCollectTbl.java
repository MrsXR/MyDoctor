package cn.gem.entity;
/*
 * 收藏
 */

import java.sql.Timestamp;

public class UserCollectTbl {
	private int userCollectId;
	private int userId;
	private int userCollectType;
	private Timestamp userCollectTime;
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
	public Timestamp getUserCollectTime() {
		return userCollectTime;
	}
	public void setUserCollectTime(Timestamp userCollectTime) {
		this.userCollectTime = userCollectTime;
	}
	public int getUserCollectDetailId() {
		return userCollectDetailId;
	}
	public void setUserCollectDetailId(int userCollectDetailId) {
		this.userCollectDetailId = userCollectDetailId;
	}


}
