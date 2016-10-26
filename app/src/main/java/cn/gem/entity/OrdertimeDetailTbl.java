package cn.gem.entity;

import java.sql.Timestamp;

/**
 * 医生提供预约时间
 * @author sony
 * ordertime_detail_tbl
 *
 */
public class OrdertimeDetailTbl {
	private int ordertimeDetailId;
	private int doctorsId;
	private Timestamp ordertimeDetailTimeTo;
	private Timestamp ordertimeDetailTimeFrom;
	private int ordertimeDetailTotal;
	private int ordertimeDetailNumber;
	

	public OrdertimeDetailTbl(int ordertimeDetailId, int doctorsId, Timestamp ordertimeDetailTimeTo,
			Timestamp ordertimeDetailTimeFrom, int ordertimeDetailTotal, int ordertimeDetailNumber) {
		super();
		this.ordertimeDetailId = ordertimeDetailId;
		this.doctorsId = doctorsId;
		this.ordertimeDetailTimeTo = ordertimeDetailTimeTo;
		this.ordertimeDetailTimeFrom = ordertimeDetailTimeFrom;
		this.ordertimeDetailTotal = ordertimeDetailTotal;
		this.ordertimeDetailNumber = ordertimeDetailNumber;
	}
	public Timestamp getOrdertimeDetailTimeTo() {
		return ordertimeDetailTimeTo;
	}
	public void setOrdertimeDetailTimeTo(Timestamp ordertimeDetailTimeTo) {
		this.ordertimeDetailTimeTo = ordertimeDetailTimeTo;
	}
	public Timestamp getOrdertimeDetailTimeFrom() {
		return ordertimeDetailTimeFrom;
	}
	public void setOrdertimeDetailTimeFrom(Timestamp ordertimeDetailTimeFrom) {
		this.ordertimeDetailTimeFrom = ordertimeDetailTimeFrom;
	}
	public int getOrdertimeDetailNumber() {
		return ordertimeDetailNumber;
	}
	public void setOrdertimeDetailNumber(int ordertimeDetailNumber) {
		this.ordertimeDetailNumber = ordertimeDetailNumber;
	}
	public int getOrdertimeDetailId() {
		return ordertimeDetailId;
	}
	public void setOrdertimeDetailId(int ordertimeDetailId) {
		this.ordertimeDetailId = ordertimeDetailId;
	}
	public int getDoctorsId() {
		return doctorsId;
	}
	public void setDoctorsId(int doctorsId) {
		this.doctorsId = doctorsId;
	}
	public int getOrdertimeDetailTotal() {
		return ordertimeDetailTotal;
	}
	public void setOrdertimeDetailTotal(int ordertimeDetailTotal) {
		this.ordertimeDetailTotal = ordertimeDetailTotal;
	}
	
	

}
