package cn.gem.util;

import java.util.ArrayList;
import java.util.List;

import cn.gem.entity.DepartmentsTbl;
import cn.gem.entity.SubjectTbl;

/**
 * Created by sony on 2016/11/2.
 */
public class ListSubject {
    List<DepartmentsTbl> departmentsTbls=new ArrayList<DepartmentsTbl>();

    public   List<DepartmentsTbl>  getDepartmentsTbls(){

        departmentsTbls.add(new DepartmentsTbl(1,"内科",getSubjectTbl(1)));
        departmentsTbls.add(new DepartmentsTbl(2,"外科",getSubjectTbl(2)));
        departmentsTbls.add(new DepartmentsTbl(3,"骨科",getSubjectTbl(3)));
        departmentsTbls.add(new DepartmentsTbl(4,"妇产科",getSubjectTbl(4)));
        departmentsTbls.add(new DepartmentsTbl(5,"妇产科",getSubjectTbl(5)));
        departmentsTbls.add(new DepartmentsTbl(6,"眼科",getSubjectTbl(6)));
        departmentsTbls.add(new DepartmentsTbl(7,"耳鼻喉科颈科",getSubjectTbl(7)));
        departmentsTbls.add(new DepartmentsTbl(8,"空腔科",getSubjectTbl(8)));
        departmentsTbls.add(new DepartmentsTbl(9,"皮肤病科",getSubjectTbl(9)));
        departmentsTbls.add(new DepartmentsTbl(10,"肿瘤科",getSubjectTbl(10)));
        departmentsTbls.add(new DepartmentsTbl(12,"麻醉科",getSubjectTbl(12)));
        departmentsTbls.add(new DepartmentsTbl(13,"中医科",getSubjectTbl(13)));
        departmentsTbls.add(new DepartmentsTbl(14,"精神心理科",getSubjectTbl(14)));
        departmentsTbls.add(new DepartmentsTbl(15,"生殖中心",getSubjectTbl(15)));
        departmentsTbls.add(new DepartmentsTbl(16,"传染病科",getSubjectTbl(16)));
        departmentsTbls.add(new DepartmentsTbl(17,"其他",getSubjectTbl(17)));
        departmentsTbls.add(new DepartmentsTbl(18,"整形科",getSubjectTbl(18)));
        departmentsTbls.add(new DepartmentsTbl(19,"康复科",getSubjectTbl(19)));
        departmentsTbls.add(new DepartmentsTbl(20,"急诊科",getSubjectTbl(20)));
        departmentsTbls.add(new DepartmentsTbl(21,"男科",getSubjectTbl(21)));
        departmentsTbls.add(new DepartmentsTbl(22,"疼痛科",getSubjectTbl(22)));


        return departmentsTbls;
    }

    private List<SubjectTbl> getSubjectTbl(int departmentsId){
        List<SubjectTbl> getSubjectTbl;
        switch (departmentsId){
            case 1:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(1,"消化内科"));
                getSubjectTbl.add(new SubjectTbl(2,"呼吸内科"));
                getSubjectTbl.add(new SubjectTbl(3,"心血管内科"));
                getSubjectTbl.add(new SubjectTbl(4,"肾脏内科"));
                getSubjectTbl.add(new SubjectTbl(5,"神经内科"));
                getSubjectTbl.add(new SubjectTbl(6,"血液科"));
                getSubjectTbl.add(new SubjectTbl(7,"风湿免疫科"));
                getSubjectTbl.add(new SubjectTbl(8,"内分泌内科"));
                getSubjectTbl.add(new SubjectTbl(31,"老年医学科"));
                getSubjectTbl.add(new SubjectTbl(32,"感染病科"));
                getSubjectTbl.add(new SubjectTbl(33,"血液内科"));
                return getSubjectTbl;
            case 2:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(9,"普通外科"));
                getSubjectTbl.add(new SubjectTbl(10,"泌尿外科"));
                getSubjectTbl.add(new SubjectTbl(11,"神经外科"));
                getSubjectTbl.add(new SubjectTbl(12,"烧伤整形外科"));
                return getSubjectTbl;
            case 3:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(13,"骨科"));
                return getSubjectTbl;
            case 4:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(14,"妇科"));
                getSubjectTbl.add(new SubjectTbl(15,"妇产科"));
                getSubjectTbl.add(new SubjectTbl(34,"生殖中心"));
                return getSubjectTbl;

            case 5:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(16,"儿科"));
                return getSubjectTbl;
            case 6:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(17,"眼科"));
                return getSubjectTbl;
            case 7:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(18,"耳鼻喉"));
                return getSubjectTbl;
            case 8:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(19,"口腔科"));
                return getSubjectTbl;
            case 9:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(20,"皮肤科"));
                return getSubjectTbl;
            case 10:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(21,"放疗科"));
                getSubjectTbl.add(new SubjectTbl(22,"肿瘤科"));
                return getSubjectTbl;
            case 12:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(23,"麻醉科"));
                return getSubjectTbl;
            case 13:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(24,"中医科"));
                return getSubjectTbl;
            case 14:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(25,"心理咨询门诊"));
                return getSubjectTbl;
            case 15:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(26,"不孕不育普通门诊"));
                return getSubjectTbl;
            case 16:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(27,"传染科"));
                getSubjectTbl.add(new SubjectTbl(28,"结核科"));
                return getSubjectTbl;
            case 19:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(35,"康复科"));
                return getSubjectTbl;
            case 20:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(29,"急诊科"));
                return getSubjectTbl;

            case 21:
                getSubjectTbl=new ArrayList<>();
                getSubjectTbl.add(new SubjectTbl(30,"男科"));
                return getSubjectTbl;

        }

        return null;
    }

}
