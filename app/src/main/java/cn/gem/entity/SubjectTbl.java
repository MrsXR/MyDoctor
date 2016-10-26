package cn.gem.entity;
/**
 * 科目表
 * @author sony
 *
 */
public class SubjectTbl {
	private int subjectId;
	private int departmentsId;
	private String subjectSname;
	private String subjectBrief;
	
	
	
	public SubjectTbl(String subjectSname) {
		super();
		this.subjectSname = subjectSname;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public int getDepartmentsId() {
		return departmentsId;
	}
	public void setDepartmentsId(int departmentsId) {
		this.departmentsId = departmentsId;
	}
	public String getSubjectSname() {
		return subjectSname;
	}
	public void setSubjectSname(String subjectSname) {
		this.subjectSname = subjectSname;
	}
	public String getSubjectBrief() {
		return subjectBrief;
	}
	public void setSubjectBrief(String subjectBrief) {
		this.subjectBrief = subjectBrief;
	}

	@Override
	public String toString() {
		return "SubjectTbl{" +
				"subjectId=" + subjectId +
				", departmentsId=" + departmentsId +
				", subjectSname='" + subjectSname + '\'' +
				", subjectBrief='" + subjectBrief + '\'' +
				'}';
	}
}
