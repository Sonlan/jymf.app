<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<title>中国产品质量追溯查询系统</title>
<link href="${ctx}/html/css/labelcheck.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/html/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
 function check(){
	var consId = $("#consId").val();
	var yzm = $("#xfm").text();
	if(consId==yzm){
		$("#search").submit();
	}else{
		alert("验证码有误，请重新输入！");
	}
    
} 
window.onload = function() {
    init();
    var winWidth = $("body").outerWidth();
    var leftMargin = Math.floor(((winWidth-201))/2);
    $("#yzm").css("left",leftMargin+"px");
    $("#yzm").parent().css("padding-left",leftMargin+"px");
    document.getElementById("xfm").style.display="";
  };
  CanvasRenderingContext2D.prototype.clearArc=function(x,y,radius,startAngle, endAngle, counterclockwise){
    var distance = function(x0,y0,x1,y1){
      var dis = Math.sqrt(Math.pow(x0-x1,2)+Math.pow(y0-y1,2));
      return Math.round(dis*10000)/10000;
    };
    //首先获得矩形
    var diameter = radius*2;
    var startX = x-radius;
    var cx = radius-(0-startX);
    startX = startX<0?0:startX;
    cx = cx-startX;
    var startY = y-radius;
    var cy = radius-(0-startY);
    startY = startY<0?0:startY;
    cy = cy-startY;
    var imgData = this.getImageData(startX,startY,diameter,diameter);//需要处理的矩形
    for (var i=0;i<imgData.data.length;i+=4){
      //矩阵内的坐标
      var ii = i/4;
      var ix = Math.floor(ii/imgData.width);
      var iy = ii%imgData.height;
      var dis = distance(ix,iy,cx,cy);
      if(dis<=radius){//此点在圆内
        imgData.data[i+0]=0;
        imgData.data[i+1]=0;
        imgData.data[i+2]=0;
        imgData.data[i+3]=0;
        continue;
      }
      var dr = dis-radius;
      if(dr<Math.SQRT2){
        var n = Math.sqrt(Math.pow(dr, 2)/2)*256;//新
        var o = imgData.data[i+3];
        imgData.data[i+3]=n<o?n:o;
        continue;
      }
    }
    this.putImageData(imgData,startX,startY);
  };
  function init() {
    var imageWidth = 201;
    var imageHeight = 101;
    var yzm = document.getElementById("yzm");
    var yzm2D = yzm.getContext("2d");
    var header= $("#query_title").outerHeight(true) + $("#header").outerHeight(true);
    var canvasWidth = Math.floor($("#query_title").outerWidth()*0.25);
    yzm2D.fillStyle = '#cccccc'; //设置覆盖层的颜色
    yzm2D.fillRect(1, 1, imageWidth, imageHeight); //设置覆盖的区域
    //设置字体样式
        yzm2D.font = "20px 宋体";
        //设置字体填充颜色
        yzm2D.fillStyle = "black";
        //
        yzm2D.fillText("请刮开涂层", 40, imageHeight/2+10);

    yzm.addEventListener("touchmove", function(event) {
      event.preventDefault();
      for(var i in event.targetTouches){
        var touch = event.targetTouches[i];
 
        yzm2D.clearArc(touch.pageX-canvasWidth,touch.pageY-header,20);
      }
    }, false);
    yzm.addEventListener("touchstart", function(event) {
      event.preventDefault();
      for(var i in event.targetTouches){
        var touch = event.targetTouches[i];
        yzm2D.clearArc(touch.pageX-canvasWidth,touch.pageY-header,20);
      }
    }, false);
  }
</script>
</head>
<body>
<form:form id="search" name="search"  modelAttribute="proInfo" method="post" action="${ctx}/m/consCode" >
<div id="query_title" style="margin:10px;overflow:hidden;">
    <input type="hidden" id="labelId" name="labelId" value="${proInfo.labelId}"/>
	<div class="query_result">
        <div class="labelId">追溯码:${proInfo.labelIdSpace}</div>
    </div>
    <div class="leftcircle"></div>
    <div class="rightcircle"></div> 
    <div class="product_base queryDes" style="margin-bottom:0px"><span>您所查询的是${proInfo.queryDes}</span></div>
</div>
<div>    
<div  style="text-align: center;">
    <div id="xfm" style="position: absolute; width: 200px; height: 100px; text-align: center;line-height: 100px;border: 1px solid #ccc;display: none;font-size: 30px;">${yzm}</div>
    <canvas style="position: absolute;" id="yzm" width="201px" height="101px"></canvas>
</div>

    <div class="p-content" style ="position: absolute; top:330px">
        <span style="color: #e9503b;">请输入验证码以获取更多信息</span><br/>
        <table style="width:100%">
            <tr>
               <td width="70%">
                   <input class="text radius" id="consId" name="consId"
                          value="验证码" onblur="if(this.value == '')this.value='验证码';" 
                          onclick="if(this.value == '验证码')this.value='';"/>
               </td>
               <td width="30%">
                  <a class="check" href="javascript:check()"></a>
               </td>
            </tr>
        </table>
        <div id="message" class="error">
        <c:if test="${not empty error}">
            ${error}
        </c:if>
    	</div>
    </div>  
   
</div>
  
</form:form> 

</body>
</html>
