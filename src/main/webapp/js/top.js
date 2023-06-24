$(document).ready(function(){
	$("#loginOutBtn").bind('click',function(){
		$.post('LoginOutSystem.action',null,
			function(responseObj) {
					if(responseObj.success) {	
						 alert('退出成功！');
						 window.location.href="page_index.action";
					}else  if(responseObj.err.msg){
						 alert('退出异常：'+responseObj.err.msg);
					}else{
						 alert('退出异常：服务器异常！');
					}	
		 },'json');
	});
});

function showmenu(str){  
    var subdiv=document.getElementById(str);  
    subdiv.style.display="block";  
}  
function hidemenu(str){  
    var subdiv=document.getElementById(str);  
    subdiv.style.display="none";  
} 