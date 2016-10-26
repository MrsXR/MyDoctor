package cn.gem.entity1;

public class QueryArticleBean {
	private String articleTitle;
	private int flag;
    private Integer pageNo;
    private Integer pageSize;
    
    
    
	public QueryArticleBean(String articleTitle, int flag, Integer pageNo, Integer pageSize) {
		super();
		this.articleTitle = articleTitle;
		this.flag = flag;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
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
