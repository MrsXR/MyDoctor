package cn.gem.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 医生表
 * @author sony
 *
 */
public class DoctorsTbl implements Parcelable {

	private int doctorsId;
	private int relevantId;//关联ID
	private int subjectId;
	private String doctorsSname;
	private int doctorsPosition;
	private int  doctorsSex;

	public int getDoctorsAge() {
		return doctorsAge;
	}

	public void setDoctorsAge(int doctorsAge) {
		this.doctorsAge = doctorsAge;
	}

	private int  doctorsAge;
	private String doctorsBrief;
	private int doctorsRecommend;//推荐指数
	private int hospitalId;
	private String doctorsPhoto;
	private float doctorsConsultPrice;


	private String hospitalSname;
	private String departmentsSname;

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

	public DoctorsTbl(int doctorsId, int relevantId, int subjectId, String doctorsSname, int doctorsPosition,
					  int doctorsSex, String doctorsBrief, int doctorsRecommend, int hospitalId, String doctorsPhoto,
					  float doctorsConsultPrice) {
		super();
		this.doctorsId = doctorsId;
		this.relevantId = relevantId;
		this.subjectId = subjectId;
		this.doctorsSname = doctorsSname;
		this.doctorsPosition = doctorsPosition;
		this.doctorsSex = doctorsSex;
		this.doctorsBrief = doctorsBrief;
		this.doctorsRecommend = doctorsRecommend;
		this.hospitalId = hospitalId;
		this.doctorsPhoto = doctorsPhoto;
		this.doctorsConsultPrice = doctorsConsultPrice;
	}



	public DoctorsTbl(int subjectId, int hospitalId) {
		super();
		this.subjectId = subjectId;
		this.hospitalId = hospitalId;
	}

	public DoctorsTbl( int relevantId, int subjectId, String doctorsSname, int doctorsPosition,
					   int doctorsAge, String doctorsBrief, int doctorsRecommend) {
		super();

		this.relevantId = relevantId;
		this.subjectId = subjectId;
		this.doctorsSname = doctorsSname;
		this.doctorsPosition = doctorsPosition;
		this.doctorsAge = doctorsAge;
		this.doctorsBrief = doctorsBrief;
		this.doctorsRecommend = doctorsRecommend;
	}


	public int getDoctorsId() {
		return doctorsId;
	}
	public void setDoctorsId(int doctorsId) {
		this.doctorsId = doctorsId;
	}
	public int getRelevantId() {
		return relevantId;
	}
	public void setRelevantId(int relevantId) {
		this.relevantId = relevantId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
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
	public int getDoctorsSex() {
		return doctorsSex;
	}
	public void setDoctorsSex(int doctorsSex) {
		this.doctorsSex = doctorsSex;
	}
	public String getDoctorsBrief() {
		return doctorsBrief;
	}
	public void setDoctorsBrief(String doctorsBrief) {
		this.doctorsBrief = doctorsBrief;
	}
	public int getDoctorsRecommend() {
		return doctorsRecommend;
	}
	public void setDoctorsRecommend(int doctorsRecommend) {
		this.doctorsRecommend = doctorsRecommend;
	}
	public int getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getDoctorsPhoto() {
		return doctorsPhoto;
	}
	public void setDoctorsPhoto(String doctorsPhoto) {
		this.doctorsPhoto = doctorsPhoto;
	}

	public float getDoctorsConsultPrice() {
		return doctorsConsultPrice;
	}
	public void setDoctorsConsultPrice(float doctorsConsultPrice) {
		this.doctorsConsultPrice = doctorsConsultPrice;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.doctorsId);
		dest.writeInt(this.relevantId);
		dest.writeInt(this.subjectId);
		dest.writeString(this.doctorsSname);
		dest.writeInt(this.doctorsPosition);
		dest.writeInt(this.doctorsSex);
		dest.writeString(this.doctorsBrief);
		dest.writeInt(this.doctorsRecommend);
		dest.writeInt(this.hospitalId);
		dest.writeString(this.doctorsPhoto);
		dest.writeFloat(this.doctorsConsultPrice);
	}

	protected DoctorsTbl(Parcel in) {
		this.doctorsId = in.readInt();
		this.relevantId = in.readInt();
		this.subjectId = in.readInt();
		this.doctorsSname = in.readString();
		this.doctorsPosition = in.readInt();
		this.doctorsSex = in.readInt();
		this.doctorsBrief = in.readString();
		this.doctorsRecommend = in.readInt();
		this.hospitalId = in.readInt();
		this.doctorsPhoto = in.readString();
		this.doctorsConsultPrice = in.readFloat();
	}

	public static final Parcelable.Creator<DoctorsTbl> CREATOR = new Parcelable.Creator<DoctorsTbl>() {
		@Override
		public DoctorsTbl createFromParcel(Parcel source) {
			return new DoctorsTbl(source);
		}

		@Override
		public DoctorsTbl[] newArray(int size) {
			return new DoctorsTbl[size];
		}
	};

}
