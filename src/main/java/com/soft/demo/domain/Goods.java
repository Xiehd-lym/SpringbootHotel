package com.soft.demo.domain;

import com.soft.demo.common.domain.BaseDomain;
import com.soft.common.util.StringUtil;
import com.soft.common.util.Transcode;

public class Goods extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -7707218216790302575L;
	private int goods_id; // 
	private int goods_type_id; // 
	private String goods_no; // 
	private String goods_name; // 
	private String goods_pic; // 
	private String goods_publisher; // 
	private double goods_price; // 
	private double goods_discount; // 
	private String goods_date; // 
	private String goods_desc; // 

	private String goods_type_name; // 
	private String goods_price_min; // 
	private String goods_price_max; // 
	private int sale_count; // 
	
	private int user_hobby; // 
	private String ids;
	private String random;
	
	private double w = 0;//余弦值

	public String getGoods_descShow(){
		if (!StringUtil.isEmptyString(goods_desc)) {
			return Transcode.htmlDiscode(goods_desc);
		}
		return goods_desc;
	}
	
	public void setGoods_id(int goods_id){
		this.goods_id=goods_id;
	}

	public int getGoods_id(){
		return goods_id;
	}

	public void setGoods_type_id(int goods_type_id){
		this.goods_type_id=goods_type_id;
	}

	public int getGoods_type_id(){
		return goods_type_id;
	}

	public void setGoods_no(String goods_no){
		this.goods_no=goods_no;
	}

	public String getGoods_no(){
		return goods_no;
	}

	public void setGoods_name(String goods_name){
		this.goods_name=goods_name;
	}

	public String getGoods_name(){
		return goods_name;
	}

	public void setGoods_pic(String goods_pic){
		this.goods_pic=goods_pic;
	}

	public String getGoods_pic(){
		return goods_pic;
	}

	public void setGoods_publisher(String goods_publisher){
		this.goods_publisher=goods_publisher;
	}

	public String getGoods_publisher(){
		return goods_publisher;
	}

	public void setGoods_price(double goods_price){
		this.goods_price=goods_price;
	}

	public double getGoods_price(){
		return goods_price;
	}

	public void setGoods_discount(double goods_discount){
		this.goods_discount=goods_discount;
	}

	public double getGoods_discount(){
		return goods_discount;
	}

	public void setGoods_date(String goods_date){
		this.goods_date=goods_date;
	}

	public String getGoods_date(){
		return goods_date;
	}

	public void setGoods_desc(String goods_desc){
		this.goods_desc=goods_desc;
	}

	public String getGoods_desc(){
		return goods_desc;
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

	public String getGoods_type_name() {
		return goods_type_name;
	}

	public void setGoods_type_name(String goods_type_name) {
		this.goods_type_name = goods_type_name;
	}

	public int getSale_count() {
		return sale_count;
	}

	public void setSale_count(int sale_count) {
		this.sale_count = sale_count;
	}

	public String getGoods_price_min() {
		return goods_price_min;
	}

	public void setGoods_price_min(String goods_price_min) {
		this.goods_price_min = goods_price_min;
	}

	public String getGoods_price_max() {
		return goods_price_max;
	}

	public void setGoods_price_max(String goods_price_max) {
		this.goods_price_max = goods_price_max;
	}

	public int getUser_hobby() {
		return user_hobby;
	}

	public void setUser_hobby(int user_hobby) {
		this.user_hobby = user_hobby;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

}
