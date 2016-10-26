package cn.gem.entity;
/**
 * 城市表格
 * @author sony
 *
 */
public class CityTbl {

	private int cityId;
	private String citySname;
	private int provinceId;
	
	
	public CityTbl(int cityId, String citySname, int provinceId) {
		super();
		this.cityId = cityId;
		this.citySname = citySname;
		this.provinceId = provinceId;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getCitySname() {
		return citySname;
	}
	public void setCitySname(String citySname) {
		this.citySname = citySname;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	@Override
	public String toString() {
		return "CityTbl{" +
				"cityId=" + cityId +
				", citySname='" + citySname + '\'' +
				", provinceId=" + provinceId +
				'}';
	}
}
