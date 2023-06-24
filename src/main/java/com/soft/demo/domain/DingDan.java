package com.soft.demo.domain;

import java.util.Date;

public class DingDan {
	long id;
	int goods_id;
	int user_id;
	Date time;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the goods_id
	 */
	public int getGoods_id() {
		return goods_id;
	}
	/**
	 * @param goods_id the goods_id to set
	 */
	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}
	/**
	 * @return the user_id
	 */
	public int getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	public DingDan() {
		super();
		// TODO Auto-generated constructor stub
	}

}
