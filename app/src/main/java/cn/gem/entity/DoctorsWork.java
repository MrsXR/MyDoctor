package cn.gem.entity;

import java.util.List;

/**
 * Created by sony on 2016/10/16.
 */
public class DoctorsWork {

    private List<ConsultTbl> consultTbl;
    private List<CommentOrderDetailTbl> commentOrderDetailTbl;
    private DoctorInHospital doctorInHospital;

    public DoctorsWork() {
    }

    private int isData;

    public int getIsData() {
        return isData;
    }

    public void setIsData(int isData) {
        this.isData = isData;
    }

    public DoctorsWork(List<ConsultTbl> consultTbl, List<CommentOrderDetailTbl> commentOrderDetailTbl,
                       DoctorInHospital doctorInHospital,int isData) {
        super();
        this.consultTbl = consultTbl;
        this.commentOrderDetailTbl = commentOrderDetailTbl;
        this.doctorInHospital = doctorInHospital;
        this.isData=isData;
    }


    public DoctorsWork(List<ConsultTbl> consultTbl, List<CommentOrderDetailTbl> commentOrderDetailTbl) {
        super();
        this.consultTbl = consultTbl;
        this.commentOrderDetailTbl = commentOrderDetailTbl;
    }
    public List<ConsultTbl> getConsultTbl() {
        return consultTbl;
    }
    public void setConsultTbl(List<ConsultTbl> consultTbl) {
        this.consultTbl = consultTbl;
    }
    public List<CommentOrderDetailTbl> getCommentOrderDetailTbl() {
        return commentOrderDetailTbl;
    }
    public void setCommentOrderDetailTbl(List<CommentOrderDetailTbl> commentOrderDetailTbl) {
        this.commentOrderDetailTbl = commentOrderDetailTbl;
    }
    public DoctorInHospital getDoctorInHospital() {
        return doctorInHospital;
    }
    public void setDoctorInHospital(DoctorInHospital doctorInHospital) {
        this.doctorInHospital = doctorInHospital;
    }

}
