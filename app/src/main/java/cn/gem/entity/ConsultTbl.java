package cn.gem.entity;

import java.sql.Timestamp;

/**
 * 咨询表
 */
public class ConsultTbl {

	private int consultId;
	private int userId;
	private int doctorsId;
	private Timestamp consultUserTime;
	private double consultPrice;
	private UserTbl userTbl;
	private Timestamp consultDoctorsTime;
	private String consultDetailContent;
	private String consultDetailPhoto;

	private String doctorsSname;
	private int doctorsPosition;
	private String subjectSname;

	public ConsultTbl(int consultId, int userId, int doctorsId, Timestamp consultUserTime,
					  double consultPrice, UserTbl userTbl, Timestamp consultDoctorsTime,
					  String consultDetailContent, String consultDetailPhoto) {
		this.consultId = consultId;
		this.userId = userId;
		this.doctorsId = doctorsId;
		this.consultUserTime = consultUserTime;
		this.consultPrice = consultPrice;
		this.userTbl = userTbl;
		this.consultDoctorsTime = consultDoctorsTime;
		this.consultDetailContent = consultDetailContent;
		this.consultDetailPhoto = consultDetailPhoto;
	}

	public UserTbl getUserTbl() {
		return userTbl;
	}

	public void setUserTbl(UserTbl userTbl) {
		this.userTbl = userTbl;
	}

	public int getDoctorsId() {
		return doctorsId;
	}
	public void setDoctorsId(int doctorsId) {
		this.doctorsId = doctorsId;
	}
	public Timestamp getConsultUserTime() {
		return consultUserTime;
	}
	public void setConsultUserTime(Timestamp consultUserTime) {
		this.consultUserTime = consultUserTime;
	}
	public Timestamp getConsultDoctorsTime() {
		return consultDoctorsTime;
	}
	public void setConsultDoctorsTime(Timestamp consultDoctorsTime) {
		this.consultDoctorsTime = consultDoctorsTime;
	}

	
	
	
	public int getConsultId() {
		return consultId;
	}
	public void setConsultId(int consultId) {
		this.consultId = consultId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getConsultPrice() {
		return consultPrice;
	}
	public void setConsultPrice(double consultPrice) {
		this.consultPrice = consultPrice;
	}
	public String getConsultDetailContent() {
		return consultDetailContent;
	}
	public void setConsultDetailContent(String consultDetailContent) {
		this.consultDetailContent = consultDetailContent;
	}
	public String getConsultDetailPhoto() {
		return consultDetailPhoto;
	}
	public void setConsultDetailPhoto(String consultDetailPhoto) {
		this.consultDetailPhoto = consultDetailPhoto;
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

	public String getSubjectSname() {
		return subjectSname;
	}

	public void setSubjectSname(String subjectSname) {
		this.subjectSname = subjectSname;
	}
}
