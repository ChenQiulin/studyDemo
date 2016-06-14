/**
 * Copyright 2015 ProficientCity Inc.
 */
package util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页组件中的页实体
 * 
 * @author Chenql
 */

public class Page<T> {

	/**
	 * 数据集
	 */
	private List<T> pageData;

	/**
	 * 
	 */
	private int pageNo;

	private int pageSize;

	private long totalNum;

	public long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
	}

	private int totalPageNum;

	private boolean hasNext;

	public Page() {
		this.pageNo = 0;
		this.pageSize = 0;

		this.totalNum = 0;
		this.totalPageNum = 0;

		this.hasNext = false;
		this.pageData = new ArrayList<T>();
	}

	/*
	 * public Page(int pageNo, int pageSize, int totalRowNum, List<T> pageData)
	 * { this.pageNo = pageNo; this.pageSize = pageSize; this.totalRowNum =
	 * totalRowNum; this.pageData = pageData; }
	 * 
	 * public Page(int pageNo, int pageSize, int totalRowNum,int
	 * totalPageNum,List<T> pageData) { this.pageNo = pageNo; this.pageSize =
	 * pageSize; this.totalRowNum = totalRowNum; this.totalPageNum=totalPageNum;
	 * this.pageData = pageData; }
	 */

	public List<T> getPageData() {
		return pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int setTotalPageNum(int totalPageNum) {
		return this.totalPageNum = totalPageNum;
	}

	public int getTotalPageNum() { // 计算总页数
		return totalPageNum;
	}

	public boolean isEmpty() {
		return this.pageData == null || this.pageData.isEmpty();
	}

	public void setEmpty(boolean isEmpty) {}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

}
