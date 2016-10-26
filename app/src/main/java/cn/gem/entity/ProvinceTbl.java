package cn.gem.entity;
/**
 * 省份
 * @author sony
 *
 */
public class ProvinceTbl {
	private int provinceId;
	private String provinceSname;
	
	
	
	
	public ProvinceTbl(int provinceId, String provinceSname) {
		super();
		this.provinceId = provinceId;
		this.provinceSname = provinceSname;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceSname() {
		return provinceSname;
	}
	public void setProvinceSname(String provinceSname) {
		this.provinceSname = provinceSname;
	}

	@Override
	public String toString() {
		return "ProvinceTbl{" +
				"provinceId=" + provinceId +
				", provinceSname='" + provinceSname + '\'' +
				'}';
	}
}
