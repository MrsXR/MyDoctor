package cn.gem.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 预约表
 * @author sony
 * 1:预约中，2:预约成功，3:取消预约
 *
 */
public class OrderTbl {

	private int orderId;
	private int userId;
	private int doctorsId;
	private String userPhone;
	private String orderIllSname;
	private String orderMessage;
	private String orderMessageTime;
	private Timestamp orderMessageWriteTime;
	private int orderPayState;
	private float ordertimeDetailPrice;
	private String orderIllPhotoOne;
	private String orderIllPhotoTwo;








	public OrderTbl(String userPhone, String orderIllSname, String orderMessage, int orderPayState,
					float ordertime_detail_price) {
		super();
		this.userPhone = userPhone;
		this.orderIllSname = orderIllSname;
		this.orderMessage = orderMessage;
		this.orderPayState = orderPayState;
		this.ordertimeDetailPrice = ordertime_detail_price;
	}
	public OrderTbl(int userId, int doctorsId, String userPhone, String orderIllSname, String orderMessage,
					String orderMessageTime, Timestamp orderMessageWriteTime, int orderPayState, float orderState,
					String orderIllPhotoOne) {
		super();
		this.userId = userId;
		this.doctorsId = doctorsId;
		this.userPhone = userPhone;
		this.orderIllSname = orderIllSname;
		this.orderMessage = orderMessage;
		this.orderMessageTime = orderMessageTime;
		this.orderMessageWriteTime = orderMessageWriteTime;
		this.orderPayState = orderPayState;
		this.ordertimeDetailPrice = orderState;
		this.orderIllPhotoOne = orderIllPhotoOne;
	}
	public OrderTbl(int userId, int doctorsId, String userPhone, String orderIllSname, String orderMessage,
					String orderMessageTime, Timestamp orderMessageWriteTime, int orderPayState, float orderState,
					String orderIllPhotoOne, String orderIllPhotoTwo) {
		super();
		this.userId = userId;
		this.doctorsId = doctorsId;
		this.userPhone = userPhone;
		this.orderIllSname = orderIllSname;
		this.orderMessage = orderMessage;
		this.orderMessageTime = orderMessageTime;
		this.orderMessageWriteTime = orderMessageWriteTime;
		this.orderPayState = orderPayState;
		this.ordertimeDetailPrice = orderState;
		this.orderIllPhotoOne = orderIllPhotoOne;
		this.orderIllPhotoTwo = orderIllPhotoTwo;
	}
	public String getOrderIllPhotoOne() {
		return orderIllPhotoOne;
	}
	public void setOrderIllPhotoOne(String orderIllPhotoOne) {
		this.orderIllPhotoOne = orderIllPhotoOne;
	}
	public String getOrderIllPhotoTwo() {
		return orderIllPhotoTwo;
	}
	public void setOrderIllPhotoTwo(String orderIllPhotoTwo) {
		this.orderIllPhotoTwo = orderIllPhotoTwo;
	}


	public OrderTbl(int userId, int doctorsId, String userPhone, String orderIllSname, String orderMessage,
					String orderMessageTime, Timestamp orderMessageWriteTime, int orderPayState, float orderState) {
		super();
		this.userId = userId;
		this.doctorsId = doctorsId;
		this.userPhone = userPhone;
		this.orderIllSname = orderIllSname;
		this.orderMessage = orderMessage;
		this.orderMessageTime = orderMessageTime;
		this.orderMessageWriteTime = orderMessageWriteTime;
		this.orderPayState = orderPayState;
		this.ordertimeDetailPrice = orderState;
	}
	public OrderTbl(int orderId, int userId, int doctorsId, String userPhone, String orderIllSname, String orderMessage,
					String orderMessageTime, Timestamp orderMessageWriteTime, int orderPayState, float orderState) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.doctorsId = doctorsId;
		this.userPhone = userPhone;
		this.orderIllSname = orderIllSname;
		this.orderMessage = orderMessage;
		this.orderMessageTime = orderMessageTime;
		this.orderMessageWriteTime = orderMessageWriteTime;
		this.orderPayState = orderPayState;
		this.ordertimeDetailPrice = orderState;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getDoctorsId() {
		return doctorsId;
	}
	public void setDoctorsId(int doctorsId) {
		this.doctorsId = doctorsId;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getOrderIllSname() {
		return orderIllSname;
	}
	public void setOrderIllSname(String orderIllSname) {
		this.orderIllSname = orderIllSname;
	}
	public String getOrderMessage() {
		return orderMessage;
	}
	public void setOrderMessage(String orderMessage) {
		this.orderMessage = orderMessage;
	}
	public String getOrderMessageTime() {
		return orderMessageTime;
	}
	public void setOrderMessageTime(String orderMessageTime) {
		this.orderMessageTime = orderMessageTime;
	}
	public Timestamp getOrderMessageWriteTime() {
		return orderMessageWriteTime;
	}
	public void setOrderMessageWriteTime(Timestamp orderMessageWriteTime) {
		this.orderMessageWriteTime = orderMessageWriteTime;
	}
	public int getOrderPayState() {
		return orderPayState;
	}
	public void setOrderPayState(int orderPayState) {
		this.orderPayState = orderPayState;
	}
	public double getOrderState() {
		return ordertimeDetailPrice;
	}
	public void setOrderState(float orderState) {
		this.ordertimeDetailPrice = orderState;
	}





}
