﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>济南酒店推荐系统网站</title>
<meta http-equiv="X-UA-Compatible" content="IE=8"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/style.css">
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
</style>
</head>
<BODY style="background-image:url('images/login_03.jpg')">
<FORM id="info" name="info"  method="post" action="LoginInSystem.action">
<DIV id=UpdatePanel1 style="width:900px;margin:0 auto">
<TABLE cellSpacing=0 cellPadding=0 width=900 align=center border=0>
	<TBODY>
		<TR>
			<TD style="font-size:46px;font-family:'隶书';text-align:center;HEIGHT:105px;color:#949449">
				济南酒店推荐系统网站
			</TD>
		</TR>
		<TR>
			<TD background=images/login_2.jpg height=300>
			<TABLE height=300 cellPadding=0 width=900 border=0>
			<TBODY>
				<TR>
					<TD colSpan=2 height=35></TD></TR>
				<TR>
					<TD width=400></TD>
					<TD>
					<TABLE cellSpacing=0 cellPadding=2 border=0>
					<TBODY>
						<TR>
							<TD style="HEIGHT: 28px" width=80>登 录 名：</TD>
							<TD style="HEIGHT: 28px" width=150><INPUT id="user_name" name="user_name" value="${user_name}" style="WIDTH: 130px" name=txtName></TD>
							<TD style="HEIGHT: 28px" width=370>
								<SPAN  id="RequiredFieldValidator1" style="FONT-WEIGHT: bold; VISIBILITY: hidden; COLOR: white">请输入登录名</SPAN>
							</TD>
						</TR>
						<TR>
							<TD style="HEIGHT: 28px">登录密码：</TD>
							<TD style="HEIGHT: 28px"><INPUT id="user_pass" name="user_pass"   style="WIDTH: 130px" 
							  type="password"></TD>
							<TD style="HEIGHT: 28px">
								<SPAN id="RequiredFieldValidator2" style="FONT-WEIGHT: bold; VISIBILITY: hidden; COLOR: white">请输入密码</SPAN>
							</TD>
						</TR>
						<TR>
							<TD style="HEIGHT: 28px">验 证 码：</TD>
							<TD style="HEIGHT: 28px">
								<INPUT id="random" name="random" style="WIDTH: 60px" name=txtcode>
								<img src="Random.jsp" width="50" valign="middle" style="cursor:pointer;vertical-align:middle" title="换一张" id="safecode" border="0" onClick="reloadcode()"/>
							</TD>
							<TD style="HEIGHT: 28px">
								<SPAN id="RequiredFieldValidator3" style="FONT-WEIGHT: bold; VISIBILITY: hidden; COLOR: white">请输入验证码</SPAN>
							</TD>
						</TR>
						<TR>
							<TD></TD>
							<TD colspan="2" id="loginTip" style="HEIGHT:22px;color:orange">${tip}</TD>
						</TR>
						<TR>
							<TD></TD>
							<TD colspan="2"><img id="loginInBtn"  style="border:0px;cursor:pointer" src="images/login_button.gif" /> </TD>
						</TR>
					</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
			</TABLE>
			</TD>
		</TR>


	</TBODY>
</TABLE>
</DIV>
</DIV>
</FORM>
<script language="javascript" type="text/javascript">
	//实现验证码点击刷新
	function reloadcode(){
		var verify=document.getElementById('safecode');
		verify.setAttribute('src','Random.jsp?'+Math.random());
	}

	$(document).ready(function(){
		var loginInBtn = $("#loginInBtn");
		var user_name = $("#user_name");
		var user_pass = $("#user_pass");
		var random = $("#random");
		var loginTip = $("#loginTip");
		
		loginInBtn.bind('click',function(){
			if(user_name.val()==''||user_pass.val()==''||random.val()==''){
				loginTip.html("用户名、密码和验证码不能为空！")
				return;
			}
			$("#info").submit();
		});
		
	});
</script>
</BODY>
</HTML>
