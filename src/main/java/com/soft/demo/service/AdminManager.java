package com.soft.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.demo.common.util.Md5;
import com.soft.demo.dao.IEvaluateDao;
import com.soft.demo.dao.IGoodsDao;
import com.soft.demo.dao.IGoodsTypeDao;
import com.soft.demo.dao.IManagerDao;
import com.soft.demo.dao.IOrdersDao;
import com.soft.demo.dao.IOrdersDetailDao;
import com.soft.demo.dao.ISblogDao;
import com.soft.demo.dao.ISblogReplyDao;
import com.soft.demo.dao.IUserDao;
import com.soft.demo.domain.Goods;
import com.soft.demo.domain.GoodsType;
import com.soft.demo.domain.Manager;
import com.soft.demo.domain.Orders;
import com.soft.demo.domain.OrdersDetail;
import com.soft.demo.domain.Sblog;
import com.soft.demo.domain.SblogReply;
import com.soft.demo.domain.User;
import com.soft.common.util.DateUtil;
import com.soft.common.util.StringUtil;
import com.soft.common.util.Transcode;

@Service
public class AdminManager {
	
	//所有待注入Dao类
	@Autowired
	IManagerDao managerDao;
	@Autowired
	IUserDao userDao;
	@Autowired
	ISblogDao sblogDao;
	@Autowired
	ISblogReplyDao sblogReplyDao;
	@Autowired
	IGoodsTypeDao goodsTypeDao;
	@Autowired
	IGoodsDao goodsDao;
	@Autowired
	IEvaluateDao evaluateDao;
	@Autowired
	IOrdersDao ordersDao;
	@Autowired
	IOrdersDetailDao ordersDetailDao;
	
	/**
	 * @Title: queryManager
	 * @Description: 管理员查询
	 * @param manager
	 * @return Manager
	 */
	public Manager queryManager(Manager manager) {
		Manager _manager = managerDao.getManager(manager);
		return _manager;
	}
	
	/**
	 * @Title: updateManager
	 * @Description: 更新管理员信息
	 * @param manager
	 * @return void
	 */
	public void updateManager(Manager manager) {
		//密码MD5加密
		if (!StringUtil.isEmptyString(manager.getManager_pass())) {
			manager.setManager_pass(Md5.makeMd5(manager.getManager_pass()));
		}
		managerDao.updateManager(manager);
	}
	

	/**
	 * @Title: listUsers
	 * @Description: 查询用户集合
	 * @param user
	 * @return List<User>
	 */
	public List<User> listUsers(User user, int[] sum) {
		if (sum != null) {
			sum[0] = userDao.listUsersCount(user);
		}
		List<User> users = userDao.listUsers(user);
		return users;
	}

	/**
	 * @Title: queryUser
	 * @Description: 用户单个查询
	 * @param user
	 * @return User
	 */
	public User queryUser(User user) {
		User _user = userDao.getUser(user);
		return _user;
	}

	/**
	 * @Title: addUser
	 * @Description: 添加用户
	 * @param user
	 * @return void
	 */
	public void addUser(User user) {
		//密码MD5加密
		if (!StringUtil.isEmptyString(user.getUser_pass())) {
			user.setUser_pass(Md5.makeMd5(user.getUser_pass()));
		}
		//默认头像
		if (StringUtil.isEmptyString(user.getUser_photo())) {
			user.setUser_photo("default.jpg");
		}
		//添加用户
		userDao.addUser(user);
	}

	/**
	 * @Title: updateUser
	 * @Description: 更新用户信息
	 * @param user
	 * @return void
	 */
	public void updateUser(User user) {
		//密码MD5加密
		if (!StringUtil.isEmptyString(user.getUser_pass())) {
			user.setUser_pass(Md5.makeMd5(user.getUser_pass()));
		}
		userDao.updateUser(user);
	}

	/**
	 * @Title: delUsers
	 * @Description: 删除用户信息
	 * @param user
	 * @return void
	 */
	public void delUsers(User user) {
		userDao.delUsers(user.getIds().split(","));
	}
	
	/**
	 * @Title: listGoodsTypes
	 * @Description: 商品类型查询
	 * @param goodsType
	 * @return List<GoodsType>
	 */
	public List<GoodsType> listGoodsTypes(GoodsType goodsType, int[] sum) {
		if (sum != null) {
			sum[0] = goodsTypeDao.listGoodsTypesCount(goodsType);
		}
		List<GoodsType> goodsTypes = goodsTypeDao.listGoodsTypes(goodsType);

		return goodsTypes;
	}

	/**
	 * @Title: queryGoodsType
	 * @Description: 商品类型查询
	 * @param goodsType
	 * @return GoodsType
	 */
	public GoodsType queryGoodsType(GoodsType goodsType) {
		GoodsType _goodsType = goodsTypeDao.getGoodsType(goodsType);
		return _goodsType;
	}

	/**
	 * @Title: addGoodsType
	 * @Description: 添加商品类型
	 * @param goodsType
	 * @return void
	 */
	public void addGoodsType(GoodsType goodsType) {
		goodsTypeDao.addGoodsType(goodsType);
	}

	/**
	 * @Title: updateGoodsType
	 * @Description: 更新商品类型信息
	 * @param goodsType
	 * @return void
	 */
	public void updateGoodsType(GoodsType goodsType) {
		goodsTypeDao.updateGoodsType(goodsType);
	}

	/**
	 * @Title: delGoodsType
	 * @Description: 删除商品类型信息
	 * @param goodsType
	 * @return void
	 */
	public void delGoodsTypes(GoodsType goodsType) {
		goodsTypeDao.delGoodsTypes(goodsType.getIds().split(","));
	}
	
	/**
	 * @Title: listGoodss
	 * @Description: 商品查询
	 * @param goods
	 * @return List<Goods>
	 */
	public List<Goods> listGoodss(Goods goods, int[] sum) {
		if (sum != null) {
			sum[0] = goodsDao.listGoodssCount(goods);
		}
		List<Goods> goodss = goodsDao.listGoodss(goods);

		return goodss;
	}

	/**
	 * @Title: queryGoods
	 * @Description: 商品查询
	 * @param goods
	 * @return Goods
	 */
	public Goods queryGoods(Goods goods) {
		Goods _goods = goodsDao.getGoods(goods);
		return _goods;
	}

	/**
	 * @Title: addGoods
	 * @Description: 添加商品
	 * @param goods
	 * @return void
	 */
	public void addGoods(Goods goods) {
		if (!StringUtil.isEmptyString(goods.getGoods_desc())) {
			goods.setGoods_desc(Transcode.htmlEncode(goods.getGoods_desc()));
		}
		goods.setGoods_date(DateUtil.getCurDateTime());
		goodsDao.addGoods(goods);
	}

	/**
	 * @Title: updateGoods
	 * @Description: 更新商品信息
	 * @param goods
	 * @return void
	 */
	public void updateGoods(Goods goods) {
		if (!StringUtil.isEmptyString(goods.getGoods_desc())) {
			goods.setGoods_desc(Transcode.htmlEncode(goods.getGoods_desc()));
		}
		goodsDao.updateGoods(goods);
	}

	/**
	 * @Title: delGoods
	 * @Description: 删除商品信息
	 * @param goods
	 * @return void
	 */
	public void delGoodss(Goods goods) {
		goodsDao.delGoodss(goods.getIds().split(","));
	}
	
	/**
	 * @Title: listOrderss
	 * @Description: 商品订单查询
	 * @param orders
	 * @return List<Orders>
	 */
	public List<Orders>  listOrderss(Orders orders,int[] sum){
		if (sum!=null) {
			sum[0] = ordersDao.listOrderssCount(orders);
		}
		List<Orders> orderss = ordersDao.listOrderss(orders);
		
		return orderss;
	}
	
	/**
	 * @Title: queryOrders
	 * @Description: 商品订单查询
	 * @param orders
	 * @return Orders
	 */
	public Orders  queryOrders(Orders orders){
		Orders _orders = ordersDao.getOrders(orders);
		return _orders;
	}
	 
	
	/**
	 * @Title: delOrderss
	 * @Description: 删除商品订单信息
	 * @param orders
	 * @return void
	 */
	public void  delOrderss(Orders orders){
		ordersDao.delOrderss(orders.getIds().split(","));
	}
	
	/**
	 * @Title: sendOrders
	 * @Description: 订单发货
	 * @param orders
	 * @return void
	 */
	public void sendOrders(Orders orders) {
		//确认订单信息
		orders.setOrders_flag(2);//2-已发货 
		ordersDao.updateOrders(orders);
	}
	
	/**
	 * @Title: listOrdersDetails
	 * @Description: 商品订单明细查询
	 * @param ordersDetail
	 * @return List<OrdersDetail>
	 */
	public List<OrdersDetail> listOrdersDetails(OrdersDetail ordersDetail, int[] sum) {
		if (sum != null) {
			sum[0] = ordersDetailDao.listOrdersDetailsCount(ordersDetail);
		}
		List<OrdersDetail> ordersDetails = ordersDetailDao.listOrdersDetails(ordersDetail);

		return ordersDetails;
	}
	
	/**
	 * @Title: listSblogs
	 * @Description: 查询帖子信息
	 * @param sblog
	 * @return List<Sblog>
	 */
	public List<Sblog>  listSblogs(Sblog sblog,int[] sum){
		if (sum!=null) {
			sum[0] = sblogDao.listSblogsCount(sblog);
		}
		List<Sblog> sblogs = sblogDao.listSblogs(sblog);
		return sblogs;
	}
	
	/**
	 * @Title: querySblog
	 * @Description: 查询帖子
	 * @param sblog
	 * @return Sblog
	 */
	public Sblog querySblog(Sblog sblog) {
		Sblog _sblog = sblogDao.getSblog(sblog);
		
		SblogReply sblogReply = new SblogReply();
		sblogReply.setStart(-1);
		sblogReply.setSblog_id(_sblog.getSblog_id());
		List<SblogReply> replys = sblogReplyDao.listSblogReplys(sblogReply);
		if (replys!=null) {
			_sblog.setReplys(replys);
		}
		return _sblog;
	}
	
	/**
	 * @Title: addSblogReply
	 * @Description: 添加回复
	 * @param sblogReply
	 * @return void
	 */
	public void addSblogReply(SblogReply sblogReply) {
		//添加回复
		if (!StringUtil.isEmptyString(sblogReply.getReply_content())) {
			sblogReply.setReply_content(Transcode.htmlEncode(sblogReply.getReply_content()));
		}
		sblogReply.setReply_date(DateUtil.getCurDateTime());
		
		sblogReplyDao.addSblogReply(sblogReply);
	}
	
	/**
	 * @Title: delSblogs
	 * @Description: 删除帖子信息
	 * @param sblog
	 * @return void
	 */
	public void  delSblogs(Sblog sblog){
		sblogDao.delSblogs(sblog.getIds().split(","));
	}
}
