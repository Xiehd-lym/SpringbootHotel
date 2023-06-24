package com.soft.demo.dao;

import java.util.List;

import com.soft.demo.domain.DingDan;
import com.soft.demo.domain.Pinglun;

public interface IPinglunDao {

	public abstract int addPinglun(Pinglun pinglun);
	
	public abstract int delPinglun(Pinglun pinglun);

	public abstract Pinglun getPinglun(Pinglun pinglun);

	public abstract int listPinglunsCount(Pinglun pinglun);
	
	public abstract List<Pinglun> listPingluns(Pinglun pinglun);
}
