<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/reg.css">
<link rel="stylesheet" type="text/css" href="css/info.css">
<script language="javascript" type="text/javascript" src=""></script>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script language="javascript" type="text/javascript"> 
	 
	
</script>
<style type="text/css">
 body,td,div
 {
   font-size:12px;
 }
 .regTable td{
 	height:40px;
 }
 .regTable input{
 	height:30px;
 }
</style>
</head>
<body>
<jsp:include page="top.jsp"></jsp:include>
<div id="middle">
	<div id="list">
		 <div class="nav">当前位置: 主页 > 用户登录 </div>
		 <div class="list_info">
		 	 <form name="info" id="info" style="width:100%;height:450px" action="LoginRegSystem.action" method="post">
		 	 <input type="hidden" name="user_card" value=""/>
		 	 <input type="hidden" name="user_powers" value=""/>
			 <table class="regTable" style="">
				<tr>
					<td class="theme" colspan="2">登录网站</td>
				</tr>
				<tr>
					<td align="right" width="20%"><span style="color:red">*</span> 用户名：</td>
					<td align="left" width="80%"><input type="text" name="user_name" id="user_name" style="width:200px;" class="inputstyle"/></td>
				</tr>
				<tr>
					<td align="right" width="20%"><span style="color:red">*</span> 密　码：</td>
					<td align="left" width="80%"><input type="password" name="user_pass" id="user_pass" style="width:200px;" class="inputstyle"/></td>
				</tr>
				<tr>
					<td align="right" width="20%"><span style="color:red">*</span> 验证码：</td>
					<td align="left" width="80%"> 
						<input type="text" id="random" name="random" style="width:80px;" class="inputstyle"/>
						&nbsp;<img src="Random.jsp" width="50" valign="middle" style="cursor:pointer;vertical-align:middle" title="换一张" id="safecode" border="0" onClick="reloadcode()"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="20%"></td>
					<td align="left" width="80%"> 
						<input type="button" id="addBtn"  value="登录"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset"  value="清空"/>
					</td>
				</tr>
		 	 </table>
		 	 </form>
		 </div>
		 
	</div>
	<div id="Picture"></div>
</div>
<script language="javascript" type="text/javascript">
//实现验证码点击刷新
function reloadcode(){
	var verify=document.getElementById('safecode');
	verify.setAttribute('src','Random.jsp?'+Math.random());
}
$(document).ready(function(){
	var addBtn = $("#addBtn");
	var user_name = $("#user_name");
	var user_pass = $("#user_pass");
	var random = $("#random");
	
	var name=/^[a-z][a-z0-9_]{3,15}$/;
    var pass=/^[a-zA-Z0-9]{3,15}$/;
    var num= /^\d+$/;
    var email=/^[\w]+[@]\w+[.][\w]+$/;
    var IdCard=/^\d{15}(\d{2}[A-Za-z0-9])?$/;
    var Phone=/^\d{11}$/;
	addBtn.bind("click",function(){
		if(user_name.val()==''||user_pass.val()==''){
			alert("用户名或密码不能为空");
			return;
		}
		if(random.val()==''){
			alert("验证码不能为空");
			return;
		}
		var aQuery = $("#info").serialize();  
		$.post('LoginInSystem.action',aQuery,
			function(responseObj) {
					if(responseObj.success) {	
						 alert('登录成功！');
						 window.location.href="page_index.action";
					}else  if(responseObj.err.msg){
						 alert('失败：'+responseObj.err.msg);
					}else{
						 alert('失败：服务器异常！');
					}	
		 },'json');
	});
});
</script>
</body>
</html>