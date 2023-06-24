function setIframeWH()
{
     
     //设置LeftMenu高度自适应     
     var LeftMenu=document.getElementById("LeftMenu");
     var LHeight1 = LeftMenu.contentWindow.document.body.scrollHeight;
     var LHeight2 = LeftMenu.contentWindow.document.documentElement.scrollHeight; 
     var LHeight = Math.max(LHeight1, LHeight2);
     LeftMenu.height =  (document.documentElement.clientHeight-78)+"px";
     MainFrame.height =  LeftMenu.height;
}   