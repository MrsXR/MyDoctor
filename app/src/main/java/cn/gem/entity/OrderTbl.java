package cn.gem.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 预约表
 * @author sony
 * 1:预约中，2:预约成功，3:取消预约
 *
 */
public class OrderTbl implements Parcelable {

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
	private int ordertimeDetailId;//对应的预约的提供ID

	public OrderTbl(int orderId, int userId, String userPhone, int doctorsId, String orderIllSname, String orderMessage, String orderMessageTime, Timestamp orderMessageWriteTime, int orderPayState, int ordertimeDetailId) {
		this.orderId = orderId;
		this.userId = userId;
		this.userPhone = userPhone;
		this.doctorsId = doctorsId;
		this.orderIllSname = orderIllSname;
		this.orderMessage = orderMessage;
		this.orderMessageTime = orderMessageTime;
		this.orderMessageWriteTime = orderMessageWriteTime;
		this.orderPayState = orderPayState;
		this.ordertimeDetailId = ordertimeDetailId;
	}

	public OrderTbl( int userId, int doctorsId, String userPhone, String orderIllSname, String orderMessage, String orderMessageTime, Timestamp orderMessageWriteTime, int orderPayState, int ordertimeDetailId) {

		this.userId = userId;
		this.doctorsId = doctorsId;
		this.userPhone = userPhone;
		this.orderIllSname = orderIllSname;
		this.orderMessage = orderMessage;
		this.orderMessageTime = orderMessageTime;
		this.orderMessageWriteTime = orderMessageWriteTime;
		this.orderPayState = orderPayState;
		this.ordertimeDetailId = ordertimeDetailId;
	}

	public OrderTbl(int userId, int doctorsId, String userPhone, String orderIllSname, String orderMessage, String orderMessageTime, Timestamp orderMessageWriteTime, int orderPayState, float ordertimeDetailPrice, int ordertimeDetailId) {
		this.userId = userId;
		this.doctorsId = doctorsId;
		this.userPhone = userPhone;
		this.orderIllSname = orderIllSname;
		this.orderMessage = orderMessage;
		this.orderMessageTime = orderMessageTime;
		this.orderMessageWriteTime = orderMessageWriteTime;
		this.orderPayState = orderPayState;
		this.ordertimeDetailPrice = ordertimeDetailPrice;
		this.ordertimeDetailId = ordertimeDetailId;
	}

	public OrderTbl(int orderId, int userId, String orderIllSname, String userPhone, int doctorsId, String orderMessage, String orderMessageTime, Timestamp orderMessageWriteTime, int orderPayState, String orderIllPhotoOne, String orderIllPhotoTwo, int ordertimeDetailId) {
		this.orderId = orderId;
		this.userId = userId;
		this.orderIllSname = orderIllSname;
		this.userPhone = userPhone;
		this.doctorsId = doctorsId;
		this.orderMessage = orderMessage;
		this.orderMessageTime = orderMessageTime;
		this.orderMessageWriteTime = orderMessageWriteTime;
		this.orderPayState = orderPayState;
		this.orderIllPhotoOne = orderIllPhotoOne;
		this.orderIllPhotoTwo = orderIllPhotoTwo;
		this.ordertimeDetailId = ordertimeDetailId;
	}

	public float getOrdertimeDetailPrice() {
		return ordertimeDetailPrice;
	}

	public void setOrdertimeDetailPrice(float ordertimeDetailPrice) {
		this.ordertimeDetailPrice = ordertimeDetailPrice;
	}

	public int getOrdertimeDetailId() {
		return ordertimeDetailId;
	}

	public void setOrdertimeDetailId(int ordertimeDetailId) {
		this.ordertimeDetailId = ordertimeDetailId;
	}

	public OrderTbl(String userPhone, String orderIllSname, String orderMessage, int orderPayState,
					float ordertime_detail_price) {
		super();
		this.userPhone = userPhone;
		this.orderIllSname = orderIllSname;
		this.orderMessage = orderMessage;
		this.orderPayState = orderPayState;
		this.ordertimeDetailPrice = ordertime_detail_price;
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
	public float getOrderState() {
		return ordertimeDetailPrice;
	}
	public void setOrderState(float orderState) {
		this.ordertimeDetailPrice = orderState;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.orderId);
		dest.writeInt(this.userId);
		dest.writeInt(this.doctorsId);
		dest.writeString(this.userPhone);
		dest.writeString(this.orderIllSname);
		dest.writeString(this.orderMessage);
		dest.writeString(this.orderMessageTime);
		dest.writeSerializable(this.orderMessageWriteTime);
		dest.writeInt(this.orderPayState);
		dest.writeFloat(this.ordertimeDetailPrice);
		dest.writeString(this.orderIllPhotoOne);
		dest.writeString(this.orderIllPhotoTwo);
		dest.writeInt(this.ordertimeDetailId);
	}

	protected OrderTbl(Parcel in) {
		this.orderId = in.readInt();
		this.userId = in.readInt();
		this.doctorsId = in.readInt();
		this.userPhone = in.readString();
		this.orderIllSname = in.readString();
		this.orderMessage = in.readString();
		this.orderMessageTime = in.readString();
		this.orderMessageWriteTime = (Timestamp) in.readSerializable();
		this.orderPayState = in.readInt();
		this.ordertimeDetailPrice = in.readFloat();
		this.orderIllPhotoOne = in.readString();
		this.orderIllPhotoTwo = in.readString();
		this.ordertimeDetailId = in.readInt();
	}

	public static final Parcelable.Creator<OrderTbl> CREATOR = new Parcelable.Creator<OrderTbl>() {
		@Override
		public OrderTbl createFromParcel(Parcel source) {
			return new OrderTbl(source);
		}

		@Override
		public OrderTbl[] newArray(int size) {
			return new OrderTbl[size];
		}
	};
}
