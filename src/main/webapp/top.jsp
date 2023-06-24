<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<div id="top">
	<div id="nav">
		<ul>
        	<li><a href="page_index.action">首 页</a></li>  
        	<li><a href="page_listGoodss.action">酒店展销</a></li>  
        	 
        	<c:if test="${userFront!=null}">
        	<li><a href="page_listGoodssHobby.action">酒店推荐</a></li>  
        	</c:if>
        	<li><a href="page_listzixuns.action">资讯信息</a></li> 
        	<li><a href="page_listSblogs.action">留言板</a></li>  
	        <c:if test="${userFront!=null}">
        	<li><a href="page_listCard.action">酒店预订单</a></li>  
        	<li onmouseover="showmenu('user')" onmouseout="hidemenu('user')"><a href="#">${userFront.nick_name}▼</a>  
	            <ul id="user" class="dropMenu">  
        			<li><a href="myInfo.jsp">用户中心</a></li>  
	                <li><a id="loginOutBtn" href="javascript:void(0)">退出网站</a></li>  
	            </ul>  
	        </li> 
	        </c:if>
	        <c:if test="${userFront==null}">
        	<li><a href="login.jsp">登 录</a></li>  
        	<li><a href="reg.jsp">注 册</a></li> 
	        </c:if>
	        <li><a href="/admin" target="_blank">管理员管理</a></li> 
		</ul>
	</div>  
</div>
<script type="text/javascript" src="js/top.js"></script>