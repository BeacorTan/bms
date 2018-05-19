package com.common.framework.util;

import java.util.List;

/**
 * 分页返回结果：
 */
public class PagedResult<T>  {
	
	/*serialVersionUID*/
	private static final long serialVersionUID = 1L;

	private List<T> dataList;//数据
	
	private long pageNumber;//当前页
	
	private long pageSize;//条数
	
	private long total;//总条数
	
	private long pages;//总页面数目

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getPages() {
		return pages;
	}

	public void setPages(long pages) {
		this.pages = pages;
	}
	
}
