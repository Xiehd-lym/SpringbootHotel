package com.soft.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.soft.common.util.DateUtil;
import com.soft.demo.common.util.Md5;
import com.soft.demo.common.util.PaperUtil;
import com.soft.demo.dao.ZixunMapper;
import com.soft.demo.domain.Goods;
import com.soft.demo.domain.GoodsType;
import com.soft.demo.domain.Manager;
import com.soft.demo.domain.Orders;
import com.soft.demo.domain.OrdersDetail;
import com.soft.demo.domain.Sblog;
import com.soft.demo.domain.SblogReply;
import com.soft.demo.domain.User;
import com.soft.demo.domain.Zixun;
import com.soft.demo.domain.ZixunExample;
import com.soft.demo.domain.ZixunExample.Criteria;
import com.soft.demo.service.AdminManager;

@Controller
public class AdminAction{

	@Autowired
	AdminManager adminManager;
	@Autowired
	ZixunMapper zixunMapper;
	public AdminManager getAdminManager() {
		return adminManager;
	}
	public void setAdminManager(AdminManager adminManager) {
		this.adminManager = adminManager;
	}
	
	/**
	 * @Title: admin
	 * @Description: 首页
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value= { "admin", "admin/" },method=RequestMethod.GET)
	public void admin(Manager paramsManager,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession) throws Exception{
		response.sendRedirect(request.getContextPath() + "admin/index.jsp");
	}

	
	/**
	 * @Title: saveAdmin
	 * @Description: 保存修改个人信息
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_saveAdmin.action",method=RequestMethod.POST)
	public String saveAdmin(Manager paramsManager,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			//验证用户会话是否失效
			if (!validateAdmin(httpSession)) {
				return "loginTip";
			}
			 //保存修改个人信息
			adminManager.updateManager(paramsManager);
			//更新session
			Manager admin = new Manager();
			admin.setManager_id(paramsManager.getManager_id());
			admin = adminManager.queryManager(admin);
			httpSession.setAttribute("admin", admin);

			setSuccessTip("编辑成功", "modifyInfo.jsp", model);
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("编辑异常", "modifyInfo.jsp", model);
		}
		return "infoTip";
	}
	
	/**
	 * @Title: saveAdminPass
	 * @Description: 保存修改个人密码
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_saveAdminPass.action",method=RequestMethod.POST)
	public String saveAdminPass(Manager paramsManager,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			//验证用户会话是否失效
			if (!validateAdmin(httpSession)) {
				return "loginTip";
			}
			 //保存修改个人密码
			Manager admin = (Manager)httpSession.getAttribute("admin");
			if (!admin.getManager_pass().equals(Md5.makeMd5(paramsManager.getManager_passOld()))) {
				setErrorTip("修改异常，原密码不正确", "modifyPwd.jsp", model);
				return "infoTip";
			}
			adminManager.updateManager(paramsManager);
			//更新session
			if (admin!=null) {
				admin.setManager_pass(paramsManager.getManager_pass());
				httpSession.setAttribute("admin", admin);
			}

			setSuccessTip("修改成功", "modifyPwd.jsp", model);
		} catch (Exception e) {
			setErrorTip("修改异常", "modifyPwd.jsp", model);
		}
		return "infoTip";
	}
	
	/**
	 * @Title: listUsers
	 * @Description: 查询注册用户
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_listUsers.action")
	public String listUsers(User paramsUser,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			if (paramsUser==null) {
				paramsUser = new User();
			}
			if (paperUtil==null) {
				paperUtil = new PaperUtil();
			}
			//设置分页信息
			paperUtil.setPagination(paramsUser);
			//总的条数
			int[] sum={0};
			//查询注册用户列表
			List<User> users = adminManager.listUsers(paramsUser,sum); 
			model.addAttribute("users", users);
			model.addAttribute("paramsUser", paramsUser);
			paperUtil.setTotalCount(sum[0]);

		} catch (Exception e) {
			setErrorTip("查询注册用户异常", "main.jsp", model);
			return "infoTip";
		}
		
		return "userShow";
	}
	
	/**
	 * @Title: addUserShow
	 * @Description: 显示添加注册用户页面
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_addUserShow.action",method=RequestMethod.GET)
	public String addUserShow(ModelMap model){
		return "userEdit";
	}
	
	/**
	 * @Title: addUser
	 * @Description: 添加注册用户
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_addUser.action",method=RequestMethod.POST)
	public String addUser(User paramsUser,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			//检查注册用户是否存在
			User user = new User();
			user.setUser_name(paramsUser.getUser_name());
			user = adminManager.queryUser(user);
			if (user!=null) {
				model.addAttribute("tip","失败，该用户名已经存在！");
				model.addAttribute("user", paramsUser);
				
				return "userEdit";
			}
			 //添加注册用户
			paramsUser.setUser_flag(1);
			paramsUser.setReg_date(DateUtil.getCurDateTime());
			adminManager.addUser(paramsUser);
			
			setSuccessTip("添加成功", "Admin_listUsers.action", model);
		} catch (Exception e) {
			setErrorTip("添加注册用户异常", "Admin_listUsers.action", model);
		}
		
		return "infoTip";
	}
	
	 
	/**
	 * @Title: editUser
	 * @Description: 编辑注册用户
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_editUser.action",method=RequestMethod.GET)
	public String editUser(User paramsUser,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			 //得到注册用户
			User user = adminManager.queryUser(paramsUser);
			model.addAttribute("user", user);
			
		} catch (Exception e) {
			setErrorTip("查询注册用户异常", "Admin_listUsers.action", model);
			return "infoTip";
		}
		
		return "userEdit";
	}
	
	/**
	 * @Title: saveUser
	 * @Description: 保存编辑注册用户
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_saveUser.action",method=RequestMethod.POST)
	public String saveUser(User paramsUser,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			 //保存编辑注册用户
			adminManager.updateUser(paramsUser);
			
			setSuccessTip("编辑成功", "Admin_listUsers.action", model);
		} catch (Exception e) {
			setErrorTip("编辑注册用户异常", "Admin_listUsers.action", model);
			return "infoTip";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delUsers
	 * @Description: 删除注册用户
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_delUsers.action")
	public String delUsers(User paramsUser,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			 //删除注册用户
			adminManager.delUsers(paramsUser);
			
			setSuccessTip("删除注册用户成功", "Admin_listUsers.action", model);
		} catch (Exception e) {
			setErrorTip("删除注册用户异常", "Admin_listUsers.action", model);
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listSblogs
	 * @Description: 查询留言信息
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_listSblogs.action")
	public String listSblogs(Sblog paramsSblog,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			if (paramsSblog==null) {
				paramsSblog = new Sblog();
			}
			//设置分页信息
			if (paperUtil==null) {
				paperUtil = new PaperUtil();
			}
			paperUtil.setPagination(paramsSblog);
			int[] sum={0};
			List<Sblog> sblogs = adminManager.listSblogs(paramsSblog,sum); 
			model.addAttribute("sblogs", sblogs);
			model.addAttribute("paramsSblog", paramsSblog);
			paperUtil.setTotalCount(sum[0]);
			
		} catch (Exception e) {
			setErrorTip("查询留言信息异常", "main.jsp", model);
			return "infoTip";
		}
		
		return "sblogShow";
	}
	@RequestMapping(value="admin/Admin_addzixun.action")
	public String Admin_addzixun( PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		return "zixunadd";
	}
	@RequestMapping(value="admin/Admin_editzixun.action",method=RequestMethod.GET)
	public String Admin_editzixun(Integer id,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		ZixunExample zixunExample = new ZixunExample();
		Criteria criteria = zixunExample.createCriteria();
		criteria.andIdEqualTo(id);
		List<Zixun> zixuns = zixunMapper.selectByExample(zixunExample);
		model.addAttribute("zixun", zixuns.get(0));
		return "zixunedit";
	}
	@RequestMapping(value="admin/Admin_delzixun.action",method=RequestMethod.POST)
	public String Admin_delzixun(Zixun zixun,PaperUtil paperUtil,Integer id,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		ZixunExample zixunExample = new ZixunExample();
		Criteria criteria = zixunExample.createCriteria();
		criteria.andIdEqualTo(id);
		zixunMapper.deleteByExample(zixunExample);
		try {
			if (zixun==null) {
				zixun = new Zixun();
			}
			
			//设置分页信息
			if (paperUtil==null) {
				paperUtil = new PaperUtil();
			}
			paperUtil.setPagination(zixun);
 
			//查询商品类型列表
			List<Zixun> zixuns = zixunMapper.selectByExample(new ZixunExample()); 
			
			model.addAttribute("zixuns", zixuns);
//			model.addAttribute("zixun", zixun);
			paperUtil.setTotalCount(zixuns.size());
			
		} catch (Exception e) {
			setErrorTip("查询商品资讯异常", "main.jsp", model);
			return "infoTip";
		}
		
		return "zixunlist";
	}
	@RequestMapping(value="admin/Admin_listzixun.action")
	public String Admin_listzixun(Zixun zixun,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			if (zixun==null) {
				zixun = new Zixun();
			}
			
			//设置分页信息
			if (paperUtil==null) {
				paperUtil = new PaperUtil();
			}
			paperUtil.setPagination(zixun);
			ZixunExample zixunExample = new ZixunExample();
			Criteria criteria = zixunExample.createCriteria();
			if(zixun!=null&&zixun.getTitle()!=null&&!zixun.getTitle().equals("")) {
				criteria.andTitleLike(zixun.getTitle());
			}
		 
			
			//查询商品类型列表
			List<Zixun> zixuns = zixunMapper.selectByExample(zixunExample); 
			
			model.addAttribute("zixuns", zixuns);
//			model.addAttribute("zixun", zixun);
			paperUtil.setTotalCount(zixuns.size());
			
		} catch (Exception e) {
			setErrorTip("查询商品类型异常", "main.jsp", model);
			return "infoTip";
		}
		
 		return "zixunlist";
	}
 
	@RequestMapping(value="admin/addzixun.action")
	public String addzixun(Zixun zixun,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		zixun.setTime(new Date());
		zixunMapper.insert(zixun);
		try {
			if (zixun==null) {
				zixun = new Zixun();
			}
			
			//设置分页信息
			if (paperUtil==null) {
				paperUtil = new PaperUtil();
			}
			paperUtil.setPagination(zixun);
			//总的条数
			int[] sum={0};
			//查询商品类型列表
			List<Zixun> zixuns = zixunMapper.selectByExample(new ZixunExample()); 
			
			model.addAttribute("zixuns", zixuns);
//			model.addAttribute("zixun", zixun);
			paperUtil.setTotalCount(zixuns.size());
			
		} catch (Exception e) {
			setErrorTip("查询商品类型异常", "main.jsp", model);
			return "infoTip";
		}
		model.addAttribute("msg", "<script>alert('资讯添加成功!')</script>");
		return "zixunlist";
	}
	@RequestMapping(value="admin/updatezixun.action")
	public String updatezixun(Zixun zixun,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		zixun.setTime(new Date());
		ZixunExample zixunExample = new ZixunExample();
		Criteria criteria = zixunExample.createCriteria();
		criteria.andIdEqualTo(zixun.getId());
		zixunMapper.updateByExampleSelective(zixun, zixunExample);
		try {
			if (zixun==null) {
				zixun = new Zixun();
			}
			
			//设置分页信息
			if (paperUtil==null) {
				paperUtil = new PaperUtil();
			}
			paperUtil.setPagination(zixun);
			//总的条数
			int[] sum={0};
			//查询商品类型列表
			List<Zixun> zixuns = zixunMapper.selectByExample(new ZixunExample()); 
			
			model.addAttribute("zixuns", zixuns);
//			model.addAttribute("zixun", zixun);
			paperUtil.setTotalCount(zixuns.size());
			
		} catch (Exception e) {
			setErrorTip("查询商品类型异常", "main.jsp", model);
			return "infoTip";
		}
		model.addAttribute("msg", "<script>alert('资讯编辑成功!')</script>");
		return "zixunlist";
	}
 	/**
	 * @Title:
	 * @Title: querySblog
	 * @Description: 编辑留言信息
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_querySblog.action",method=RequestMethod.GET)
	public String querySblog(Sblog paramsSblog,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			 //得到留言信息
			Sblog sblog = adminManager.querySblog(paramsSblog);
			model.addAttribute("sblog", sblog);
			
		} catch (Exception e) {
			setErrorTip("查询留言异常", "Admin_listSblogs.action", model);
			return "infoTip";
		}
		
		return "sblogDetail";
	}
	
	/**
	 * @Title: delSblogs
	 * @Description: 删除留言信息
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_delSblogs.action")
	public String delSblogs(Sblog paramsSblog,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			 //删除留言信息
			adminManager.delSblogs(paramsSblog);

			setSuccessTip("删除留言信息成功", "Admin_listSblogs.action", model);
		} catch (Exception e) {
			setErrorTip("删除留言信息异常", "Admin_listSblogs.action", model);
		}
		return "infoTip";
	}
	
	/**
	 * @Title:
	 * @Title: replySblog
	 * @Description: 回复留言信息
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_replySblog.action",method=RequestMethod.GET)
	public String replySblog(Sblog paramsSblog,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			 //得到留言信息
			Sblog sblog = adminManager.querySblog(paramsSblog);
			model.addAttribute("sblog", sblog);
			
		} catch (Exception e) {
			setErrorTip("查询留言异常", "Admin_listSblogs.action", model);
			return "infoTip";
		}
		
		return "sblogReply";
	}
	
	/**
	 * @Title: saveSblogReply
	 * @Description: 回复留言信息
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_saveSblogReply.action")
	public String saveSblogReply(SblogReply paramsSblogReply,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			 //回复留言信息
			adminManager.addSblogReply(paramsSblogReply);;

			setSuccessTip("回复留言成功", "Admin_listSblogs.action", model);
		} catch (Exception e) {
			setErrorTip("回复留言异常", "Admin_listSblogs.action", model);
		}
		return "infoTip";
	}
	
	/**
	 * @Title: listGoodsTypes
	 * @Description: 查询商品类型
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_listGoodsTypes.action")
	public String listGoodsTypes(GoodsType paramsGoodsType,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			if (paramsGoodsType==null) {
				paramsGoodsType = new GoodsType();
			}
			
			//设置分页信息
			if (paperUtil==null) {
				paperUtil = new PaperUtil();
			}
			paperUtil.setPagination(paramsGoodsType);
			//总的条数
			int[] sum={0};
			//查询商品类型列表
			List<GoodsType> goodsTypes = adminManager.listGoodsTypes(paramsGoodsType,sum); 
			
			model.addAttribute("goodsTypes", goodsTypes);
			model.addAttribute("paramsGoodsType", paramsGoodsType);
			paperUtil.setTotalCount(sum[0]);
			
		} catch (Exception e) {
			setErrorTip("查询商品类型异常", "main.jsp", model);
			return "infoTip";
		}
		
		return "goodsTypeShow";
	}
	
	/**
	 * @Title: addGoodsTypeShow
	 * @Description: 显示添加商品类型页面
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_addGoodsTypeShow.action",method=RequestMethod.GET)
	public String addGoodsTypeShow(){
		return "goodsTypeEdit";
	}
	
	/**
	 * @Title: addGoodsType
	 * @Description: 添加商品类型
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_addGoodsType.action",method=RequestMethod.POST)
	public String addGoodsType(GoodsType paramsGoodsType,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			//检查商品类型是否存在
			GoodsType goodsType = new GoodsType();
			goodsType.setGoods_type_name(paramsGoodsType.getGoods_type_name());
			goodsType = adminManager.queryGoodsType(goodsType);
			if (goodsType!=null) {
				String tip="失败，该商品类型已经存在！";
				model.addAttribute("tip", tip);
				model.addAttribute("goodsType", paramsGoodsType);
				
				return "goodsTypeEdit";
			}
			
			 //添加商品类型
			adminManager.addGoodsType(paramsGoodsType);
			
			setSuccessTip("添加成功", "Admin_listGoodsTypes.action",model);
		} catch (Exception e) {
			setErrorTip("添加商品类型异常", "Admin_listGoodsTypes.action", model);
		}
		
		return "infoTip";
	}
	
	 
	/**
	 * @Title: editGoodsType
	 * @Description: 编辑商品类型
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_editGoodsType.action",method=RequestMethod.GET)
	public String editGoodsType(GoodsType paramsGoodsType,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			 //得到商品类型
			GoodsType goodsType = adminManager.queryGoodsType(paramsGoodsType);
			model.addAttribute("goodsType", goodsType);
			
		} catch (Exception e) {
			setErrorTip("查询商品类型异常", "Admin_listGoodsTypes.action", model);
			return "infoTip";
		}
		
		return "goodsTypeEdit";
	}
	
	/**
	 * @Title: saveGoodsType
	 * @Description: 保存编辑商品类型
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_saveGoodsType.action",method=RequestMethod.POST)
	public String saveGoodsType(GoodsType paramsGoodsType,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			//检查商品类型是否存在
			GoodsType goodsType = new GoodsType();
			goodsType.setGoods_type_name(paramsGoodsType.getGoods_type_name());
			goodsType = adminManager.queryGoodsType(goodsType);
			if (goodsType!=null && goodsType.getGoods_type_id()!=paramsGoodsType.getGoods_type_id()) {
				String tip="失败，该商品类型已经存在！";
				model.addAttribute("tip", tip);
				model.addAttribute("goodsType", paramsGoodsType);
				
				return "goodsTypeEdit";
			}
			
			 //保存编辑商品类型
			adminManager.updateGoodsType(paramsGoodsType);
			
			setSuccessTip("编辑成功", "Admin_listGoodsTypes.action",model);
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("编辑失败", "Admin_listGoodsTypes.action",model);
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delGoodsTypes
	 * @Description: 删除商品类型
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_delGoodsTypes.action")
	public String delGoodsTypes(GoodsType paramsGoodsType,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			 //删除商品类型
			adminManager.delGoodsTypes(paramsGoodsType);
			
			setSuccessTip("删除商品类型成功", "Admin_listGoodsTypes.action",model);
		} catch (Exception e) {
			setErrorTip("删除商品类型异常", "Admin_listGoodsTypes.action", model);
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listGoodss
	 * @Description: 查询商品
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_listGoodss.action")
	public String listGoodss(Goods paramsGoods,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			if (paramsGoods==null) {
				paramsGoods = new Goods();
			}
			
			//设置分页信息
			if (paperUtil==null) {
				paperUtil = new PaperUtil();
			}
			paperUtil.setPagination(paramsGoods);
			//总的条数
			int[] sum={0};
			//查询商品列表
			List<Goods> goodss = adminManager.listGoodss(paramsGoods,sum); 
			
			model.addAttribute("goodss", goodss);
			model.addAttribute("paramsGoods", paramsGoods);
			paperUtil.setTotalCount(sum[0]);
			
			//查询商品类型
			GoodsType goodsType = new GoodsType();
			goodsType.setStart(-1);
			List<GoodsType> goodsTypes = adminManager.listGoodsTypes(goodsType, null);
			if (goodsTypes==null) {
				goodsTypes = new ArrayList<GoodsType>();
			}
			model.addAttribute("goodsTypes", goodsTypes);
			
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("查询商品异常", "main.jsp", model);
			return "infoTip";
		}
		
		return "goodsShow";
	}
	
	/**
	 * @Title: queryGoods
	 * @Description: 编辑商品
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_queryGoods.action",method=RequestMethod.GET)
	public String queryGoods(Goods paramsGoods,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			 //得到商品
			Goods goods = adminManager.queryGoods(paramsGoods);
			model.addAttribute("goods", goods);
			
		} catch (Exception e) {
			setErrorTip("查询商品异常", "Admin_listGoodss.action", model);
			return "infoTip";
		}
		
		return "goodsDetail";
	}
	
	/**
	 * @Title: addGoodsShow
	 * @Description: 显示添加商品页面
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_addGoodsShow.action",method=RequestMethod.GET)
	public String addGoodsShow(ModelMap model){
		//查询商品类型
		GoodsType goodsType = new GoodsType();
		goodsType.setStart(-1);
		List<GoodsType> goodsTypes = adminManager.listGoodsTypes(goodsType, null);
		if (goodsTypes==null) {
			goodsTypes = new ArrayList<GoodsType>();
		}
		model.addAttribute("goodsTypes", goodsTypes);
		
		return "goodsEdit";
	}
	
	/**
	 * @Title: addGoods
	 * @Description: 添加商品
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_addGoods.action",method=RequestMethod.POST)
	public String addGoods(Goods paramsGoods,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			//检查商品是否存在
			Goods goods = new Goods();
			goods.setGoods_no(paramsGoods.getGoods_no());
			goods = adminManager.queryGoods(goods);
			if (goods!=null) {
				String tip="失败，该商品号已经存在！";
				model.addAttribute("tip", tip);
				model.addAttribute("goods", paramsGoods);
				
				//查询商品类型
				GoodsType goodsType = new GoodsType();
				goodsType.setStart(-1);
				List<GoodsType> goodsTypes = adminManager.listGoodsTypes(goodsType, null);
				if (goodsTypes==null) {
					goodsTypes = new ArrayList<GoodsType>();
				}
				model.addAttribute("goodsTypes", goodsTypes);
				
				return "goodsEdit";
			}
			
			 //添加商品
			adminManager.addGoods(paramsGoods);
			
			setSuccessTip("添加成功", "Admin_listGoodss.action",model);
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("添加商品异常", "Admin_listGoodss.action", model);
		}
		
		return "infoTip";
	}
	
	 
	/**
	 * @Title: editGoods
	 * @Description: 编辑商品
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_editGoods.action",method=RequestMethod.GET)
	public String editGoods(Goods paramsGoods,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			 //得到商品
			Goods goods = adminManager.queryGoods(paramsGoods);
			model.addAttribute("goods", goods);
			
			//查询商品类型
			GoodsType goodsType = new GoodsType();
			goodsType.setStart(-1);
			List<GoodsType> goodsTypes = adminManager.listGoodsTypes(goodsType, null);
			if (goodsTypes==null) {
				goodsTypes = new ArrayList<GoodsType>();
			}
			model.addAttribute("goodsTypes", goodsTypes);
			
		} catch (Exception e) {
			setErrorTip("查询商品异常", "Admin_listGoodss.action", model);
			return "infoTip";
		}
		
		return "goodsEdit";
	}
	
	/**
	 * @Title: saveGoods
	 * @Description: 保存编辑商品
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_saveGoods.action",method=RequestMethod.POST)
	public String saveGoods(Goods paramsGoods,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			 //保存编辑商品
			adminManager.updateGoods(paramsGoods);
			
			setSuccessTip("编辑成功", "Admin_listGoodss.action",model);
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("编辑失败", "Admin_listGoodss.action",model);
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delGoodss
	 * @Description: 删除商品
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_delGoodss.action")
	public String delGoodss(Goods paramsGoods,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			 //删除商品
			adminManager.delGoodss(paramsGoods);
			
			setSuccessTip("删除商品成功", "Admin_listGoodss.action",model);
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("删除商品异常", "Admin_listGoodss.action", model);
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listOrderss
	 * @Description: 查询商品订单
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_listOrderss.action")
	public String listOrderss(Orders paramsOrders,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			if (paramsOrders==null) {
				paramsOrders = new Orders();
			}
			//设置分页信息
			paperUtil.setPagination(paramsOrders);
			//总的条数
			int[] sum={0};
			//查询商品订单列表
			List<Orders> orderss = adminManager.listOrderss(paramsOrders,sum); 
			model.addAttribute("orderss", orderss);
			paperUtil.setTotalCount(sum[0]);
			
		} catch (Exception e) {
			setErrorTip("查询商品订单异常", "main.jsp",model);
			return "infoTip";
		}
		
		return "ordersShow";
	}
	
	/**
	 * @Title: sendOrders
	 * @Description: 订单发货
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_sendOrders.action",method=RequestMethod.GET)
	public String sendOrders(Orders paramsOrders,
			ModelMap model){
		try {
			 //订单发货
			adminManager.sendOrders(paramsOrders);
			
			setSuccessTip("订单发货成功", "Admin_listOrderss.action",model);
		} catch (Exception e) {
			setErrorTip("订单发货异常", "Admin_listOrderss.action",model);
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delOrderss
	 * @Description: 删除商品订单
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_delOrderss.action")
	public String delOrderss(Orders paramsOrders,
			ModelMap model){
		try {
			 //删除商品订单
			adminManager.delOrderss(paramsOrders);
			
			setSuccessTip("删除商品订单成功", "Admin_listOrderss.action",model);
		} catch (Exception e) {
			setErrorTip("删除商品订单异常", "Admin_listOrderss.action",model);
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listOrdersDetails
	 * @Description: 查询商品订单明细
	 * @return String
	 */
	@RequestMapping(value="admin/Admin_listOrdersDetails.action")
	public String listOrdersDetails(OrdersDetail paramsOrdersDetail,PaperUtil paperUtil,
			ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		try {
			if (paramsOrdersDetail==null) {
				paramsOrdersDetail = new OrdersDetail();
			}
			//设置分页信息
			paperUtil.setPagination(paramsOrdersDetail);
			//总的条数
			int[] sum={0};
			//查询商品订单明细
			List<OrdersDetail> ordersDetails = adminManager.listOrdersDetails(paramsOrdersDetail,sum); 
			
			model.addAttribute("ordersDetails", ordersDetails);
			paperUtil.setTotalCount(sum[0]);
			
			model.addAttribute("orders_no", paramsOrdersDetail.getOrders_no());
			if (ordersDetails!=null && ordersDetails.size()>0) {
				model.addAttribute("orders_money", ordersDetails.get(0).getOrders_money());
			}
			
			
		} catch (Exception e) {
			setErrorTip("查询商品订单明细异常", "main.jsp", model);
			return "infoTip";
		}
		
		return "ordersDetailShow";
	}
	
	/**
	 * @Title: validateAdmin
	 * @Description: admin登录验证
	 * @return boolean
	 */
	private boolean validateAdmin(HttpSession httpSession){
		Manager admin = (Manager)httpSession.getAttribute("admin");
		if (admin!=null) {
			return true;
		}else {
			return false;
		}
	}
	
	private void setErrorTip(String tip,String url,ModelMap model){
		model.addAttribute("tipType", "error");
		model.addAttribute("tip", tip);
		model.addAttribute("url1", url);
		model.addAttribute("value1", "确 定");
	}
	
	private void setSuccessTip(String tip,String url,ModelMap model){
		model.addAttribute("tipType", "success");
		model.addAttribute("tip", tip);
		model.addAttribute("url1", url);
		model.addAttribute("value1", "确 定");
	}

}
