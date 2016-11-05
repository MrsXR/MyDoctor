package cn.gem.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class UserTbl implements Parcelable {
	private int userId;
	private String userSname;
	private String userPhone;
	private String userLoginAccount;
	private String userPassword;
	private int userAge;
	private int userSex;
	private float userHeight;
	private float userWeight;
	private String userCase;
	private String userPhoto;

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public UserTbl(String userSname, String userPhone, String userCard) {
		this.userSname = userSname;
		this.userPhone = userPhone;
		this.userCard = userCard;
	}

	public UserTbl(int userId, String userSname, String userPhone, String userCard) {
		this.userId = userId;
		this.userSname = userSname;
		this.userPhone = userPhone;
		this.userCard = userCard;
	}

	private String userCard;//*身份证
	private String userElectronicBilling;
	private String userRegisteredOrder;
	private int userIndividualAccount;
	private String userWalletPassword;
	private double userBalance;
	private int userBankCard;
	private int userDetailPhoto;
	private int userMedicalInsurance;

	public UserTbl(int userId, String userSname) {
		this.userId = userId;
		this.userSname = userSname;
	}

	public UserTbl(String userSname, int userAge, int userSex) {
		super();
		this.userSname = userSname;
		this.userAge = userAge;
		this.userSex = userSex;
	}

	public UserTbl(String userSname, String userPhoto) {
		this.userSname = userSname;
		this.userPhone = userPhoto;
	}


	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserSname() {
		return userSname;
	}
	public void setUserSname(String userSname) {
		this.userSname = userSname;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhoto) {
		this.userPhone = userPhoto;
	}
	public String getUserLoginAccount() {
		return userLoginAccount;
	}
	public void setUserLoginAccount(String userLoginAccount) {
		this.userLoginAccount = userLoginAccount;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public int getUserAge() {
		return userAge;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	public int getUserSex() {
		return userSex;
	}
	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}
	public float getUserHeight() {
		return userHeight;
	}
	public void setUserHeight(float userHeight) {
		this.userHeight = userHeight;
	}
	public float getUserWeight() {
		return userWeight;
	}
	public void setUserWeight(float userWeight) {
		this.userWeight = userWeight;
	}
	public String getUserCase() {
		return userCase;
	}
	public void setUserCase(String userCase) {
		this.userCase = userCase;
	}
	public String getUserCard() {
		return userCard;
	}
	public void setUserCard(String userCard) {
		this.userCard = userCard;
	}
	public String getUserElectronicBilling() {
		return userElectronicBilling;
	}
	public void setUserElectronicBilling(String userElectronicBilling) {
		this.userElectronicBilling = userElectronicBilling;
	}
	public String getUserRegisteredOrder() {
		return userRegisteredOrder;
	}
	public void setUserRegisteredOrder(String userRegisteredOrder) {
		this.userRegisteredOrder = userRegisteredOrder;
	}
	public int getUserIndividualAccount() {
		return userIndividualAccount;
	}
	public void setUserIndividualAccount(int userIndividualAccount) {
		this.userIndividualAccount = userIndividualAccount;
	}
	public String getUserWalletPassword() {
		return userWalletPassword;
	}
	public void setUserWalletPassword(String userWalletPassword) {
		this.userWalletPassword = userWalletPassword;
	}
	public double getUserBalance() {
		return userBalance;
	}
	public void setUserBalance(double userBalance) {
		this.userBalance = userBalance;
	}
	public int getUserBankCard() {
		return userBankCard;
	}
	public void setUserBankCard(int userBankCard) {
		this.userBankCard = userBankCard;
	}
	public int getUserDetailPhoto() {
		return userDetailPhoto;
	}
	public void setUserDetailPhoto(int userDetailPhoto) {
		this.userDetailPhoto = userDetailPhoto;
	}
	public int getUserMedicalInsurance() {
		return userMedicalInsurance;
	}
	public void setUserMedicalInsurance(int userMedicalInsurance) {
		this.userMedicalInsurance = userMedicalInsurance;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.userId);
		dest.writeString(this.userSname);
		dest.writeString(this.userPhone);
		dest.writeString(this.userLoginAccount);
		dest.writeString(this.userPassword);
		dest.writeInt(this.userAge);
		dest.writeInt(this.userSex);
		dest.writeFloat(this.userHeight);
		dest.writeFloat(this.userWeight);
		dest.writeString(this.userCase);
		dest.writeString(this.userCard);
		dest.writeString(this.userElectronicBilling);
		dest.writeString(this.userRegisteredOrder);
		dest.writeInt(this.userIndividualAccount);
		dest.writeString(this.userWalletPassword);
		dest.writeDouble(this.userBalance);
		dest.writeInt(this.userBankCard);
		dest.writeInt(this.userDetailPhoto);
		dest.writeInt(this.userMedicalInsurance);
	}

	protected UserTbl(Parcel in) {
		this.userId = in.readInt();
		this.userSname = in.readString();
		this.userPhone = in.readString();
		this.userLoginAccount = in.readString();
		this.userPassword = in.readString();
		this.userAge = in.readInt();
		this.userSex = in.readInt();
		this.userHeight = in.readFloat();
		this.userWeight = in.readFloat();
		this.userCase = in.readString();
		this.userCard = in.readString();
		this.userElectronicBilling = in.readString();
		this.userRegisteredOrder = in.readString();
		this.userIndividualAccount = in.readInt();
		this.userWalletPassword = in.readString();
		this.userBalance = in.readDouble();
		this.userBankCard = in.readInt();
		this.userDetailPhoto = in.readInt();
		this.userMedicalInsurance = in.readInt();
	}

	public static final Parcelable.Creator<UserTbl> CREATOR = new Parcelable.Creator<UserTbl>() {
		@Override
		public UserTbl createFromParcel(Parcel source) {
			return new UserTbl(source);
		}

		@Override
		public UserTbl[] newArray(int size) {
			return new UserTbl[size];
		}
	};
}
