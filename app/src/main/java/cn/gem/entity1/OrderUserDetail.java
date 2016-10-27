package cn.gem.entity1;


import cn.gem.entity.OrderTbl;

public class OrderUserDetail {

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
	
	
	
}
