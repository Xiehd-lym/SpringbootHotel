<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>酒店推荐列表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script language="javascript" type="text/javascript"> 
$(document).ready(function(){
 
}); 
</script>
<style type="text/css">
 body,td,div
 {
   font-size:12px;
 }
</style>
</head>
<body>
<jsp:include page="top.jsp"></jsp:include>
<div id="middleBg">
	<!--  产品检索展示 -->
	 <div id="product_info">
	 		<!--  产品列表 -->
	 		<form name="info" id="info" action="page_listGoodssHobby.action" method="post">
	 		<input type="hidden" id="pageNo" name="pageNo" value="${paperUtil.pageNo}"/>
			<div class="list">
					<div class="sign" style="background:none;text-align:right">
						酒店名称：<input type="text" name="goods_name" value="${paramsGoods.goods_name}" class="inputstyle" style="width:150px;"/>&nbsp;
						酒店类型： <select id="goods_type_id" name="goods_type_id" class="inputstyle" style="width:150px;">
							      	<option value="0">请选择</option>
							      	<c:forEach items="${goodsTypes}" var="goodsType">
							      	<option value="${goodsType.goods_type_id}" ${goodsType.goods_type_id==paramsGoods.goods_type_id?'selected':''}>${goodsType.goods_type_name}</option>
							      	</c:forEach>
							      </select>&nbsp;&nbsp;
						<input type="button" value="搜索" onclick="serch();" class="btnstyle"/>&nbsp;
					</div>
					<div class="products">
					<c:if test="${goodss!=null && fn:length(goodss)>0}">
   					<c:forEach items="${goodss}" var="goods" varStatus="status">
					<div class="product">
						<div class="productPic"><a href="page_queryGoods.action?goods_id=${goods.goods_id}"><img src="images/datas/${goods.goods_pic}" /></a></div>
						<div class="productText"><span class="title">${goods.goods_name}</span>
						<br/>酒店价格：￥${goods.goods_price}
							  <br/>当前折扣：${goods.goods_discount}折
						</div>
					</div>
					</c:forEach>
   					</c:if>
					</div>
					
					<!--  产品分页 -->
					<jsp:include page="page.jsp"></jsp:include>
				    <!--  产品分页 -->

			</div>
			</form>
			<!--  产品列表 -->
			
	 </div>
	 <!--  产品检索展示 -->
	 
</div>
<jsp:include page="bottom.jsp"></jsp:include>
<script language="javascript" type="text/javascript">
function GoPage()
{
  var pagenum=document.getElementById("goPage").value;
  var patten=/^\d+$/;
  if(!patten.exec(pagenum))
  {
    alert("页码必须为大于0的数字");
    return false;
  }
  document.getElementById("pageNo").value=pagenum;
  document.info.submit();
}
function ChangePage(pagenum)
{
	 document.getElementById("pageNo").value=pagenum;
	 document.info.submit();
}	 
function serch()
{
   document.info.action="page_listGoodssHobby.action";
   document.info.submit();
}
</script>
</body>
</html>