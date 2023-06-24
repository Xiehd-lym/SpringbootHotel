package com.soft.demo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.common.util.DateUtil;
import com.soft.common.util.StringUtil;
import com.soft.common.util.Transcode;
import com.soft.demo.common.util.Md5;
import com.soft.demo.dao.IEvaluateDao;
import com.soft.demo.dao.IGoodsDao;
import com.soft.demo.dao.IGoodsTypeDao;
import com.soft.demo.dao.IOrdersDao;
import com.soft.demo.dao.IOrdersDetailDao;
import com.soft.demo.dao.ISblogDao;
import com.soft.demo.dao.ISblogReplyDao;
import com.soft.demo.dao.IUserDao;
import com.soft.demo.dao.IUviewDao;
import com.soft.demo.domain.Evaluate;
import com.soft.demo.domain.Goods;
import com.soft.demo.domain.GoodsType;
import com.soft.demo.domain.Orders;
import com.soft.demo.domain.OrdersDetail;
import com.soft.demo.domain.Sblog;
import com.soft.demo.domain.SblogReply;
import com.soft.demo.domain.User;
import com.soft.demo.domain.Uview;

@Service
public class IndexManager {
	
	//所有待注入Dao类
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
	@Autowired
	IUviewDao uviewDao;

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
	 * @Title: updateUser
	 * @Description: 更新用户信息
	 * @param user
	 * @return void
	 */
	public void updateUser(User user) {
		if (!StringUtil.isEmptyString(user.getUser_pass())) {
			user.setUser_pass(Md5.makeMd5(user.getUser_pass()));
		}
		userDao.updateUser(user);
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
	 * @Title: listGoodss
	 * @Description: 查询商品信息
	 * @param goods
	 * @return List<Goods>
	 */
	public List<Goods>  listGoodss(Goods goods,int[] sum){
		if (sum!=null) {
			sum[0] = goodsDao.listGoodssCount(goods);
		}
		List<Goods> goodss = goodsDao.listGoodss(goods);
		return goodss;
	}
	
	public List<Goods>  listGoodssTop(Goods goods){
		List<Goods> goodss = goodsDao.listGoodssTop(goods);
		return goodss;
	}
	
	public List<Goods>  findLikesByUser(int user_id){
		List<Goods> goodss = goodsDao.listGoodssTop(new Goods());
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
	 * @Title: listEvaluates
	 * @Description: 查询商品评价信息
	 * @param evaluate
	 * @return List<Evaluate>
	 */
	public List<Evaluate>  listEvaluates(Evaluate evaluate,int[] sum){
		if (sum!=null) {
			sum[0] = evaluateDao.listEvaluatesCount(evaluate);
		}
		List<Evaluate> evaluates = evaluateDao.listEvaluates(evaluate);
		return evaluates;
	}
	
	/**
	 * @Title: addCard
	 * @Description: 添加购物车
	 * @param ordersDetail
	 */
	@SuppressWarnings("unchecked")
	public void addCard(OrdersDetail ordersDetail,HttpSession httpSession) {
		//查询购物车
		List<OrdersDetail> card = (List<OrdersDetail>) httpSession.getAttribute("card");
		if (card==null) {
			card = new ArrayList<OrdersDetail>();
		}
		OrdersDetail oldDetail = getGoodsFromCard(ordersDetail.getGoods_id(),httpSession);
		if (oldDetail==null) {//新增商品
			//计算总额
			double goods_money = ordersDetail.getGoods_price()*ordersDetail.getGoods_count()*ordersDetail.getGoods_discount()/10.0;
			ordersDetail.setGoods_money(Math.round(goods_money*10)/10.0);
			card.add(ordersDetail);
		}else {//修改购物车商品
			card.remove(oldDetail);
			oldDetail.setGoods_count(oldDetail.getGoods_count()+ordersDetail.getGoods_count());
			double goods_money = oldDetail.getGoods_price()*oldDetail.getGoods_count()*oldDetail.getGoods_discount()/10.0;;
			oldDetail.setGoods_money(Math.round(goods_money*10)/10.0);
			card.add(oldDetail);
		}
		
		httpSession.setAttribute("card", card);
		
	}
	
	/**
	 * @Title: modifyCard
	 * @Description: 修改购物车商品
	 * @param ordersDetail
	 */
	@SuppressWarnings("unchecked")
	public void modifyCard(OrdersDetail ordersDetail,HttpSession httpSession) {
		//查询购物车
		List<OrdersDetail> card = (List<OrdersDetail>) httpSession.getAttribute("card");
		OrdersDetail oldDetail = getGoodsFromCard(ordersDetail.getGoods_id(),httpSession);
		//修改购物车商品
		card.remove(oldDetail);
		oldDetail.setGoods_count(ordersDetail.getGoods_count());
		double goods_money = oldDetail.getGoods_price()*oldDetail.getGoods_count()*oldDetail.getGoods_discount()/10.0;
		oldDetail.setGoods_money(Math.round(goods_money*10)/10.0);
		card.add(oldDetail);
		httpSession.setAttribute("card", card);
		
	}
	
	/**
	 * @Title: delGoodsFromCard
	 * @Description: 从购物车删除商品
	 * @param goods_id
	 */
	@SuppressWarnings("unchecked")
	public void delGoodsFromCard(int goods_id,HttpSession httpSession) {
		//查询购物车
		List<OrdersDetail> card = (List<OrdersDetail>) httpSession.getAttribute("card");
		if (card!=null) {
			for (OrdersDetail ordersDetail : card) {
				if (ordersDetail.getGoods_id()==goods_id) {
					card.remove(ordersDetail);
					break;
				}
			}
		}
		httpSession.setAttribute("card", card);
		
	}
	
	/**
	 * @Title: clearCard
	 * @Description: 清空购物车
	 */
	public void clearCard(HttpSession httpSession) {
		//清空购物车
		httpSession.removeAttribute("card");
		
	}
	
	@SuppressWarnings("unchecked")
	private OrdersDetail getGoodsFromCard(int goods_id,HttpSession httpSession) {
		//查询购物车
		List<OrdersDetail> card = (List<OrdersDetail>) httpSession.getAttribute("card");
		if (card!=null) {
			for (OrdersDetail ordersDetail : card) {
				if (ordersDetail.getGoods_id()==goods_id) {
					return ordersDetail;
				}
			}
		}else {
			return null;
		}
		return null;
	}
	
	/**
	 * @Title: addOrders
	 * @Description: 添加商品订单
	 * @param orders
	 * @return Orders
	 */
	@SuppressWarnings("unchecked")
	public void addOrders(Orders orders,HttpSession httpSession) {
		//生成订单号
		String orders_no = DateUtil.dateToDateString(new Date(), "yyyyMMddHHmmss")+orders.getUser_id();
		orders.setOrders_no(orders_no);
		//订单日期
		orders.setOrders_date(DateUtil.dateToDateString(new Date(), "yyyy-MM-dd"));
		//1：待发货
		orders.setOrders_flag(1);
		//查询购物车
		List<OrdersDetail> card = (List<OrdersDetail>) httpSession.getAttribute("card");
		double orders_money=0;
		for (int i = 0; i < card.size(); i++) {
			OrdersDetail ordersDetail = card.get(i);
			orders_money+=ordersDetail.getGoods_money();//累计总金额
			ordersDetail.setOrders_no(orders_no);//设置订单号
			//保存订单明细
			ordersDetailDao.addOrdersDetail(ordersDetail);
		}
		//设置总额
		orders.setOrders_money(orders_money);
		//保存订单
		ordersDao.addOrders(orders);
		
		//清空购物车
		httpSession.removeAttribute("card");
		
		
	}

	/**
	 * @Title: listCard
	 * @Description: 查询购物车
	 * @return List<OrdersDetail>
	 */
	@SuppressWarnings("unchecked")
	public List<OrdersDetail> listCard(HttpSession httpSession) {
		//查询购物车
		List<OrdersDetail> card = (List<OrdersDetail>) httpSession.getAttribute("card");
		if (card==null) {
			card = new ArrayList<OrdersDetail>();
		}
		return card;
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
	 * @Title: finishOrders
	 * @Description: 确认收货
	 * @param evaluate
	 * @return void
	 */
	public void finishOrders(Orders orders) {
		//更新订单为已收货
		orders.setOrders_flag(3);
		ordersDao.updateOrders(orders);
	}
	
	/**
	 * @Title: listOrdersDetails
	 * @Description: 订单明细查询
	 * @param ordersDetail
	 * @return List<Borrow>
	 */
	public List<OrdersDetail> listOrdersDetails(OrdersDetail ordersDetail, int[] sum) {
		
		if (sum != null) {
			sum[0] = ordersDetailDao.listOrdersDetailsCount(ordersDetail);
		}
		List<OrdersDetail> ordersDetails = ordersDetailDao.listOrdersDetails(ordersDetail);

		
		return ordersDetails;
	}
	
	/**
	 * @Title: addEvaluate
	 * @Description: 添加商品评价
	 * @param evaluate
	 * @return void
	 */
	public void addEvaluate(Evaluate evaluate) {
		//添加商品评价
		evaluate.setEvaluate_date(DateUtil.getCurDateTime());
		evaluateDao.addEvaluateBatch(evaluate);
		
		//更新订单为已评价
		Orders orders = new Orders();
		orders.setOrders_no(evaluate.getOrders_no());
		orders.setOrders_flag(4);
		ordersDao.updateOrders(orders);
		
		
	}

	/**
	 * @Title: listSblogs
	 * @Description: 查询留言信息
	 * @param sblog
	 * @return List<Sblog>
	 */
	public List<Sblog>  listSblogs(Sblog sblog,int[] sum){
		if (sum!=null) {
			sum[0] = sblogDao.listSblogsCount(sblog);
		}
		List<Sblog> sblogs = sblogDao.listSblogs(sblog);
		if (sblogs!=null) {
			for (Sblog sblog2 : sblogs) {
				SblogReply sblogReply = new SblogReply();
				sblogReply.setStart(-1);
				sblogReply.setSblog_id(sblog2.getSblog_id());
				List<SblogReply> replys = sblogReplyDao.listSblogReplys(sblogReply);
				if (replys!=null) {
					sblog2.setReplys(replys);
				}
			}
		}
		return sblogs;
	}
	
	/**
	 * @Title: querySblog
	 * @Description: 查询留言
	 * @param sblog
	 * @return Sblog
	 */
	public Sblog querySblog(Sblog sblog) {
		Sblog _sblog = sblogDao.getSblog(sblog);
		return _sblog;
	}
	
	/**
	 * @Title: addSblog
	 * @Description: 新增留言信息
	 * @param sblog
	 * @return void
	 */
	public void  addSblog(Sblog sblog){
		if (!StringUtil.isEmptyString(sblog.getSblog_content())) {
			sblog.setSblog_content(Transcode.htmlEncode(sblog.getSblog_content()));
		}
		sblog.setSblog_date(DateUtil.getCurDateTime());
		sblogDao.addSblog(sblog);
	}
	
	/**
	 * @Title: updateSblog
	 * @Description: 更新帖子信息
	 * @param sblog
	 * @return void
	 */
	public void updateSblog(Sblog _sblog) {
		sblogDao.updateSblog(_sblog);
	}
	
	/**
	 * @Title: delSblogs
	 * @Description: 删除留言信息
	 * @param sblog
	 * @return void
	 */
	public void  delSblogs(Sblog sblog){
		sblogDao.delSblogs(sblog.getIds().split(","));
	}
	
	/**
	 * @Title: listSblogReplys
	 * @Description: 查询回复集合
	 * @param user
	 * @return List<User>
	 */
	public List<SblogReply> listSblogReplys(SblogReply user, int[] sum) {
		if (sum != null) {
			sum[0] = sblogReplyDao.listSblogReplysCount(user);
		}
		List<SblogReply> sblogReplys = sblogReplyDao.listSblogReplys(user);
		return sblogReplys;
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
	 * @Title: listUviews
	 * @Description: 浏览记录查询
	 * @param uview
	 * @return List<Uview>
	 */
	public List<Uview> listUviews(Uview uview, int[] sum) {
		if (sum != null) {
			sum[0] = uviewDao.listUviewsCount(uview);
		}
		List<Uview> uviews = uviewDao.listUviews(uview);

		return uviews;
	}

	/**
	 * @Title: queryUview
	 * @Description: 浏览记录查询
	 * @param uview
	 * @return Uview
	 */
	public Uview queryUview(Uview uview) {
		Uview _uview = uviewDao.getUview(uview);
		return _uview;
	}

	/**
	 * @Title: addUview
	 * @Description: 添加浏览记录
	 * @param uview
	 * @return void
	 */
	public void addUview(Uview uview) {
		uviewDao.addUview(uview);
	}
	
	/**
	 * @Title: listGoodssSimilarity
	 * @Description: 根据协同过滤算法得出商品推荐
	 * @param goods
	 * @return List<Goods>
	 */
	public List<Goods>  listGoodssSimilarity(HttpSession session){
		List<Goods> recomLists = new ArrayList<>();
		
		User userFront = (User)session.getAttribute("userFront");//当前用户
		int uid = userFront.getUser_id();//当前用户ID
		
		List<Goods> likeLists;  //其他用户喜欢的商品列表
		List<User> users = userDao.listUsers(new User()); //所有用户列表
		List<Goods> goodss = goodsDao.listGoodss(new Goods());   //所有商品列表
        int[][] curMatrix = new int[goodss.size()+5][goodss.size()+5];   //当前矩阵
        int[][] comMatrix = new int[goodss.size()+5][goodss.size()+5];   //共现矩阵
        int[] N = new int[goodss.size()+5];                              //喜欢每个物品的人数
        for(User user: users){
        	if(user.getUser_id()==uid) continue; //当前用户则跳过
        	likeLists = findLikesByUser(user.getUser_id()); //当前用户的喜欢列表
        	for(int i = 0; i < goodss.size(); i++)
                for(int j = 0; j < goodss.size(); j++)
                    curMatrix[i][j] = 0;                               //清空矩阵

            for(int i = 0; i < likeLists.size(); i++){
                int pid1 = likeLists.get(i).getGoods_id();
                ++N[pid1];
                for(int j = i+1; j < likeLists.size(); j++){
                    int pid2 = likeLists.get(j).getGoods_id();
                    ++curMatrix[pid1][pid2];
                    ++curMatrix[pid2][pid1]; //两两加一
                }
            }
            //累加所有矩阵, 得到共现矩阵
            for(int i = 0; i < goodss.size(); i++){
                for(int j = 0; j < goodss.size(); j++){
                    int pid1 = goodss.get(i).getGoods_id(), pid2 = goodss.get(j).getGoods_id();
                    comMatrix[pid1][pid2] += curMatrix[pid1][pid2];
                    comMatrix[pid1][pid2] += curMatrix[pid1][pid2];
                }
            }
        }
        
        TreeSet<Goods> preList = new TreeSet<>(new Comparator<Goods>() {
			@Override
			public int compare(Goods o1, Goods o2) {
				// TODO Auto-generated method stub
				if(o1.getW()!=o2.getW())
                    return o1.getW() > o2.getW() ? -1 : 1;
                else
                    return o1.getGoods_id()-o2.getGoods_id();
			}
        	
		});//预处理的列表
		
        likeLists = findLikesByUser(uid);       //当前用户喜欢的论文列表
        boolean[] used = new boolean[goodss.size()+5];  //判重数组
        for(Goods like: likeLists){
            int Nij = 0;                         //既喜欢i又喜欢j的人数
            double Wij;                          //相似度

            int i = like.getGoods_id();
            for(Goods goods: goodss){
                if(like.getGoods_id() == goods.getGoods_id()) continue;
                int j = goods.getGoods_id();

                Nij = comMatrix[i][j];
                Wij = (double)Nij/Math.sqrt(N[i]*N[j]); //计算余弦相似度

                goods.setW(Wij);

                if(used[goods.getGoods_id()]) continue;
                preList.add(goods);
                used[goods.getGoods_id()] = true;
            }
        }
        
        for(int i = 0; preList.size()>0 && i<5; i++){
            recomLists.add(preList.pollLast());
            preList.pollLast();
        }
        if(recomLists.size()<5){
            //推荐数量不满5个, 补足喜欢数最高的商品
            recomLists = listGoodssTop(new Goods());
        }
        
		return recomLists;
	}
	
}
