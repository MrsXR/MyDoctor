package cn.gem.entity1;


import android.os.Parcel;
import android.os.Parcelable;

import cn.gem.entity.OrderTbl;

public class OrderUserDetail implements Parcelable {

	private OrderTbl orderTbl;
	private String userSname;
	private String doctorsSname;
	private int doctorsPosition;
	private String hospitalSname;
	private String departmentsSname;
	private String hospitalAddress;
	private String userCard;
	
	
	public OrderUserDetail(OrderTbl orderTbl, String userSname, String doctorsSname, int doctorsPosition,
			String hospitalSname, String departmentsSname, String hospitalAddress, String userCard) {
		super();
		this.orderTbl = orderTbl;
		this.userSname = userSname;
		this.doctorsSname = doctorsSname;
		this.doctorsPosition = doctorsPosition;
		this.hospitalSname = hospitalSname;
		this.departmentsSname = departmentsSname;
		this.hospitalAddress = hospitalAddress;
		this.userCard = userCard;
	}
	public OrderTbl getOrderTbl() {
		return orderTbl;
	}
	public void setOrderTbl(OrderTbl orderTbl) {
		this.orderTbl = orderTbl;
	}
	public String getUserSname() {
		return userSname;
	}
	public void setUserSname(String userSname) {
		this.userSname = userSname;
	}
	public String getDoctorsSname() {
		return doctorsSname;
	}
	public void setDoctorsSname(String doctorsSname) {
		this.doctorsSname = doctorsSname;
	}
	public int getDoctorsPosition() {
		return doctorsPosition;
	}
	public void setDoctorsPosition(int doctorsPosition) {
		this.doctorsPosition = doctorsPosition;
	}
	public String getHospitalSname() {
		return hospitalSname;
	}
	public void setHospitalSname(String hospitalSname) {
		this.hospitalSname = hospitalSname;
	}
	public String getDepartmentsSname() {
		return departmentsSname;
	}
	public void setDepartmentsSname(String departmentsSname) {
		this.departmentsSname = departmentsSname;
	}
	public String getHospitalAddress() {
		return hospitalAddress;
	}
	public void setHospitalAddress(String hospitalAddress) {
		this.hospitalAddress = hospitalAddress;
	}
	public String getUserCard() {
		return userCard;
	}
	public void setUserCard(String userCard) {
		this.userCard = userCard;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(this.orderTbl, flags);
		dest.writeString(this.userSname);
		dest.writeString(this.doctorsSname);
		dest.writeInt(this.doctorsPosition);
		dest.writeString(this.hospitalSname);
		dest.writeString(this.departmentsSname);
		dest.writeString(this.hospitalAddress);
		dest.writeString(this.userCard);
	}

	protected OrderUserDetail(Parcel in) {
		this.orderTbl = in.readParcelable(OrderTbl.class.getClassLoader());
		this.userSname = in.readString();
		this.doctorsSname = in.readString();
		this.doctorsPosition = in.readInt();
		this.hospitalSname = in.readString();
		this.departmentsSname = in.readString();
		this.hospitalAddress = in.readString();
		this.userCard = in.readString();
	}

	public static final Parcelable.Creator<OrderUserDetail> CREATOR = new Parcelable.Creator<OrderUserDetail>() {
		@Override
		public OrderUserDetail createFromParcel(Parcel source) {
			return new OrderUserDetail(source);
		}

		@Override
		public OrderUserDetail[] newArray(int size) {
			return new OrderUserDetail[size];
		}
	};
}
