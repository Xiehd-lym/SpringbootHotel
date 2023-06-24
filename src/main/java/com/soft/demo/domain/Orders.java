package com.soft.demo.domain;

import com.soft.demo.common.domain.BaseDomain;

public class Orders extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -3910377482946958692L;
	private int orders_id; // 
	private String orders_no; // 
	private String orders_date; // 
	private int user_id; // 
	private String real_name; // 
	private String user_mail; // 
	private String user_phone; // 
	private double orders_money; // 
	private int orders_flag; // 1：待收货 2-已发货 2-已收货 3-已评价

	private String orders_date_min;
	private String orders_date_max;
	private String ids;
	private String random;
	private String address;

	public String getOrders_flagDesc() {
		switch (orders_flag) {
			case 1:
				return "待发货";
			case 2:
				return "已发货";
			case 3:
				return "已收货";
			case 4:
				return "已评价";
			default:
				return "";
		}
	}
	
	public void setOrders_id(int orders_id){
		this.orders_id=orders_id;
	}

	public int getOrders_id(){
		return orders_id;
	}

	public void setOrders_no(String orders_no){
		this.orders_no=orders_no;
	}

	public String getOrders_no(){
		return orders_no;
	}

	public void setOrders_date(String orders_date){
		this.orders_date=orders_date;
	}

	public String getOrders_date(){
		return orders_date;
	}

	public void setUser_id(int user_id){
		this.user_id=user_id;
	}

	public int getUser_id(){
		return user_id;
	}

	public void setReal_name(String real_name){
		this.real_name=real_name;
	}

	public String getReal_name(){
		return real_name;
	}

	public void setUser_mail(String user_mail){
		this.user_mail=user_mail;
	}

	public String getUser_mail(){
		return user_mail;
	}

	public void setUser_phone(String user_phone){
		this.user_phone=user_phone;
	}

	public String getUser_phone(){
		return user_phone;
	}

	public void setOrders_money(double orders_money){
		this.orders_money=orders_money;
	}

	public double getOrders_money(){
		return orders_money;
	}

	public void setOrders_flag(int orders_flag){
		this.orders_flag=orders_flag;
	}

	public int getOrders_flag(){
		return orders_flag;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getIds() {
		return ids;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getRandom() {
		return random;
	}

	public String getOrders_date_min() {
		return orders_date_min;
	}

	public String getOrders_date_max() {
		return orders_date_max;
	}

	public void setOrders_date_min(String orders_date_min) {
		this.orders_date_min = orders_date_min;
	}

	public void setOrders_date_max(String orders_date_max) {
		this.orders_date_max = orders_date_max;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
