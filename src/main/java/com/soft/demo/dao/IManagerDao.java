package com.soft.demo.dao;

import java.util.List;

import com.soft.demo.domain.Manager;

public interface IManagerDao {

	public abstract int addManager(Manager manager);

	public abstract int delManager(String manager_id);

	public abstract int delManagers(String[] manager_ids);

	public abstract int updateManager(Manager manager);

	public abstract Manager getManager(Manager manager);

	public abstract List<Manager>  listManagers(Manager manager);

	public abstract int listManagersCount(Manager manager);

}
