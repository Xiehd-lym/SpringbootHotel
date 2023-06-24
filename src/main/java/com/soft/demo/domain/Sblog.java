package com.soft.demo.domain;

import com.soft.demo.common.domain.BaseDomain;

import java.util.ArrayList;
import java.util.List;

import com.soft.common.util.StringUtil;
import com.soft.common.util.Transcode;

public class Sblog extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 4229284833893762339L;
	private int sblog_id; // 
	private int user_id; // 
	private String sblog_content; // 
	private String sblog_date; // 

	private String nick_name; // 
	private String user_photo; //
	private String ids;
	private String random;
	
	private List<SblogReply> replys = new ArrayList<SblogReply>();

	public String getSblog_contentShow(){
		if (!StringUtil.isEmptyString(sblog_content)) {
			return Transcode.htmlDiscode(sblog_content);
		}
		return sblog_content;
	}
	
	public void setSblog_id(int sblog_id){
		this.sblog_id=sblog_id;
	}

	public int getSblog_id(){
		return sblog_id;
	}

	public void setUser_id(int user_id){
		this.user_id=user_id;
	}

	public int getUser_id(){
		return user_id;
	}

	public void setSblog_content(String sblog_content){
		this.sblog_content=sblog_content;
	}

	public String getSblog_content(){
		return sblog_content;
	}

	public void setSblog_date(String sblog_date){
		this.sblog_date=sblog_date;
	}

	public String getSblog_date(){
		return sblog_date;
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

	public String getUser_photo() {
		return user_photo;
	}

	public void setUser_photo(String user_photo) {
		this.user_photo = user_photo;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public List<SblogReply> getReplys() {
		return replys;
	}

	public void setReplys(List<SblogReply> replys) {
		this.replys = replys;
	}

}
