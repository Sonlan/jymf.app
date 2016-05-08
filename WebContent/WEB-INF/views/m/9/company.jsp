<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<title>中国产品质量追溯查询系统</title>
<style type="text/css">
.xzapp {
	width:30%;
    position:fixed;
    bottom:8.5%; 
    right:3%;
    filter:alpha(opacity=85);  -moz-opacity:0.85;  -khtml-opacity: 0.85;  opacity: 0.85;  
}
</style>
</head>
<body>
	<div id="product_menu">
		<nav>
		     <ul>
		         <li>
		             <a href="${ctx}/m/product" class="menu_a">产品信息</a>
		         </li>
		         <li class="product_default">
		             <a href="${ctx}/m/company" class="menu_a">公司信息</a>
		         </li>
		     </ul>
		</nav>
	</div>
	<div style="margin: 10px; overflow: hidden;">
		<div class="pdtCompany">
			<div class="hdp">
				<ul>
					<c:forEach var="imgUrl" items="${proInfo.companyImgs}">
						<li><img class="result_img" src="${imgUrl}" /></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="leftcircle"></div>
		<div class="rightcircle"></div>
		<div class="product_base">
			<ul>
				<li><p>${proInfo.companyDesc}</p></li>
			</ul>
		</div>
	</div>
	<a href="#"><img src="${ctx}/html/img/xzzyapp.png" class="xzapp"/></a>
</body>
</html>
