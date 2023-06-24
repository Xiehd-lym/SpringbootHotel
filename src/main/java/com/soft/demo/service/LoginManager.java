package com.soft.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.common.util.StringUtil;
import com.soft.demo.common.util.Md5;
import com.soft.demo.dao.IManagerDao;
import com.soft.demo.domain.Manager;

@Service
public class LoginManager {
	
	@Autowired
	IManagerDao managerDao;

	public IManagerDao getManagerDao() {
		return managerDao;
	}

	public void setManagerDao(IManagerDao managerDao) {
		this.managerDao = managerDao;
	}

	/**
	 * @Title: getManager
	 * @Description: 查询用户
	 * @param manager
	 * @return Manager
	 */
	public Manager getManager(Manager manager){
		if (!StringUtil.isEmptyString(manager.getManager_pass())) {
			manager.setManager_pass(Md5.makeMd5(manager.getManager_pass()));
		}
		Manager _manager = managerDao.getManager(manager);
		return _manager;
	}
	
	/**
	 * @Title: addManager
	 * @Description: 用户注册
	 * @return void
	 */
	public void addManager(Manager manager) {
		//密码加密
		if (!StringUtil.isEmptyString(manager.getManager_pass())) {
			manager.setManager_pass(Md5.makeMd5(manager.getManager_pass()));
		}
		managerDao.addManager(manager);
		
	}  
}
