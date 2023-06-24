<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>提交订单</title>
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
<script type="text/javascript"
	src="https://api.map.baidu.com/api?v=2.0&ak=B1f82jlfVMT9uUr31SCAX48fs96lmx1n"></script>
<script language="javascript" type="text/javascript">
	
</script>
<style type="text/css">
body, td, div {
	font-size: 12px;
}
</style>
</head>
<body>
	<jsp:include page="top.jsp"></jsp:include>
	<div id="middle">
		<div id="list">
			<div class="nav">当前位置: 主页 > 提交订单</div>
			<div class="list_info">
				<form name="info" id="info" style="width: 100%; min-height: 500px"
					action="LoginRegSystem.action" method="post">
					<input type="hidden" name="user_id" value="${userFront.user_id}" />
					<input type="hidden" name="real_name"
						value="${userFront.real_name}" />
					<table class="regTable">
						<tr class="theme">
							<td width="" align="center">酒店名称</td>
							<td width="" align="center">酒店单价</td>
							<td width="" align="center">酒店折扣</td>
							<td width="" align="center">酒店数量</td>
							<td width="" align="center">酒店总额</td>
						</tr>
						<c:if test="${ordersDetails!=null && fn:length(ordersDetails)>0}">
							<c:forEach items="${ordersDetails}" var="ordersDetail"
								varStatus="status">
								<tr>
									<td width="" align="center">${ordersDetail.goods_name}</td>
									<td width="" align="center"
										id="goods_price-${ordersDetail.goods_id}">￥${ordersDetail.goods_price}</td>
									<td width="" align="center"
										id="goods_discount-${ordersDetail.goods_id}">${ordersDetail.goods_discount}</td>
									<td width="" align="center">${ordersDetail.goods_count}</td>
									<td width="" align="center"
										id="goods_money-${ordersDetail.goods_id}">￥${ordersDetail.goods_money}</td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="5" align="right"
									style="padding-right: 50px; font-weight: bold">
									订单总额：￥${orders_money}</td>
							</tr>
							<tr>
								<td align="right"><span style="color: red">*</span> 联系电话：</td>
								<td align="left" colspan="4"><input type="text"
									name="user_phone" id="user_phone"
									value="${userFront.user_phone}" style="width: 200px;"
									class="inputstyle" /></td>
							</tr>
							<tr>
								<td align="right"><span style="color: red">*</span> 收货地址：</td>
								<td align="left" colspan="4"><input type="text"
									name="address" id="suggestId" style="width: 200px;"
									class="inputstyle" />
									<div id="searchResultPanel"
										style="border: 1px solid #C0C0C0; width: 150px; height: auto; display: none;"></div>
								</td>
							</tr>
							<tr>
								<td align="right"><span style="color: red">*</span> 电子邮箱：</td>
								<td align="left" colspan="4"><input type="text"
									name="user_mail" id="user_mail" value="${userFront.user_mail}"
									style="width: 200px;" class="inputstyle" /></td>
							</tr>
							<tr>
								<td align="right"></td>
								<td align="left" colspan="4"><input type="button"
									id="addBtn" class="btnstyle" value="确认支付" /></td>
							</tr>
						</c:if>
						<c:if test="${ordersDetails==null && fn:length(ordersDetails)==0}">
							<tr>
								<td height="60" colspan="6" align="center"><b>&lt;不存在订单信息&gt;</b></td>
							</tr>
						</c:if>
					</table>
			</div>
		</div>
		<div id="Picture"></div>
		<div style="width: 100%; height: 500px; z-index:999" id="l-map"></div>
		

	</div>
	<jsp:include page="bottom.jsp"></jsp:include>
	<script language="javascript" type="text/javascript">
		$(document).ready(function() {
			var addBtn = $("#addBtn");
			//提交订单
			addBtn.bind("click", function() {
				if ($("#user_phone").val() == '') {
					alert("联系电话不能为空");
					return;
				}
				if ($("#user_mail").val() == '') {
					alert("电子邮箱不能为空");
					return;
				}
				if ($("#address").val() == '') {
					alert("收货地址不能为空");
					return;
				}
				var aQuery = $("#info").serialize();
				$.post('page_addOrders.action', aQuery, function(responseObj) {
					if (responseObj.success) {
						alert('支付成功！');
						window.location.href = "page_listMyOrderss.action";
					} else if (responseObj.err.msg) {
						alert('失败：' + responseObj.err.msg);
					} else {
						alert('失败：服务器异常！');
					}
				}, 'json');
			});

		});
	</script>
	<script type="text/javascript">
		// 百度地图API功能
		function G(id) {
			return document.getElementById(id);
		}

		var map = new BMap.Map("l-map");
		map.centerAndZoom("北京", 12); // 初始化地图,设置城市和地图级别。

		var ac = new BMap.Autocomplete( //建立一个自动完成的对象
		{
			"input" : "suggestId",
			"location" : map
		});

		ac.addEventListener("onhighlight", function(e) { //鼠标放在下拉列表上的事件
			var str = "";
			var _value = e.fromitem.value;
			var value = "";
			if (e.fromitem.index > -1) {
				value = _value.province + _value.city + _value.district
						+ _value.street + _value.business;
			}
			str = "FromItem<br />index = " + e.fromitem.index
					+ "<br />value = " + value;

			value = "";
			if (e.toitem.index > -1) {
				_value = e.toitem.value;
				value = _value.province + _value.city + _value.district
						+ _value.street + _value.business;
			}
			str += "<br />ToItem<br />index = " + e.toitem.index
					+ "<br />value = " + value;
			G("searchResultPanel").innerHTML = str;
		});

		var myValue;
		ac.addEventListener("onconfirm", function(e) { //鼠标点击下拉列表后的事件
			var _value = e.item.value;
			myValue = _value.province + _value.city + _value.district
					+ _value.street + _value.business;
			G("searchResultPanel").innerHTML = "onconfirm<br />index = "
					+ e.item.index + "<br />myValue = " + myValue;

			setPlace();
		});

		function setPlace() {
			map.clearOverlays(); //清除地图上所有覆盖物
			function myFun() {
				var pp = local.getResults().getPoi(0).point; //获取第一个智能搜索的结果
				map.centerAndZoom(pp, 18);
				map.addOverlay(new BMap.Marker(pp)); //添加标注
			}
			var local = new BMap.LocalSearch(map, { //智能搜索
				onSearchComplete : myFun
			});
			local.search(myValue);
		}
		// 百度地图API功能
		var map = new BMap.Map("l-map");
		var point = new BMap.Point(116.331398,39.897445);
		map.centerAndZoom(point,12);

		var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(r){
			if(this.getStatus() == BMAP_STATUS_SUCCESS){
				var mk = new BMap.Marker(r.point);
				map.addOverlay(mk);
				map.panTo(r.point);
			}
			else {
				alert('failed'+this.getStatus());
			}        
		},{enableHighAccuracy: true})
	</script>

</body>
</html>