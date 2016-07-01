package com.tianwen.dcdp.pojo;

import com.tianwen.dcdp.common.General;

/**
 * 分页标签工具类
 * @author QiaoGuangqing
 * @date 2012-03-29
 */
public class Page {
	private int start;
	private int end;
	private int totalPage;
	private int cpage;
	private int totalRow;
	private int pageSize;
    private String link;
	
    /**
     * 判断当前页是否包含记录
     * @return
     */
    public boolean hasRecords(){
    	return totalPage > 0 ? totalPage >= cpage : false;
    }
    
    /**
     * 
     * @param cpage 当前页码
     * @param totalRow 总记录数
     * @param pageSize 每页记录数
     */
    public Page(int cpage, int totalRow, int pageSize) {
		//如果pageSize为0 默认为10条
		if(pageSize == 0)
			pageSize = 10;
		
		this.totalRow = totalRow;
		this.pageSize = pageSize;
		this.cpage = (cpage <= 0) ? 1 : cpage;
		this.totalPage = totalRow / pageSize;
		if (totalRow % pageSize > 0)
			this.totalPage = this.totalPage + 1;
		//注释掉下面一行，当请求页面大于总页面时，不再返回最后一页
		//if(this.cpage > this.totalPage) this.cpage = this.totalPage;
		
		//选择数据库，默认为Mysql
		String database_type ="0"; 
		if("0".equals(database_type)){
			if(totalRow==0){
				start=0;
				end = 0;
				totalPage = 0;
				return ;
			}
			//LIMIT #START#-1,#END#-#START#+1  
			this.start = ((this.cpage - 1) * pageSize);
			this.end = pageSize;
		}else{
			this.start = (this.cpage - 1) * pageSize;
			this.end = this.start + pageSize;
		}
	}

	public int getStart() {
		return start;
	}
	public int getEnd() {
		return end;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public int getCpage() {
		return cpage;
	}
	public int getTotalRow() {
		return totalRow;
	}
	public int getPageSize() {
		return pageSize;
	}
	public String getLink() {
		if(General.isEmpty(this.link))
			return "cpage";
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
