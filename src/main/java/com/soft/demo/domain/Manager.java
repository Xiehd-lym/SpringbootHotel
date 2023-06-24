package com.soft.demo.domain;

import com.soft.demo.common.domain.BaseDomain;

public class Manager extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 748980125996575524L;
	private int manager_id; // 
	private String manager_name; // 
	private String manager_pass; // 
	private String real_name; // 
	private int manager_sex; // 1：男 0：女
	private String manager_mail; // 
	private String manager_phone; // 

	private String manager_passOld; // 
	private String ids;
	private String random;
	
	public String getManager_sexDesc(){
		switch (manager_sex) {
		case 1:
			return "男";
		case 2:
			return "女";
		default:
			return "";
		}
	}

	public void setManager_id(int manager_id){
		this.manager_id=manager_id;
	}

	public int getManager_id(){
		return manager_id;
	}

	public void setManager_name(String manager_name){
		this.manager_name=manager_name;
	}

	public String getManager_name(){
		return manager_name;
	}

	public void setManager_pass(String manager_pass){
		this.manager_pass=manager_pass;
	}

	public String getManager_pass(){
		return manager_pass;
	}

	public void setReal_name(String real_name){
		this.real_name=real_name;
	}

	public String getReal_name(){
		return real_name;
	}

	public void setManager_sex(int manager_sex){
		this.manager_sex=manager_sex;
	}

	public int getManager_sex(){
		return manager_sex;
	}

	public void setManager_mail(String manager_mail){
		this.manager_mail=manager_mail;
	}

	public String getManager_mail(){
		return manager_mail;
	}

	public void setManager_phone(String manager_phone){
		this.manager_phone=manager_phone;
	}

	public String getManager_phone(){
		return manager_phone;
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

	public String getManager_passOld() {
		return manager_passOld;
	}

	public void setManager_passOld(String manager_passOld) {
		this.manager_passOld = manager_passOld;
	}

}
