package com.soft.demo.dao;

import java.util.List;

import com.soft.demo.domain.DingDan;

public interface IDingDanDao {

	public abstract int addDingDan(DingDan dingdan);
	
	public abstract int delDingDan(DingDan dingdan);

	public abstract DingDan getDingDan(DingDan dingdan);

	public abstract int listDingDansCount(DingDan dingdan);
	
	public abstract List<DingDan> listDingDans(DingDan dingdan);
}
