package com.soft.demo.domain;

import com.soft.demo.common.domain.BaseDomain;

public class GoodsType extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -9045409815458944449L;
	private int goods_type_id; // 
	private String goods_type_name; // 

	private String ids;
	private String random;

	public void setGoods_type_id(int goods_type_id){
		this.goods_type_id=goods_type_id;
	}

	public int getGoods_type_id(){
		return goods_type_id;
	}

	public void setGoods_type_name(String goods_type_name){
		this.goods_type_name=goods_type_name;
	}

	public String getGoods_type_name(){
		return goods_type_name;
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
