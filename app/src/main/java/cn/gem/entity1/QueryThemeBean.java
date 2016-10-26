package cn.gem.entity1;



public class QueryThemeBean {
	private int moduleId;
    private int flag;
    private Integer pageNo;
    private Integer pageSize;
    
    
	
	
	
	public QueryThemeBean(int moduleId,int flag, Integer pageNo, Integer pageSize) {
		super();
		this.moduleId=moduleId;
		this.flag = flag;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		
	}
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

    
}
