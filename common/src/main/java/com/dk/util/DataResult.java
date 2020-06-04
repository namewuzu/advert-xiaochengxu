package com.dk.util;

import com.github.pagehelper.Page;

import java.util.List;


/**
 * 功能概要：
 * 
 * @author chengzb
 */
public class DataResult<T> {
	public DataResult(List<T> dataList) {
		this.dataList = dataList;
	}

	/*serialVersionUID*/
	private static final long serialVersionUID = 1L;

	private List<T> dataList;//数据

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
}
