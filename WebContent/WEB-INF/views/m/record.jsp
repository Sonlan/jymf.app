<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
<title>追溯记录</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<style>
/*追溯记录*/
.record_con{ width:100%;}
.record_con ul{overflow:hidden;}
.record_con li{padding:0.4rem; border-bottom:0.05rem solid #e8e8e8; overflow:hidden;}
.record_con li.last{ border-bottom:none;}
.record_con li .imgbox{ float:left; width:5rem; border-radius:0.2rem; overflow:hidden;} 
.record_con li .imgbox img{width:100%;}
.record_con li .right{float:left; margin-left:0.3rem; color:#555; margin-top:3%;}
.record_con li .right h6{ font-size:0.6rem;}
.record_con li .right p{font-size:0.5rem; padding-top:0.1rem;}
.record_con li .right p.data{font-size:0.48rem; color:#999;}
</style>
</head>
<body>
<div class="con container">
    <div class="record_con">
    	<ul>
    	<c:forEach var="bean" items="${list}">
    	  <li>
          	<div class="imgbox imgtobig"><img src="${bean.prdimgurl}" alt=""/></div>
            <div class="right">
            	<h6>${bean.prdname}</h6>
                <p>追溯码: ${bean.labelid}</p>
                <p class="data">${bean.firsttime}</p>
            </div>
          </li>
    	</c:forEach>
    	</ul>
        
    </div>
</div>
</body>
<script type="text/javascript">
//设置rem
(function(){
	window.onload=window.onresize=function(){
		document.documentElement.style.fontSize=document.documentElement.clientWidth*20/320+'px';
	};
})();
</script>
</html>
