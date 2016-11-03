package cn.gem.entity;

import java.util.List;

/**
 * 科室表
 * @author sony
 *
 */
public class DepartmentsTbl {

	private int departmentsId;
	private String departmentsSname;
	private String departmentsBrief;
	private int relevantId;
	private List<SubjectTbl> subjectTbl;//对应的科室信息
	
	

	public DepartmentsTbl(int departmentsId, String departmentsSname,
			List<SubjectTbl> subjectTbl) {
		super();
		this.departmentsId = departmentsId;
		this.departmentsSname = departmentsSname;
		this.subjectTbl = subjectTbl;
	}
	
	
	
	
	public DepartmentsTbl(int departmentsId, String departmentsSname, int relevantId, List<SubjectTbl> subjectTbl) {
		super();
		this.departmentsId = departmentsId;
		this.departmentsSname = departmentsSname;
		this.relevantId = relevantId;
		this.subjectTbl = subjectTbl;
	}




	public int getDepartmentsId() {
		return departmentsId;
	}
	public void setDepartmentsId(int departmentsId) {
		this.departmentsId = departmentsId;
	}
	public String getDepartmentsSname() {
		return departmentsSname;
	}
	public void setDepartmentsSname(String departmentsSname) {
		this.departmentsSname = departmentsSname;
	}
	public String getDepartmentsBrief() {
		return departmentsBrief;
	}
	public void setDepartmentsBrief(String departmentsBrief) {
		this.departmentsBrief = departmentsBrief;
	}
	
	public List<SubjectTbl> getSubjectTbl() {
		return subjectTbl;
	}
	public void setSubjectTbl(List<SubjectTbl> subjectTbl) {
		this.subjectTbl = subjectTbl;
	}

	public int getRelevantId() {
		return relevantId;
	}

	public void setRelevantId(int relevantId) {
		this.relevantId = relevantId;
	}

	@Override
	public String toString() {
		return "DepartmentsTbl{" +
				"departmentsId=" + departmentsId +
				", departmentsSname='" + departmentsSname + '\'' +
				", departmentsBrief='" + departmentsBrief + '\'' +
				", relevantId=" + relevantId +
				", subjectTbl=" + subjectTbl +
				'}';
	}
}
