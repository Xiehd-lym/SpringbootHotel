<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${goods!=null && goods.goods_id!=0?'编辑':'添加'}酒店信息</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script charset="utf-8" src="editor2/kindeditor-all-min.js"></script>
<script charset="utf-8" src="editor2/lang/zh-CN.js"></script>
<script language="javascript" type="text/javascript">
$(document).ready(function(){
	
	 var num = /^\d+(\.\d+)?$/;
	 var num2 = /^\d+$/;
	 $("#addBtn").bind('click',function(){
		editor.sync();
		if($("#goods_no").val()==''){
			alert('酒店号不能为空');
			return;
		}
		if($("#goods_name").val()==''){
			alert('酒店名称不能为空');
			return;
		}
		if($("#goods_type_id").val()=='0'){
			alert('酒店类型不能为空');
			return;
		}
		if($("#goods_publisher").val()==''){
			alert('发行商不能为空');
			return;
		}
		if($("#goods_pic").val()==''){
			alert('酒店图片不能为空');
			return;
		}
		if(!num.exec($("#goods_price").val())){
			alert('酒店原价必须为数字');
			return;
		}
		if(!num.exec($("#goods_discount").val())){
			alert('酒店折扣必须为数字');
			return;
		}
		if($("#noticeContent").val()==''){
			alert('酒店介绍不能为空');
			return;
		}
		$("#goods_id").val(0);
		$("#info").attr('action','Admin_addGoods.action').submit();
		 
	 });
	 
	 $("#editBtn").bind('click',function(){
		editor.sync();
		if($("#goods_name").val()==''){
			alert('酒店名称不能为空');
			return;
		}
		if($("#goods_type_id").val()=='0'){
			alert('酒店类型不能为空');
			return;
		}
		if($("#goods_publisher").val()==''){
			alert('发行商不能为空');
			return;
		}
		if($("#goods_pic").val()==''){
			alert('酒店图片不能为空');
			return;
		}
		if(!num.exec($("#goods_price").val())){
			alert('酒店原价必须为数字');
			return;
		}
		if(!num.exec($("#goods_discount").val())){
			alert('酒店折扣必须为数字');
			return;
		}
		if($("#noticeContent").val()==''){
			alert('酒店介绍不能为空');
			return;
		}
		$("#info").attr('action','Admin_saveGoods.action').submit();
			 
	});
	
});
</script>
<style type="text/css">
</style>
</head>
<body>
<div class="pageTitle">
	&nbsp;&nbsp;<img src="images/right1.gif" align="middle" /> &nbsp;<span id="MainTitle" style="color:white">酒店管理&gt;&gt;${goods!=null && goods.goods_id!=0?'编辑':'添加'}酒店</span>
</div>
<form id="info" name="info" action="Admin_addGoods.action" method="post">   
<input type="hidden" id="goods_id" name="goods_id" value="${goods.goods_id}" /> 
<input type="hidden" name="goods_pic" id="goods_pic" value='${goods.goods_pic}'/>
<table width="800" align="center" cellpadding="0" cellspacing="0" style="margin-top:10px;margin-bottom:10px;">
  <tr> 
     <td height="24">
       <Table border="0" cellspacing="0" cellpadding="0" align="center" width="100%"> 
            <TR>
              <TD height="24" class="edittitleleft">&nbsp;</TD>
              <TD class="edittitle">${goods!=null && goods.goods_id!=0?'编辑':'添加'}</TD>
              <TD class="edittitleright">&nbsp;</TD>
            </TR>
        </TABLE>
     </td>
   </tr>
   <tr>
     <td height="1" bgcolor="#8f8f8f"></td>
   </tr>
   <tr>
     <td >
     <table width="100%" align="center" cellpadding="1" cellspacing="1" class="editbody">
        <tr>
          <td width="150" align="right" style="padding-right:5px"><font color="red">*</font> 酒店号：</td>
          <td width="*">
          	<c:if test="${goods!=null && goods.goods_id!=0}">
          	${goods.goods_no}
          	</c:if>
          	<c:if test="${goods==null || goods.goods_id==0}">
          	<input type="text" name="goods_no" id="goods_no" value="${goods.goods_no}"/>
          	</c:if>
          </td>
        </tr> 
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 酒店名称：</td>
          <td>
          	<input type="text" name="goods_name" id="goods_name" value="${goods.goods_name}"/>
          </td>
        </tr> 
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 酒店类型：</td>
          <td>
          	 <select id="goods_type_id" name="goods_type_id" style="width:150px;">
		      	<option value="0">请选择</option>
		      	<c:forEach items="${goodsTypes}" var="goodsType">
		      	<option value="${goodsType.goods_type_id}" ${goodsType.goods_type_id==goods.goods_type_id?'selected':''}>${goodsType.goods_type_name}</option>
		      	</c:forEach>
		      </select>
          </td>
        </tr> 
        <tr>
		  <td align="right" style="padding-right:5px"><font color="red">*</font> 酒店图片：</td>
		  <td align="left" colspan="3">
		    <img id="userImg" src="../images/datas/${goods.goods_pic}" width="70" height="80" style="border:0px;"/>
	        &nbsp;<span id="op" style="display:none"><img src="images/loading004.gif"  height="80" /></span>
	      </td>
	    </tr>
	     <tr>
		  <td align="right" style="padding-right:5px"></td>
	      <td align="left" colspan="3">
	          <iframe name="uploadPage" src="uploadImg2.jsp" width="100%" height="50" marginheight="0" marginwidth="0" scrolling="no" frameborder="0"></iframe>            
	       </td>
	    </tr>
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 发行商：</td>
          <td>
          	<input type="text" name="goods_publisher" id="goods_publisher" value="${goods.goods_publisher}"/>
          </td>
        </tr> 
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 酒店原价：</td>
          <td>
          	<input type="text" name="goods_price" id="goods_price" value="${goods.goods_price}"/>
          </td>
        </tr> 
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 酒店折扣：</td>
          <td>
          	<input type="text" name="goods_discount" id="goods_discount" value="${goods.goods_discount}"/> 折
          </td>
        </tr> 
        <tr>
          <td align="right" style="padding-right:5px"><font color="red">*</font> 酒店介绍：</td>
          <td>
          	<textarea style="width:500px;height:300px" name="goods_desc" id="noticeContent">${goods.goods_desc}</textarea>
          </td>
        </tr>
     </table>
     </td>
   </tr>  
   <tr>
     <td>
       <table width="100%" align="center" cellpadding="0" cellspacing="0" class="editbody">
        <tr class="editbody">
          <td align="center" height="30">
          	<c:if test="${goods!=null && goods.goods_id!=0}">
          	<input type="button" id="editBtn" Class="btnstyle" value="编 辑"/> 
          	</c:if>
          	<c:if test="${goods==null || goods.goods_id==0}">
          	<input type="button" id="addBtn" Class="btnstyle" value="添 加" />
          	</c:if>
            &nbsp;<label style="color:red">${tip}</label>
          </td>
        </tr>
      </table>
     </td>
   </tr>
</table>
</form>
<script>        
KindEditor.ready(function(K) {
	window.editor = K.create('#noticeContent',{
		width : '95%',
		items:[
			'fullscreen','|','justifyleft', 'justifycenter', 'justifyright','justifyfull',
			'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			'italic', 'underline','anchor', 'link', 'unlink'
		],
        allowFileManager : true
	});
});
	   
</script>
</body>
</html>