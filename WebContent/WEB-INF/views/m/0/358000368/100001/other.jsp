<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<title>中国产品质量追溯查询系统</title>
</head>
<body>

<div id="product_menu">
<nav>
     <ul>
         <li style="width:33%">
             <a href="${ctx}/m/product" class="menu_a">产品信息</a>
         </li>
         <li class="product_default" style="width:33%">
             <a href="${ctx}/m/other" class="menu_a">溯源详细</a>
         </li>
         <li style="width:34%">
             <a href="${ctx}/m/company" class="menu_a">公司信息</a>
         </li>
     </ul>
</nav>
</div>

<div style="margin:10px;overflow:hidden;">
    <Iframe id="auto" src="http://zhuogan.vicp.cc:8888/suyuan/"   
        width="95%" height="900px" scroll="auto" frameborder="0" name="content" >
    </iframe>
</div>

</body>
</html>
