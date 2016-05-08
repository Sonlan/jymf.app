<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<link href="${ctx}/html/css/qipei.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div id="product_menu">
<nav><ul>
    <li class="product_default">
        <a href="${ctx}/m/product" class="menu_a">产品信息</a>
    </li>
    <li>
        <a href="${ctx}/m/company" class="menu_a">公司信息</a>
    </li>
</ul></nav>
</div>
<div style="margin:10px;overflow:hidden;">
    <div class="query_result">
        <c:if test="${proInfo.consCnt eq '1'}">
            <div class="query_result_title">
                <span class="title">该产品首次查询</span>
            </div>
        </c:if>
        
        <c:if test="${proInfo.consCnt ne '1'}">
            <div class="query_result_title"><span>警告</span></div>
            <div class="query_result_content">
                <span>此商品已被消费！</span><br/>
                <span>消费时间：${proInfo.firstConsDate}</span><br/>
                <c:if test="${proInfo.firstConsAddress ne null and proInfo.firstConsAddress ne ''}">
                    <span>消费地址：${proInfo.firstConsAddress}</span><br/>
                </c:if>
            </div>
        </c:if>
        
        <div class="queryDes"><span>您所查询的是${proInfo.queryDes}</span></div>
    </div>  

    <div class="product_base_title">配件详细 </div> 
    <div class="leftcircle"></div>
    <div class="rightcircle"></div>          
    <div class="product_base">
        <c:forEach var="product" items="${proInfo.carLst}">
		    <div class="desc">
		    <table>
                <tr><td width="100px">配件名称:</td><td>${product.name}</td></tr>
                <tr><td>配件型号:</td><td>${product.numberId}</td></tr>
                <c:if test="${product.producer ne null and product.producer ne ''}">
                    <tr><td>配件厂商:</td><td>${product.producer}</td></tr>
                </c:if>
                <tr><td>配件数量:</td><td>${product.number}</td></tr>
            </table>
		    </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
