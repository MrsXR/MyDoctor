package cn.gem.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class DoctorInHospital implements Parcelable {

	private String doctorsSname;
	private String doctorsPhoto;
	private String hospitalSname;
	private String subjectSname;
	private String hospitalAddress;

	private float ordertimeDetailPrice;
	public float getOrdertimeDetailPrice() {
		return ordertimeDetailPrice;
	}
	public void setOrdertimeDetailPrice(float ordertimeDetailPrice) {
		this.ordertimeDetailPrice = ordertimeDetailPrice;
	}

	public DoctorInHospital(String doctorsSname, String doctorsPhoto,String subjectSname, String hospitalSname,
							String hospitalAddress) {
		super();
		this.doctorsSname = doctorsSname;
		this.doctorsPhoto = doctorsPhoto;
		this.subjectSname = subjectSname;
		this.hospitalSname = hospitalSname;
		this.hospitalAddress = hospitalAddress;
	}

	public String getDoctorsSname() {
		return doctorsSname;
	}
	public void setDoctorsSname(String doctorsSname) {
		this.doctorsSname = doctorsSname;
	}
	public String getDoctorsPhoto() {
		return doctorsPhoto;
	}
	public void setDoctorsPhoto(String doctorsPhoto) {
		this.doctorsPhoto = doctorsPhoto;
	}
	public String getHospitalSname() {
		return hospitalSname;
	}
	public void setHospitalSname(String hospitalSname) {
		this.hospitalSname = hospitalSname;
	}
	public String getSubjectSname() {
		return subjectSname;
	}
	public void setSubjectSname(String subjectSname) {
		this.subjectSname = subjectSname;
	}
	public String getHospitalAddress() {
		return hospitalAddress;
	}
	public void setHospitalAddress(String hospitalAddress) {
		this.hospitalAddress = hospitalAddress;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.doctorsSname);
		dest.writeString(this.doctorsPhoto);
		dest.writeString(this.hospitalSname);
		dest.writeString(this.subjectSname);
		dest.writeString(this.hospitalAddress);
	}

	protected DoctorInHospital(Parcel in) {
		this.doctorsSname = in.readString();
		this.doctorsPhoto = in.readString();
		this.hospitalSname = in.readString();
		this.subjectSname = in.readString();
		this.hospitalAddress = in.readString();
	}

	public static final Parcelable.Creator<DoctorInHospital> CREATOR = new Parcelable.Creator<DoctorInHospital>() {
		@Override
		public DoctorInHospital createFromParcel(Parcel source) {
			return new DoctorInHospital(source);
		}

		@Override
		public DoctorInHospital[] newArray(int size) {
			return new DoctorInHospital[size];
		}
	};
}
