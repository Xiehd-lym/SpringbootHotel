<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户留言</title>
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
</style>
</head>
<body>
<jsp:include page="top.jsp"></jsp:include>
<div id="middle">
	 <div class="nav">当前位置: 首页 > 用户留言 >  </div>
	 <!-- 信息开始 -->
	 <c:if test="${sblogs!=null &&  fn:length(sblogs)>0}">
   	 <c:forEach items="${sblogs}" var="sblog" varStatus="status">
	 <div class="messages">
		 <div class="messages_left">
			<div class="nickName" style="">&nbsp;&nbsp;${sblog.nick_name}</div>
			<div class="headphoto"><img class="circle" src="images/head/${sblog.user_photo}"/></div>
			<div class="stuNo"></div>
		</div>
		<div class="messages_right">
			<div class="time">
				<img src="images/time.gif" valign="middle"/> 
				${fn:substring(sblog.sblog_date,0,19)}&nbsp;&nbsp;
			</div>
			<div class="title">
			     ${sblog.sblog_contentShow}
			</div>
			<c:if test="${sblog.replys != null && fn:length(sblog.replys) >0}">
	 		<c:forEach items="${sblog.replys}" var="sblogReply">
			<div class="reply">
				 <div class="user">管理员回复：</div>
				 <div class="reply_con">
				 	${sblogReply.reply_contentShow}
				 </div>
				 <div class="reply_time">回复时间：${sblogReply.reply_date}</div>
			</div>
			<hr/>
			</c:forEach>
	 		</c:if>
		</div>
	 </div>
	 </c:forEach>
	 </c:if>
	<!-- 信息结束 -->

	 <jsp:include page="page.jsp"></jsp:include>

	 <!-- 发布留言 -->
	 <div id="addSblog" style="display:block">
	 <form name="info" id="info" action="page_addSblog.action" method="post">
	 <input type="hidden" name="user_id" id="user_id" value="${userFront.user_id}"/>
	 <table class="reply_add">
		<tr>
			<td class="theme" colspan="2">发表留言：</td>
		</tr>
		<tr>
			<td align="left" colspan="2" style="padding-left:10px">
				<textarea name="sblog_content" id="noticeContent" style="width:600px;height:150px" class="inputstyle"></textarea>
			</td>
		</tr>
		<tr>
			<td align="left" colspan="2" style="padding-left:10px">
				<input type="button" id="addBtn" class="btnstyle" value="提交"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="reset" id="resetBtn" class="btnstyle" value="清空"/>
			</td>
		</tr>
	 </table>
	 </form>
	 </div>
	 <a name="link"></a>
</div>
<jsp:include page="bottom.jsp"></jsp:include>
<script type="text/javascript">
var user_id = "${userFront.user_id}";
//实现验证码点击刷新
function reloadcode(){
	var verify=document.getElementById('safecode');
	verify.setAttribute('src','Random.jsp?'+Math.random());
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
  window.location.href="page_listSblogs.action?pageNo="+pagenum;
}
function ChangePage(pagenum)
{
  window.location.href="page_listSblogs.action?pageNo="+pagenum;
}
 
$(document).ready(function(){
	$("#addBtn").bind("click",function(){
		editor.sync();
		if(user_id==''){
			alert('请先登录后在进行留言！')
			return;
		}
		if($("#noticeContent").val()==''){
			alert('留言内容不能为空！')
			return;
		}
		var aQuery = $("#info").serialize();  
		$.post('page_addSblog.action',aQuery,
			function(responseObj) {
					if(responseObj.success) {	
						 alert('感谢您的留言！');
						 window.location.reload();
					}else  if(responseObj.err.msg){
						 alert('留言失败：'+responseObj.err.msg);
					}else{
						 alert('留言失败：服务器异常！');
					}	
		},'json');
	});
	
});

KindEditor.ready(function(K) {
	window.editor = K.create('#noticeContent',{
		width : '95%',
		items:[
			'fullscreen','|','justifyleft', 'justifycenter', 'justifyright','justifyfull',
			'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			'italic', 'underline','anchor', 'link', 'unlink'
		],
		uploadJson : 'admin/editor2/jsp/upload_json.jsp',
        fileManagerJson : 'admin/editor2/jsp/file_manager_json.jsp',
        allowFileManager : true

	});
});
</script>
</body>
</html>