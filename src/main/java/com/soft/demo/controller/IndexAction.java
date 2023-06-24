package com.soft.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soft.common.util.JSONData;
import com.soft.demo.common.util.Md5;
import com.soft.demo.common.util.PaperUtil;
import com.soft.demo.dao.IDingDanDao;
import com.soft.demo.dao.IGoodsDao;
import com.soft.demo.dao.IGouWuCheDao;
import com.soft.demo.dao.IOrdersDetailDao;
import com.soft.demo.dao.IPinglunDao;
import com.soft.demo.dao.ZixunMapper;
import com.soft.demo.domain.DingDan;
import com.soft.demo.domain.Evaluate;
import com.soft.demo.domain.Goods;
import com.soft.demo.domain.GoodsType;
import com.soft.demo.domain.GouWuChe;
import com.soft.demo.domain.Manager;
import com.soft.demo.domain.Orders;
import com.soft.demo.domain.OrdersDetail;
import com.soft.demo.domain.Pinglun;
import com.soft.demo.domain.Sblog;
import com.soft.demo.domain.Similar;
import com.soft.demo.domain.User;
import com.soft.demo.domain.Uview;
import com.soft.demo.domain.Zixun;
import com.soft.demo.domain.ZixunExample;
import com.soft.demo.domain.ZixunExample.Criteria;
import com.soft.demo.service.AdminManager;
import com.soft.demo.service.IndexManager;

@Controller
public class IndexAction{

	@Autowired
	IndexManager indexManager;
	@Autowired
	IGouWuCheDao gouWuCheDao;
	@Autowired
	IDingDanDao dingDanDao;
	@Autowired
	IOrdersDetailDao ordersDetailDao;
	@Autowired
	IPinglunDao pinglunDao;
	@Autowired
	IGoodsDao goodsDao;
	@Autowired
	ZixunMapper zixunMapper;
	public IndexManager getIndexManager() {
		return indexManager;
	}
	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
	}

	String tip;

	/**
	 * @Title: index
	 * @Description: 首页
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value= { " ", "", "/" },method=RequestMethod.GET)
	public void admin(Manager paramsManager,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession) throws Exception{
		response.sendRedirect(request.getContextPath() + "index.jsp");
	}

	/**
	 * @Title: index
	 * @Description: 首页
	 * @return String
	 */
	@RequestMapping(value="page_index.action",method=RequestMethod.GET)
	public String index(ModelMap model){
		//查询热销商品信息
		Goods goods = new Goods();
		goods.setStart(0);
		goods.setLimit(8);
		List<Goods> goodss = indexManager.listGoodssTop(goods);
		model.addAttribute("goodss", goodss);

		//查询最新上架商品
		List<Goods> goodss2 = indexManager.listGoodss(goods, null);
		model.addAttribute("goodss2", goodss2);

		return "default"; 
	}

	/**
	 * @Title: listGoodss
	 * @Description: 查询商品
	 * @return String
	 */
	@RequestMapping(value="page_listGoodss.action")
	public String listGoodss(Goods paramsGoods,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			if (paramsGoods==null) {
				paramsGoods = new Goods();
			}
			if (paperUtil==null) {
				paperUtil = new PaperUtil();
			}
			//设置分页信息
			paperUtil.setPagination(paramsGoods);
			//总的条数
			int[] sum={0};
			//查询商品列表
			List<Goods> goodss = indexManager.listGoodss(paramsGoods,sum); 
			model.addAttribute("goodss", goodss);
			model.addAttribute("paramsGoods", paramsGoods);
			paperUtil.setTotalCount(sum[0]);

			//查询商品类型
			GoodsType goodsType = new GoodsType();
			goodsType.setStart(-1);
			List<GoodsType> goodsTypes = indexManager.listGoodsTypes(goodsType, null);
			if (goodsTypes==null) {
				goodsTypes = new ArrayList<GoodsType>();
			}
			model.addAttribute("goodsTypes", goodsTypes);

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "goods";
	}
	@RequestMapping(value="page_listzixuns.action",method=RequestMethod.GET)
	public String page_listzixuns( PaperUtil paperUtil,Integer id,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
 
		try {
		 
			
			//设置分页信息
			if (paperUtil==null) {
				paperUtil = new PaperUtil();
			}
  
			//查询商品类型列表
			List<Zixun> zixuns = zixunMapper.selectByExample(new ZixunExample()); 
			
			model.addAttribute("zixuns", zixuns);
//			model.addAttribute("zixun", zixun);
			paperUtil.setTotalCount(zixuns.size());
			
		} catch (Exception e) {
 			return "infoTip";
		}
		
		return "zixunshow";
	}
	/**
	 * @Title: listGoodssHobby
	 * @Description: 查询商品推荐
	 * @return String
	 */
	@RequestMapping(value="page_listGoodssHobby.action")
	public String listGoodssHobby(Goods paramsGoods,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			if (paramsGoods==null) {
				paramsGoods = new Goods();
			}
			if (paperUtil==null) {
				paperUtil = new PaperUtil();
			}
			//设置分页信息
			paperUtil.setPagination(paramsGoods);
			//总的条数
			int[] sum={0};
			//查询商品列表
			User userFront = (User)httpSession.getAttribute("userFront");

			List<Integer> gouwucheList = getReleativeUserByGouWuChe(userFront.getUser_id());
			List<Integer> dingdanList = getReleativeUserByDingdan(userFront.getUser_id());
			List<Integer> pinglunList = getReleativeUserByPinglun(userFront.getUser_id());
			System.out.println(gouwucheList);
			System.out.println(dingdanList);
			System.out.println(pinglunList);
			Map<Integer, Double> map = new HashMap<Integer, Double>();
			for(Integer id:gouwucheList) {
				double similar = getSimilarWithUserByGouWuChe(userFront.getUser_id(), id);
				map.put(id, similar);
			}
			for(Integer id:dingdanList) {
				double similar = getSimilarWithUserByDingdan(userFront.getUser_id(), id);
				if(map.get(id) == null) {
					map.put(id, similar);
				}else {
					map.put(id,map.get(id)+similar);
				}
			}
			for(Integer id:gouwucheList) {
				double similar = getSimilarWithUserByPinglun(userFront.getUser_id(), id);
				if(map.get(id) == null) {
					map.put(id, similar);
				}else {
					map.put(id,map.get(id)+similar);
				}
			}
			List<Similar> list = new ArrayList<Similar>();
			if(map.size() != 0) {
				for(Map.Entry<Integer, Double> entry:map.entrySet()){  
					Similar similar = new Similar();
					similar.setUserid(entry.getKey());
					similar.setSimilar(entry.getValue());
					list.add(similar);
				}  
			}
			Collections.sort(list, new Comparator<Similar>() {  
				@Override
				public int compare(Similar arg0, Similar arg1) {
					// TODO Auto-generated method stub
					if(arg0.getSimilar()>arg1.getSimilar()) {
						return 0;
					}else {
						return 1;
					}

				}  
			});
			List<Goods> goods = new ArrayList<Goods>();
			for(Similar similar:list) {
				Pinglun pinglun = new Pinglun();
				pinglun.setUser_id(similar.getUserid());
				List<Pinglun> pingluns = pinglunDao.listPingluns(pinglun);
				for(Pinglun temp:pingluns) {
					Goods tempGoods = new Goods();
					tempGoods.setGoods_id(temp.getGoods_id());
					List<Goods> goodsList = goodsDao.listGoodss(tempGoods);
					goods.addAll(goodsList);
				}
			}
			for(Similar similar:list) {
				DingDan dingdan = new DingDan();
				dingdan.setUser_id(similar.getUserid());
				List<DingDan> dingDans = dingDanDao.listDingDans(dingdan);
				for(DingDan temp:dingDans) {
					Goods tempGoods = new Goods();
					tempGoods.setGoods_id(temp.getGoods_id());
					List<Goods> goodsList = goodsDao.listGoodss(tempGoods);
					goods.addAll(goodsList);
				}
			}
			for(Similar similar:list) {
				GouWuChe gouwuche = new GouWuChe();
				gouwuche.setUser_id(similar.getUserid());
				List<GouWuChe> gouWuChes = gouWuCheDao.listGouWuChes(gouwuche);
				for(GouWuChe temp:gouWuChes) {
					Goods tempGoods = new Goods();
					tempGoods.setGoods_id(temp.getGoods_id());
					List<Goods> goodsList = goodsDao.listGoodss(tempGoods);
					goods.addAll(goodsList);
				}
			}
			paramsGoods.setUser_hobby(userFront.getUser_id());
			if(goods.size()<5) {
				//				List<Goods> goodss = indexManager.listGoodss(paramsGoods,sum); 
				//				goods.addAll(goodss);
			}
			Set<Goods> goodSet = new HashSet<Goods>();
			goods = goods.stream().distinct().collect(Collectors.toList());
			for (int i = 0; i < goods.size(); i++) { 
				for (int j = 0; j < goods.size(); j++) { 
					if(i!=j&&goods.get(i).getGoods_id()==goods.get(j).getGoods_id()) { 
						goodSet.add(goods.get(i));
						
					} 
				} 
			} 
			for(Goods goods2:goodSet) {
				goods.remove(goods2); 
			}
			model.addAttribute("goodss", goods);
			model.addAttribute("paramsGoods", paramsGoods);
			paperUtil.setTotalCount(goods.size());
			//查询商品类型
			GoodsType goodsType = new GoodsType();
			goodsType.setStart(-1);
			List<GoodsType> goodsTypes = indexManager.listGoodsTypes(goodsType, null);
			if (goodsTypes==null) {
				goodsTypes = new ArrayList<GoodsType>();
			}
			model.addAttribute("goodsTypes", goodsTypes);

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "goodsHobby";
	}

	double getSimilarWithUserByGouWuChe(int me,int otherUserId) {
		GouWuChe gouwuche = new GouWuChe();
		gouwuche.setUser_id(otherUserId);
		List<Integer> mygoods_ids = new ArrayList<Integer>();
		List<Integer> mygoods_ids_clone = new ArrayList<Integer>();
		List<Integer> othergoods_ids = new ArrayList<Integer>();
		List<Integer> othergoods_ids_clone = new ArrayList<Integer>();
		List<GouWuChe> otherUserGouWuChes = (List<GouWuChe>) gouWuCheDao.listGouWuChes(gouwuche);
		for (GouWuChe gChe:otherUserGouWuChes) {
			othergoods_ids.add(gChe.getGoods_id());
			othergoods_ids_clone.add(gChe.getGoods_id());
		}
		gouwuche = new GouWuChe();
		gouwuche.setUser_id(me);
		List<GouWuChe> mygoodsGouWuChes = (List<GouWuChe>) gouWuCheDao.listGouWuChes(gouwuche);
		for (GouWuChe gChe:mygoodsGouWuChes) {
			mygoods_ids.add(gChe.getGoods_id());
		}
		mygoods_ids_clone.retainAll(othergoods_ids);//mygoods_ids变为交集
		int a = mygoods_ids_clone.size();
		int b = 0;
		othergoods_ids.removeAll(mygoods_ids);
		othergoods_ids.addAll(mygoods_ids);
		if(othergoods_ids.size() == 0) {
			b = 1;
		}else {
			b = othergoods_ids.size();
		}
		return a/b*1;
	}

	double getSimilarWithUserByDingdan(int me,int otherUserId) {
		DingDan dingdan = new DingDan();
		dingdan.setUser_id(otherUserId);
		List<Integer> mygoods_ids = new ArrayList<Integer>();
		List<Integer> mygoods_ids_clone = new ArrayList<Integer>();
		List<Integer> othergoods_ids = new ArrayList<Integer>();
		List<Integer> othergoods_ids_clone = new ArrayList<Integer>();
		List<DingDan> otherUserDingDans = (List<DingDan>) dingDanDao.listDingDans(dingdan);
		for (DingDan gChe:otherUserDingDans) {
			othergoods_ids.add(gChe.getGoods_id());
			othergoods_ids_clone.add(gChe.getGoods_id());
		}
		dingdan = new DingDan();
		dingdan.setUser_id(me);
		List<DingDan> mygoodsDingDans = (List<DingDan>) dingDanDao.listDingDans(dingdan);
		for (DingDan gChe:mygoodsDingDans) {
			mygoods_ids.add(gChe.getGoods_id());
		}
		mygoods_ids_clone.retainAll(othergoods_ids);//mygoods_ids变为交集
		int a = mygoods_ids.size();
		int b = 0;
		othergoods_ids.removeAll(mygoods_ids);
		othergoods_ids.addAll(mygoods_ids);
		if(othergoods_ids.size() == 0) {
			b = 1;
		}else {
			b = othergoods_ids.size();
		}
		return a/b*2;
	}

	double getSimilarWithUserByPinglun(int me,int otherUserId) {
		Pinglun pinglun = new Pinglun();
		pinglun.setUser_id(otherUserId);
		List<Integer> mygoods_ids = new ArrayList<Integer>();
		List<Integer> mygoods_ids_clone = new ArrayList<Integer>();
		List<Integer> othergoods_ids = new ArrayList<Integer>();
		List<Integer> othergoods_ids_clone = new ArrayList<Integer>();
		List<Pinglun> otherUserPingluns = (List<Pinglun>) pinglunDao.listPingluns(pinglun);
		for (Pinglun gChe:otherUserPingluns) {
			othergoods_ids.add(gChe.getGoods_id());
			othergoods_ids_clone.add(gChe.getGoods_id());
		}
		pinglun = new Pinglun();
		pinglun.setUser_id(me);
		List<Pinglun> mygoodsPingluns = (List<Pinglun>) pinglunDao.listPingluns(pinglun);
		for (Pinglun gChe:mygoodsPingluns) {
			mygoods_ids.add(gChe.getGoods_id());
		}
		mygoods_ids_clone.retainAll(othergoods_ids);//mygoods_ids变为交集
		int a = mygoods_ids_clone.size();
		int b = 0;
		othergoods_ids.removeAll(mygoods_ids);
		othergoods_ids.addAll(mygoods_ids);
		if(othergoods_ids.size() == 0) {
			b = 1;
		}else {
			b = othergoods_ids.size();
		}
		return a/b*3;
	}

	//通过购物车获取相关联的用户
	public List<Integer> getReleativeUserByGouWuChe(int userid){
		GouWuChe gouwuche = new GouWuChe();
		gouwuche.setUser_id(userid);
		List<GouWuChe> myselfGouWuChes = (List<GouWuChe>) gouWuCheDao.listGouWuChes(gouwuche);
		List<GouWuChe> gouwucheReleative = new ArrayList<GouWuChe>();
		List<Integer> releativeUsers = new ArrayList<Integer>();
		for(GouWuChe gChe:myselfGouWuChes) {
			int goods_id = gChe.getGoods_id();
			gouwuche = new GouWuChe();
			gouwuche.setGoods_id(goods_id);
			gouwucheReleative = gouWuCheDao.listGouWuChes(gouwuche);
			for(GouWuChe otherGouWuChe:gouwucheReleative) {
				if(otherGouWuChe.getUser_id() != userid)
					releativeUsers.add(otherGouWuChe.getUser_id());
			}
		}		
		return releativeUsers.stream().distinct().collect(Collectors.toList());
	}
	//通过订单获取相关联的用户
	public List<Integer> getReleativeUserByDingdan(int userid){
		DingDan dingdan = new DingDan();
		dingdan.setUser_id(userid);
		List<DingDan> myselfDingDans = (List<DingDan>) dingDanDao.listDingDans(dingdan);
		List<DingDan> dingdanReleative = new ArrayList<DingDan>();
		List<Integer> releativeUsers = new ArrayList<Integer>();
		for(DingDan gChe:myselfDingDans) {
			int goods_id = gChe.getGoods_id();
			dingdan = new DingDan();
			dingdan.setGoods_id(goods_id);
			dingdanReleative = dingDanDao.listDingDans(dingdan);
			for(DingDan otherDingDan:dingdanReleative) {
				if(otherDingDan.getUser_id() != userid)
					releativeUsers.add(otherDingDan.getUser_id());
			}
		}		
		return releativeUsers.stream().distinct().collect(Collectors.toList());
	}

	//通过订单获取相关联的用户
	public List<Integer> getReleativeUserByPinglun(int userid){
		Pinglun pinglun = new Pinglun();
		pinglun.setUser_id(userid);
		List<Pinglun> myselfPingluns = (List<Pinglun>) pinglunDao.listPingluns(pinglun);
		List<Pinglun> pinglunReleative = new ArrayList<Pinglun>();
		List<Integer> releativeUsers = new ArrayList<Integer>();
		for(Pinglun gChe:myselfPingluns) {
			int goods_id = gChe.getGoods_id();
			pinglun = new Pinglun();
			pinglun.setGoods_id(goods_id);
			pinglunReleative = pinglunDao.listPingluns(pinglun);
			for(Pinglun otherPinglun:pinglunReleative) {
				if(otherPinglun.getUser_id() != userid)
					releativeUsers.add(otherPinglun.getUser_id());
			}
		}		
		return releativeUsers.stream().distinct().collect(Collectors.toList());
	}


	/**
	 * @Title: queryGoods
	 * @Description: 查询商品
	 * @return String
	 */
	@RequestMapping(value="page_queryGoods.action",method=RequestMethod.GET)
	public String queryGoods(Goods paramsGoods,Evaluate paramsEvaluate,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			//得到商品
			Goods goods = indexManager.queryGoods(paramsGoods);
			model.addAttribute("goods", goods);

			//查询评价集合
			paperUtil.setPagination(paramsEvaluate);
			int[] sum={0};
			List<Evaluate> evaluates = indexManager.listEvaluates(paramsEvaluate,sum); 
			model.addAttribute("evaluates", evaluates);
			paperUtil.setTotalCount(sum[0]);

			//添加浏览记录
			User userFront = (User)httpSession.getAttribute("userFront");
			if (userFront!=null) {
				Uview uview = new Uview();
				uview.setUser_id(userFront.getUser_id());
				uview.setGoods_id(goods.getGoods_id());
				uview = indexManager.queryUview(uview);
				if (uview==null) {
					uview = new Uview();
					uview.setUser_id(userFront.getUser_id());
					uview.setGoods_id(goods.getGoods_id());
					indexManager.addUview(uview);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "goodsDetail";
	}

	/**
	 * @Title: listCard
	 * @Description: 查询购物车
	 * @return String
	 */
	@RequestMapping(value="page_listCard.action")
	public String listCard(ModelMap model,HttpSession httpSession){
		try {
			//查询购物车
			List<OrdersDetail> ordersDetails = new ArrayList<OrdersDetail>();
			User user = (User)httpSession.getAttribute("userFront");
			if(user == null) {
				model.addAttribute("tip","登录超时请重新登录！");
				return "login";
			}
			GouWuChe gouwuche = new GouWuChe();
			gouwuche.setUser_id(user.getUser_id());
			List<GouWuChe> gouWuChes = gouWuCheDao.listGouWuChes(gouwuche);
			if(gouWuChes != null) {
				for(GouWuChe tempChe : gouWuChes) {
					OrdersDetail ordersDetail = new OrdersDetail();
					Goods goods = new Goods();
					goods.setGoods_id(tempChe.getGoods_id());
					Goods dbGoods = goodsDao.getGoods(goods);
					ordersDetail.setGoods_id(dbGoods.getGoods_id());
					ordersDetail.setGoods_name(dbGoods.getGoods_name());
					ordersDetail.setGoods_price(dbGoods.getGoods_price());
					ordersDetail.setGoods_discount(dbGoods.getGoods_discount());
					ordersDetail.setGoods_count(tempChe.getCount());
					ordersDetail.setGoods_money(dbGoods.getGoods_price()*tempChe.getCount());
					ordersDetails.add(ordersDetail);
				}
			}
			httpSession.removeAttribute("ordersDetails");
			httpSession.setAttribute("ordersDetails", ordersDetails);
			model.addAttribute("ordersDetails", ordersDetails);

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "card";
	}

	/**
	 * @Title: addCard
	 * @Description: 添加到购物车
	 * @return String
	 */
	@RequestMapping(value="page_addCard.action")
	public String addCard(OrdersDetail paramsOrdersDetail,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			//添加到购物车
			indexManager.addCard(paramsOrdersDetail, httpSession);
			//查询购物车
			List<OrdersDetail> ordersDetails = indexManager.listCard(httpSession);
			model.addAttribute("ordersDetails", ordersDetails);
			User user = (User)httpSession.getAttribute("userFront");
			if(user == null) {
				model.addAttribute("tip","登录超时请重新登录！");
				return "login";
			}
			GouWuChe gouWuChe = new GouWuChe();
			gouWuChe.setUser_id(user.getUser_id());
			gouWuChe.setGoods_id(paramsOrdersDetail.getGoods_id());
			GouWuChe dbGouWuChe = gouWuCheDao.getGouWuChe(gouWuChe);
			if(dbGouWuChe == null) {
				gouWuChe.setTime(new Date());
				gouWuChe.setCount(1);
				gouWuCheDao.addGouWuChe(gouWuChe);
			}else if(dbGouWuChe.getGoods_id() == paramsOrdersDetail.getGoods_id()){
				gouWuCheDao.delGouWuChe(dbGouWuChe);
				dbGouWuChe.setCount(dbGouWuChe.getCount()+1);
				gouWuCheDao.addGouWuChe(dbGouWuChe);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "card";
	}

	/**
	 * @Title: modifyCard
	 * @Description: 修改购物车
	 * @return String
	 */
	@RequestMapping(value="page_modifyCard.action")
	@ResponseBody
	public JSONData modifyCard(OrdersDetail paramsOrdersDetail,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		JSONData jsonData = new JSONData();
		try {
			//修改购物车
			indexManager.modifyCard(paramsOrdersDetail, httpSession);

		} catch (Exception e) {
			e.printStackTrace();
			jsonData.setErrorReason("修改数量失败！");
			return jsonData;
		}

		return jsonData;
	}

	/**
	 * @Title: delGoodsFromCard
	 * @Description: 从购物车删除
	 * @return String
	 */
	@RequestMapping(value="page_delGoodsFromCard.action")
	public String delGoodsFromCard(OrdersDetail paramsOrdersDetail,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			//从购物车删除
			indexManager.delGoodsFromCard(paramsOrdersDetail.getGoods_id(), httpSession);

			//查询购物车
			List<OrdersDetail> ordersDetails = indexManager.listCard(httpSession);
			model.addAttribute("ordersDetails", ordersDetails);
			User user = (User)httpSession.getAttribute("userFront");
			if(user == null) {
				model.addAttribute("tip","登录超时请重新登录！");
				return "login";
			}
			GouWuChe gouWuChe = new GouWuChe();
			gouWuChe.setUser_id(user.getUser_id());
			gouWuChe.setGoods_id(paramsOrdersDetail.getGoods_id());
			GouWuChe dbGouWuChe = gouWuCheDao.getGouWuChe(gouWuChe);
			if(dbGouWuChe.getCount()>=0) {
				gouWuCheDao.delGouWuChe(gouWuChe);
				dbGouWuChe.setCount(dbGouWuChe.getCount() - 1);
				if(dbGouWuChe.getCount() - 1 > 0 )
					gouWuCheDao.addGouWuChe(dbGouWuChe);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "card";
	}

	/**
	 * @Title: clearCard
	 * @Description: 清空购物车
	 * @return String
	 */
	@RequestMapping(value="page_clearCard.action")
	public String clearCard(OrdersDetail paramsOrdersDetail,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			//清空购物车
			indexManager.clearCard(httpSession);
			//查询购物车
			List<OrdersDetail> ordersDetails = indexManager.listCard(httpSession);
			model.addAttribute("ordersDetails", null);
			User user = (User)httpSession.getAttribute("userFront");
			if(user == null) {
				model.addAttribute("tip","登录超时请重新登录！");
				return "login";
			}
			GouWuChe gouWuChe = new GouWuChe();
			gouWuChe.setUser_id(user.getUser_id());
			gouWuCheDao.delGouWuChe(gouWuChe);
			GouWuChe gouwuche = new GouWuChe();
			gouwuche.setUser_id(user.getUser_id());
			List<GouWuChe> gouWuChes = gouWuCheDao.listGouWuChes(gouwuche);
			if(gouWuChes != null) {
				for(GouWuChe tempChe : gouWuChes) {
					OrdersDetail ordersDetail = new OrdersDetail();
					Goods goods = new Goods();
					goods.setGoods_id(tempChe.getGoods_id());
					Goods dbGoods = goodsDao.getGoods(goods);
					ordersDetail.setGoods_id(dbGoods.getGoods_id());
					ordersDetail.setGoods_name(dbGoods.getGoods_name());
					ordersDetail.setGoods_price(dbGoods.getGoods_price());
					ordersDetail.setGoods_discount(dbGoods.getGoods_discount());
					ordersDetail.setGoods_count(tempChe.getCount());
					ordersDetails.add(ordersDetail);
				}
			}
			httpSession.removeAttribute("ordersDetails");
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "card";
	}

	/**
	 * @Title: addOrdersShow
	 * @Description: 新增订单页面
	 * @return String
	 */
	@RequestMapping(value="page_addOrdersShow.action")
	public String addOrdersShow(OrdersDetail paramsOrdersDetail,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			//查询购物车
			List<OrdersDetail> ordersDetails = indexManager.listCard(httpSession);
			model.addAttribute("ordersDetails", ordersDetails);

			//查询订单总额
			double orders_money=0;
			for (int i = 0; i < ordersDetails.size(); i++) {
				OrdersDetail ordersDetail = ordersDetails.get(i);
				orders_money+=ordersDetail.getGoods_money();//累计总金额
			}
			model.addAttribute("orders_money", Math.round(orders_money*10)/10.0);

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "ordersAdd";
	}

	/**
	 * @Title: addOrders
	 * @Description: 新增订单
	 * @return String
	 */
	@RequestMapping(value="page_addOrders.action")
	@ResponseBody
	public JSONData addOrders(Orders paramsOrders,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		JSONData jsonData = new JSONData();
		try {
			//新增订单
			indexManager.addOrders(paramsOrders,httpSession);
			User user = (User)httpSession.getAttribute("userFront");
			if(user == null) {
				jsonData.setErrorReason("登录超时请重新登录！");
				return jsonData;
			}
			GouWuChe gouWuChe = new GouWuChe();
			gouWuChe.setUser_id(user.getUser_id());
			gouWuCheDao.delGouWuChe(gouWuChe);
			Orders orders = new Orders();
			orders.setOrders_no(paramsOrders.getOrders_no());
			List<OrdersDetail> listsDetails = ordersDetailDao.listOrdersDetailsByOrder(orders);
			if(listsDetails != null) {
				for(OrdersDetail ordersDetail:listsDetails) {
					DingDan dingdan = new DingDan();
					dingdan.setGoods_id(ordersDetail.getGoods_id());
					dingdan.setTime(new Date());
					dingdan.setUser_id(user.getUser_id());
					if(dingDanDao.getDingDan(dingdan) == null)
						dingDanDao.addDingDan(dingdan);
				}
			}

			System.out.println(listsDetails.size());
		} catch (Exception e) {
			e.printStackTrace();
			jsonData.setErrorReason("提交订单失败");
			return jsonData;
		}

		return jsonData;
	}

	/**
	 * @Title: listSblogs
	 * @Description: 留言信息
	 * @return String
	 */
	@RequestMapping(value="page_listSblogs.action")
	public String listSblogs(Sblog paramsSblog,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			//查询留言信息集合
			if (paramsSblog==null) {
				paramsSblog = new Sblog();
			}
			if (paperUtil==null) {
				paperUtil = new PaperUtil();
			}
			//设置分页信息
			paperUtil.setPagination(paramsSblog);
			int[] sum={0};
			List<Sblog> sblogs = indexManager.listSblogs(paramsSblog,sum); 
			model.addAttribute("sblogs", sblogs);
			paperUtil.setTotalCount(sum[0]);

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "sblog";
	}

	/**
	 * @Title: addSblog
	 * @Description: 新增留言
	 * @return String
	 */
	@RequestMapping(value="page_addSblog.action")
	@ResponseBody
	public JSONData addSblog(Sblog paramsSblog,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		JSONData jsonData = new JSONData();
		try {
			//新增留言
			indexManager.addSblog(paramsSblog);

		} catch (Exception e) {
			e.printStackTrace();
			jsonData.setErrorReason("新增留言失败！");
			return jsonData;
		}

		return jsonData;
	}

	/**
	 * @Title: saveAdmin
	 * @Description: 保存修改个人信息
	 * @return String
	 */
	@RequestMapping(value="page_saveUserFront.action",method=RequestMethod.POST)
	@ResponseBody
	public JSONData saveUserFront(User paramsUser,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		JSONData jsonData = new JSONData();
		try {
			//保存修改个人信息
			indexManager.updateUser(paramsUser);
			//更新session
			User admin = new User();
			admin.setUser_id(paramsUser.getUser_id());
			admin = indexManager.queryUser(admin);
			httpSession.setAttribute("userFront", admin);
		} catch (Exception e) {
			e.printStackTrace();
			jsonData.setErrorReason("后台服务器异常");
			return jsonData;
		}
		return jsonData;
	}

	/**
	 * @Title: saveUserFrontPass
	 * @Description: 保存修改个人密码
	 * @return String
	 */
	@RequestMapping(value="page_saveUserFrontPass.action",method=RequestMethod.POST)
	@ResponseBody
	public JSONData saveUserFrontPass(User paramsUser,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		JSONData jsonData = new JSONData();
		try {
			//保存修改个人密码
			User userFront = (User)httpSession.getAttribute("userFront");
			if (!userFront.getUser_pass().equals(Md5.makeMd5(paramsUser.getUser_passOld()))) {
				jsonData.setErrorReason("原密码不正确");
				return jsonData;
			}
			indexManager.updateUser(paramsUser);
			//更新session
			if (userFront!=null) {
				userFront.setUser_pass(paramsUser.getUser_pass());
				httpSession.setAttribute("userFront", userFront);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonData.setErrorReason("后台服务器异常");
			return jsonData;
		}
		return jsonData;
	}

	/**
	 * @Title: listMyOrderss
	 * @Description: 查询我的商品订单
	 * @return String
	 */
	@RequestMapping(value="page_listMyOrderss.action")
	public String listMyOrderss(Orders paramsOrders,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			if (paramsOrders==null) {
				paramsOrders = new Orders();
			}
			//获取用户,用户只能查询自己的订单
			User userFront = (User)httpSession.getAttribute("userFront");
			paramsOrders.setUser_id(userFront.getUser_id());
			//设置分页信息
			paperUtil.setPagination(paramsOrders);
			//总的条数
			int[] sum={0};
			//查询商品预约列表
			List<Orders> orderss = indexManager.listOrderss(paramsOrders,sum); 

			model.addAttribute("orderss", orderss);
			paperUtil.setTotalCount(sum[0]);

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "ordersShow";
	}

	/**
	 * @Title: listMyOrdersDetails
	 * @Description: 查询我的订单明细
	 * @return String
	 */
	@RequestMapping(value="page_listMyOrdersDetails.action")
	public String listMyOrdersDetails(OrdersDetail paramsOrdersDetail,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			if (paramsOrdersDetail==null) {
				paramsOrdersDetail = new OrdersDetail();
			}
			//设置分页信息
			paramsOrdersDetail.setStart(-1);
			//查询订单明细
			List<OrdersDetail> ordersDetails = indexManager.listOrdersDetails(paramsOrdersDetail,null); 
			model.addAttribute("ordersDetails", ordersDetails);

			//订单信息
			model.addAttribute("orders_no", paramsOrdersDetail.getOrders_no());
			if (ordersDetails!=null && ordersDetails.size()>0) {
				model.addAttribute("orders_money", ordersDetails.get(0).getOrders_money());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "ordersDetailShow";
	}

	/**
	 * @Title: finishOrders
	 * @Description: 确认收货
	 * @return String
	 */
	@RequestMapping(value="page_finishOrders.action")
	@ResponseBody
	public JSONData finishOrders(Orders paramsOrders,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		JSONData jsonData = new JSONData();
		try {
			//确认收货
			indexManager.finishOrders(paramsOrders);

		} catch (Exception e) {
			e.printStackTrace();
			jsonData.setErrorReason("确认收货失败！");
			return jsonData;
		}

		return jsonData;
	}

	/**
	 * @Title: addEvaluateShow
	 * @Description: 评价商品界面
	 * @return String
	 */
	@RequestMapping(value="page_addEvaluateShow.action")
	public String addEvaluateShow(Orders paramsOrders,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			//查询订单
			Orders orders = indexManager.queryOrders(paramsOrders);
			model.addAttribute("orders", orders);

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "evaluateAdd";
	}

	/**
	 * @Title: addEvaluate
	 * @Description: 评价商品
	 * @return String
	 */
	@RequestMapping(value="page_addEvaluate.action")
	@ResponseBody
	public JSONData addEvaluate(Evaluate paramsEvaluate,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		JSONData jsonData = new JSONData();
		try {
			//新增商品评价
			indexManager.addEvaluate(paramsEvaluate);
			User user = (User)httpSession.getAttribute("userFront");
			if(user == null) {
				jsonData.setErrorReason("登录超时请重新登录！");
				return jsonData;
			}
			Pinglun pinglunCron = new Pinglun();
			Orders ordersCron = new Orders();
			ordersCron.setOrders_no(paramsEvaluate.getOrders_no());
			List<OrdersDetail> ordersDetails = ordersDetailDao.listOrdersDetailsByOrder(ordersCron);
			for(OrdersDetail ordersDetail:ordersDetails) {
				pinglunCron = new Pinglun();
				pinglunCron.setGoods_id(ordersDetail.getGoods_id());
				pinglunCron.setUser_id(paramsEvaluate.getUser_id());
				pinglunCron.setTime(new Date());
				pinglunCron.setLevel(paramsEvaluate.getEvaluate_level());
				Pinglun pinglun = pinglunDao.getPinglun(pinglunCron);
				if(pinglun == null) {
					pinglunDao.addPinglun(pinglunCron);
				}
				else if (pinglun.getLevel() > paramsEvaluate.getEvaluate_level()) {
					pinglunCron.setTime(new Date());
					pinglunDao.delPinglun(pinglun);
					pinglunCron.setLevel(paramsEvaluate.getEvaluate_level());
					pinglunDao.addPinglun(pinglunCron);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonData.setErrorReason("评价商品失败！");
			return jsonData;
		}

		return jsonData;
	}
}
