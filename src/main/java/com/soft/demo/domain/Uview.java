package com.soft.demo.domain;

import com.soft.demo.common.domain.BaseDomain;

public class Uview extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 5196212095551018993L;
	private int uview_id; // 
	private int user_id; // 
	private int goods_id; // 

	private String ids;
	private String random;
	
	public int getUview_id() {
		return uview_id;
	}
	public void setUview_id(int uview_id) {
		this.uview_id = uview_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getRandom() {
		return random;
	}
	public void setRandom(String random) {
		this.random = random;
	}
	

}
