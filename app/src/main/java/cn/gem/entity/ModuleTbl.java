package cn.gem.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * bankuaibiao
 * @author sony
 *
 */
public class ModuleTbl implements Parcelable {

	private int moduleId;
	private String moduleSname;
	private int moduleNum;
	
	
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleSname() {
		return moduleSname;
	}
	public void setModuleSname(String moduleSname) {
		this.moduleSname = moduleSname;
	}
	public int getModuleNum() {
		return moduleNum;
	}
	public void setModuleNum(int moduleNum) {
		this.moduleNum = moduleNum;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.moduleId);
		dest.writeString(this.moduleSname);
		dest.writeInt(this.moduleNum);
	}

	public ModuleTbl() {
	}

	protected ModuleTbl(Parcel in) {
		this.moduleId = in.readInt();
		this.moduleSname = in.readString();
		this.moduleNum = in.readInt();
	}

	public static final Parcelable.Creator<ModuleTbl> CREATOR = new Parcelable.Creator<ModuleTbl>() {
		@Override
		public ModuleTbl createFromParcel(Parcel source) {
			return new ModuleTbl(source);
		}

		@Override
		public ModuleTbl[] newArray(int size) {
			return new ModuleTbl[size];
		}
	};
}
