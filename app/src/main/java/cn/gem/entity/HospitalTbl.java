package cn.gem.entity;

/**
 * Created by sony on 2016/9/24.
 */
public class HospitalTbl {

    private int hospitalId;
    private int cityId;
    private String hospitalSname;
    private String hospitalAddress;
    private String hospitalBrief;
    private String hospitalPhoto;
    private String hospitalPlanId;
    private String hospitalPlanPhoto;
    private String hospitalOrderRule;

    public HospitalTbl() {

    }

    public HospitalTbl(int hospitalId, String hospitalSname, String hospitalPhoto) {
        this.hospitalId = hospitalId;
        this.hospitalSname = hospitalSname;
        this.hospitalPhoto = hospitalPhoto;
    }

    public HospitalTbl(int hospitalId, int cityId, String hospitalSname, String hospitalAddress, String hospitalBrief,
                       String hospitalPhoto, String hospitalPlanId, String hospitalPlanPhoto) {
        super();
        this.hospitalId = hospitalId;
        this.cityId = cityId;
        this.hospitalSname = hospitalSname;
        this.hospitalAddress = hospitalAddress;
        this.hospitalBrief = hospitalBrief;
        this.hospitalPhoto = hospitalPhoto;
        this.hospitalPlanId = hospitalPlanId;
        this.hospitalPlanPhoto = hospitalPlanPhoto;
    }

    //应用于一个医院的详情的时候
    public HospitalTbl(int hospitalId, int cityId, String hospitalSname, String hospitalAddress, String hospitalBrief,
                       String hospitalPhoto, String hospitalOrderRule) {
        super();
        this.hospitalId = hospitalId;
        this.cityId = cityId;
        this.hospitalSname = hospitalSname;
        this.hospitalAddress = hospitalAddress;
        this.hospitalBrief = hospitalBrief;
        this.hospitalPhoto = hospitalPhoto;
        this.hospitalOrderRule = hospitalOrderRule;
    }

    public int getHospitalId() {
        return hospitalId;
    }
    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }
    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    public String getHospitalSname() {
        return hospitalSname;
    }
    public void setHospitalSname(String hospitalSname) {
        this.hospitalSname = hospitalSname;
    }
    public String getHospitalAddress() {
        return hospitalAddress;
    }
    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }
    public String getHospitalBrief() {
        return hospitalBrief;
    }
    public void setHospitalBrief(String hospitalBrief) {
        this.hospitalBrief = hospitalBrief;
    }
    public String getHospitalPhoto() {
        return hospitalPhoto;
    }
    public void setHospitalPhoto(String hospitalPhoto) {
        this.hospitalPhoto = hospitalPhoto;
    }
    public String getHospitalPlanId() {
        return hospitalPlanId;
    }
    public void setHospitalPlanId(String hospitalPlanId) {
        this.hospitalPlanId = hospitalPlanId;
    }
    public String getHospitalPlanPhoto() {
        return hospitalPlanPhoto;
    }
    public void setHospitalPlanPhoto(String hospitalPlanPhoto) {
        this.hospitalPlanPhoto = hospitalPlanPhoto;
    }
    public String getHospitalOrderRule() {
        return hospitalOrderRule;
    }

    public void setHospitalOrderRule(String hospitalOrderRule) {
        this.hospitalOrderRule = hospitalOrderRule;
    }

    @Override
    public String toString() {
        return "HospitalTbl [hospitalId=" + hospitalId + ", cityId=" + cityId + ", hospitalSname=" + hospitalSname
                + ", hospitalAddress=" + hospitalAddress + ", hospitalBrief=" + hospitalBrief + ", hospitalPhoto="
                + hospitalPhoto + ", hospitalPlanId=" + hospitalPlanId + ", hospitalPlanPhoto=" + hospitalPlanPhoto
                + "]";
    }


}
