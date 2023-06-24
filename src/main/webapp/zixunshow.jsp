<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户资讯</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/message.css">
<script language="javascript" type="text/javascript" src=""></script>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script charset="utf-8" src="admin/editor2/kindeditor-all-min.js"></script>
<script charset="utf-8" src="admin/editor2/lang/zh-CN.js"></script>
<script language="javascript" type="text/javascript"> 
	 
	
</script>
<style type="text/css">
 body,td,div
 {
   font-size:12px;
 }
 
 .messages {
    margin-top: 5px;
    /* border: 1px solid #d2d2d8; */
    background-color: #FBFEED;
    overflow: hidden;
}
</style>
</head>
<body>
<jsp:include page="top.jsp"></jsp:include>
<div id="middle">
	 <div class="nav">当前位置: 首页 > 用户资讯 >  </div>
	 <!-- 信息开始 -->
	 <c:if test="${zixuns!=null &&  fn:length(zixuns)>0}">
   	 <c:forEach items="${zixuns}" var="zixun" varStatus="status">
	 <div class="messages" style=" background-color:white">
		 
		<div class="messages_right">
			<div class="time">
				<img src="images/time.gif" valign="middle"/> 
				${fn:substring(zixun.time,0,19)}&nbsp;&nbsp;
			</div>
			<div class="title">
			     标题：${zixun.title}
			</div>
			<div class="title">
			    内容： ${zixun.title}
			</div>
			 
		</div>
	 </div>
	 </c:forEach>
	 </c:if>
	<!-- 信息结束 -->

	 <jsp:include page="page.jsp"></jsp:include>

 
 </div>
<jsp:include page="bottom.jsp"></jsp:include>
 
</body>
</html>