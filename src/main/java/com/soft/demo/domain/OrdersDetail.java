package com.soft.demo.domain;

import com.soft.demo.common.domain.BaseDomain;

public class OrdersDetail extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -961818115851050015L;
	private int detail_id; // 
	private String orders_no; // 
	private int goods_id; // 
	private String goods_name; // 
	private double goods_price; // 
	private double goods_discount; // 
	private int goods_count; // 
	private double goods_money; // 

	private String real_name;
	private String orders_money;
	private String ids;
	private String random;

	public void setDetail_id(int detail_id){
		this.detail_id=detail_id;
	}

	public int getDetail_id(){
		return detail_id;
	}

	public void setOrders_no(String orders_no){
		this.orders_no=orders_no;
	}

	public String getOrders_no(){
		return orders_no;
	}

	public void setGoods_id(int goods_id){
		this.goods_id=goods_id;
	}

	public int getGoods_id(){
		return goods_id;
	}

	public void setGoods_name(String goods_name){
		this.goods_name=goods_name;
	}

	public String getGoods_name(){
		return goods_name;
	}

	public void setGoods_price(double goods_price){
		this.goods_price=goods_price;
	}

	public double getGoods_price(){
		return goods_price;
	}

	public void setGoods_count(int goods_count){
		this.goods_count=goods_count;
	}

	public int getGoods_count(){
		return goods_count;
	}

	public void setGoods_money(double goods_money){
		this.goods_money=goods_money;
	}

	public double getGoods_money(){
		return goods_money;
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

	public String getReal_name() {
		return real_name;
	}

	public String getOrders_money() {
		return orders_money;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public void setOrders_money(String orders_money) {
		this.orders_money = orders_money;
	}

	public double getGoods_discount() {
		return goods_discount;
	}

	public void setGoods_discount(double goods_discount) {
		this.goods_discount = goods_discount;
	}

}
