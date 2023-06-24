package com.soft.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.common.util.StringUtil;
import com.soft.demo.common.util.Md5;
import com.soft.demo.dao.IUserDao;
import com.soft.demo.domain.User;

@Service
public class LoginIndexManager {
	
	@Autowired
	IUserDao userDao;

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @Title: getUser
	 * @Description: 查询用户
	 * @param user
	 * @return User
	 */
	public User getUser(User user){
		if (!StringUtil.isEmptyString(user.getUser_pass())) {
			user.setUser_pass(Md5.makeMd5(user.getUser_pass()));
		}
		User _user = userDao.getUser(user);
		return _user;
	}
	
	/**
	 * @Title: addUser
	 * @Description: 用户注册
	 * @return void
	 */
	public void addUser(User user) {
		//密码加密
		if (!StringUtil.isEmptyString(user.getUser_pass())) {
			user.setUser_pass(Md5.makeMd5(user.getUser_pass()));
		}
		//默认头像
		if (StringUtil.isEmptyString(user.getUser_photo())) {
			user.setUser_photo("default.jpg");
		}
		userDao.addUser(user);
		
	}  
	public static void main(String[] args) {
		System.out.println(Md5.makeMd5("111111"));
	}
}
