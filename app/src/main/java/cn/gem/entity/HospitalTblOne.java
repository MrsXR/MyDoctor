package cn.gem.entity;

import java.util.List;

/**
 *某一个医院的全部信息
 * @author sony
 *
 */
public class HospitalTblOne {

	    private HospitalTbl hospitalTbl;
	    private List<DepartmentsTbl> departmentsTbl;//对应科目信息

	public HospitalTblOne() {
	}

	public HospitalTblOne(HospitalTbl hospitalTbl, List<DepartmentsTbl> departmentsTbl) {
			super();
			this.hospitalTbl = hospitalTbl;
			this.departmentsTbl = departmentsTbl;
		}


		public HospitalTbl getHospitalTbl() {
			return hospitalTbl;
		}


		public void setHospitalTbl(HospitalTbl hospitalTbl) {
			this.hospitalTbl = hospitalTbl;
		}


		public List<DepartmentsTbl> getDepartmentsTbl() {
			return departmentsTbl;
		}


		public void setDepartmentsTbl(List<DepartmentsTbl> departmentsTbl) {
			this.departmentsTbl = departmentsTbl;
		}
	    
	    
}
