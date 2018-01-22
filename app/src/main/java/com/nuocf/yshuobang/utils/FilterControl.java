package com.nuocf.yshuobang.utils;
/**
 * 
* @ClassName: FilterControl 
* @Description: TODO(筛选参数)
* @author efun
* @date 2014-11-5 下午3:12:40 
*
 */
public abstract class FilterControl {
	//地区id
	private String areaId = "0";
	//科目id
	private String sectionId="0";
	//疾病id
	private String deseaseId=null;
	//排序
	private String orderBy="0";

	public FilterControl() {}

	/**
	 * 
	* @Title: reset 
	* @Description: TODO(重置参数)  
	 */
	public void reset(){
		areaId = "0";
		sectionId="0";
		orderBy="0";
		deseaseId=null;
	}

	/**
	 * 
	* @Title: getAreaId 
	* @Description: TODO(获取地区) 
	 */
	public String getAreaId() {
		return areaId;
	}

	/**
	 * 
	* @Title: setAreaId 
	* @Description: TODO(设置地区参数) 
	* @param areaId 地区id
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getDeseaseId() {
		return deseaseId;
	}

	public void setDeseaseId(String deseaseId) {
		this.deseaseId = deseaseId;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
