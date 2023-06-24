package com.soft.demo.domain;

import com.soft.demo.common.domain.BaseDomain;

public class Evaluate extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 5196212095551018993L;
	private int evaluate_id; // 
	private String orders_no; // 
	private int user_id; // 
	private String nick_name; // 
	private int goods_id; // 
	private String evaluate_date; // 
	private int evaluate_level; // 1-差评 2-一般 3-比较满意 4-好评
	private String evaluate_content; // 

	private String ids;
	private String random;
	
	public String getEvaluate_levelDesc() {
		switch (evaluate_level) {
			case 1:
				return "差评";
			case 2:
				return "一般";
			case 3:
				return "比较满意";
			case 4:
				return "好评";
			default:
				return "";
		}
	}

	public void setEvaluate_id(int evaluate_id){
		this.evaluate_id=evaluate_id;
	}

	public int getEvaluate_id(){
		return evaluate_id;
	}

	public void setOrders_no(String orders_no){
		this.orders_no=orders_no;
	}

	public String getOrders_no(){
		return orders_no;
	}

	public void setUser_id(int user_id){
		this.user_id=user_id;
	}

	public int getUser_id(){
		return user_id;
	}

	public void setNick_name(String nick_name){
		this.nick_name=nick_name;
	}

	public String getNick_name(){
		return nick_name;
	}

	public void setGoods_id(int goods_id){
		this.goods_id=goods_id;
	}

	public int getGoods_id(){
		return goods_id;
	}

	public void setEvaluate_date(String evaluate_date){
		this.evaluate_date=evaluate_date;
	}

	public String getEvaluate_date(){
		return evaluate_date;
	}

	public void setEvaluate_level(int evaluate_level){
		this.evaluate_level=evaluate_level;
	}

	public int getEvaluate_level(){
		return evaluate_level;
	}

	public void setEvaluate_content(String evaluate_content){
		this.evaluate_content=evaluate_content;
	}

	public String getEvaluate_content(){
		return evaluate_content;
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

}
