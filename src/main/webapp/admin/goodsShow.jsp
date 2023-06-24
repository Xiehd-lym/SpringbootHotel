<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>酒店信息</title>
<link href="css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript">
var tempClassName="";
function tr_mouseover(obj) 
{ 
	tempClassName=obj.className;
	obj.className="list_mouseover";
}
function tr_mouseout(obj)      
{ 
	obj.className=tempClassName;
}      
function CheckAll(obj) 
{
	var checks=document.getElementsByName("chkid");
    for (var i=0;i<checks.length;i++)
	{
	    var e = checks[i];
	    e.checked = obj.checked;
	}
    
}


function serch()
{
   document.info.action="Admin_listGoodss.action";
   document.info.submit();
}
function del()
{
	var checks=document.getElementsByName("chkid");
    var ids="";
	for (var i=0;i<checks.length;i++)
    {
        var e = checks[i];
		if(e.checked==true)
		{
		  if(ids=="")
		  {
		    ids=ids+e.value;
		  }
		  else
		  {
		    ids=ids+","+e.value;
		  }
		}
    }
    if(ids=="")
    {
       alert("请至少选择一个要删除的酒店！");
       return false;
    }
    if(confirm('确认删除吗!?'))
    {
       document.info.action="Admin_delGoodss.action?ids="+ids;
       document.info.submit();
    }
    
}
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
  document.info.action="Admin_listGoodss.action";
  document.info.submit();
}
function ChangePage(pagenum)
{
  document.getElementById("pageNo").value=pagenum;
  document.info.action="Admin_listGoodss.action";
  document.info.submit();
}
</script>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">酒店管理&gt;&gt;酒店查询</span>
</div>
<form name="info" id="info" action="Admin_listGoodss.action" method="post">
<input type="hidden" name="pageNo" id="pageNo" value="${paperUtil.pageNo}"/>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
  <tr><td colspan="2" height="10px">&nbsp;</td></tr>
  <tr>
    <td width="">酒店列表</td>
    <td width="" align="right">
            酒店号：
      <input type="text" id="goods_no" name="goods_no" value="${paramsGoods.goods_no}" class="inputstyle" Style="width:100px;"/>&nbsp;
            酒店名称：
      <input type="text" id="goods_name" name="goods_name" value="${paramsGoods.goods_name}" class="inputstyle" Style="width:100px;"/>&nbsp;
            酒店类型：
      <select id="goods_type_id" name="goods_type_id" class="selectstyle" style="width:100px;">
      	<option value="0">请选择</option>
      	<c:forEach items="${goodsTypes}" var="goodsType">
      	<option value="${goodsType.goods_type_id}" ${goodsType.goods_type_id==paramsGoods.goods_type_id?'selected':''}>${goodsType.goods_type_name}</option>
      	</c:forEach>
      </select>&nbsp;
      <input type="button" value="搜索" onclick="serch();" class="btnstyle"/>&nbsp;
      <input type="button" value="删除" onclick="del();" class="btnstyle"/>
    </td>
  </tr>
  <tr><td colspan="2" height="2px"></td></tr>  
</table>
<table width="95%" align="center" class="table_list" cellpadding="0" cellspacing="0">
   <tr class="listtitle">
     <td width="40" align="center"><input type="checkbox" onclick="CheckAll(this);" style="vertical-align:text-bottom;" title="全选/取消全选"/></td>
     <td width="40" align="center">序号</td>
     <td width="" align="center">酒店号</td>
     <td width="" align="center">酒店名称</td>
     <td width="" align="center">酒店类型</td>
     <td width="" align="center">发行商</td>
     <td width="" align="center">酒店原价</td>
     <td width="" align="center">当前折扣</td>
     <td width="" align="center">操作</td>
   </tr>
   <c:if test="${goodss!=null && fn:length(goodss)>0}">
   <c:forEach items="${goodss}" var="goods" varStatus="status">
   <tr class="list0" onmouseover="tr_mouseover(this)" onmouseout="tr_mouseout(this)"> 
     <td width="" align="center"><input type="checkbox" name="chkid" value="${goods.goods_id}" style="vertical-align:text-bottom;"/></td>
     <td width="" align="center">${status.index+1}</td>
     <td width="" align="center">${goods.goods_no}</td>
     <td width="" align="center">${goods.goods_name}</td>
     <td width="" align="center">${goods.goods_type_name}</td>
     <td width="" align="center">${goods.goods_publisher}</td>
     <td width="" align="center">${goods.goods_price}</td>
     <td width="" align="center">${goods.goods_discount}</td>
     <td width="" align="center">
       <a href="Admin_queryGoods.action?goods_id=${goods.goods_id}">查看</a>&nbsp;
       <a href="Admin_editGoods.action?goods_id=${goods.goods_id}">编辑</a>
     </td>
   </tr> 
   </c:forEach>
   </c:if>
   <c:if test="${goodss==null && fn:length(goodss)==0}">
   <tr>
     <td height="60" colspan="12" align="center"><b>&lt;不存在酒店信息&gt;</b></td>
   </tr>
   </c:if>
   
</table>
<jsp:include page="page.jsp"></jsp:include>
</form> 
</body>
</html>