package com.soft.demo.dao;

import java.util.List;

import com.soft.demo.domain.DingDan;
import com.soft.demo.domain.GouWuChe;

public interface IGouWuCheDao {

	public abstract int addGouWuChe(GouWuChe gouwuche);
	
	public abstract int delGouWuChe(GouWuChe gouwuche);

	public abstract GouWuChe getGouWuChe(GouWuChe gouwuche);

	public abstract int listGouWuChesCount(GouWuChe gouwuche);
	
	public abstract List<GouWuChe> listGouWuChes(GouWuChe gouwuche);
}
