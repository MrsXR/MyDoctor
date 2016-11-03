package cn.gem.entity;

import java.util.List;

public class AllMsg {
private List<ArticleTbl> artList;
private List<DoctorsTbl> docList;
private List<HospitalTbl> hspList;
public AllMsg(List<ArticleTbl> artList, List<DoctorsTbl> docList, List<HospitalTbl> hspList) {
	super();
	this.artList = artList;
	this.docList = docList;
	this.hspList = hspList;
}
public List<ArticleTbl> getArtList() {
	return artList;
}
public void setArtList(List<ArticleTbl> artList) {
	this.artList = artList;
}
public List<DoctorsTbl> getDocList() {
	return docList;
}
public void setDocList(List<DoctorsTbl> docList) {
	this.docList = docList;
}
public List<HospitalTbl> getHspList() {
	return hspList;
}
public void setHspList(List<HospitalTbl> hspList) {
	this.hspList = hspList;
}




	

}
