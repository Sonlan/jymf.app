<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<link href="${ctx}/html/css/shangcheng.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div style="margin:10px;overflow:hidden;">  
    <c:if test="${proInfo.consCnt eq '1'}">
    <div class="query_result">
        <div class="query_result_title">
            <span class="title">该产品首次查询</span>
        </div>
    </div>
    </c:if>
    <c:if test="${proInfo.consCnt ne '1'}">
    <div class="query_result">
        <div class="query_result_title">
            <span>警告</span>
        </div>
        <div class="query_result_content">
            <span>此商品已被消费！</span><br/>
            <span>消费时间：${proInfo.firstConsDate}</span><br/>
            <c:if test="${proInfo.firstConsAddress ne null and proInfo.firstConsAddress ne ''}">
            <span>消费地址：${proInfo.firstConsAddress}</span><br/>
            </c:if>
        </div>
    </div>
    </c:if>
    
    <div class="product_rst">
        <p>您所查询的是${proInfo.queryDes}</p>
    </div>

    <div class="product_url">
        <span>了解更多商品企业信息,请登录商铺网站</span><br/>
        <a href="${proInfo.url}">${proInfo.url}</a>
    </div>
    <div class="company_base">
        <span>${proInfo.companyName}</span>
        <ul>
            <li><p>${proInfo.companyDesc}</p></li>
        </ul>
    </div>
    <div class="pdtSlide">
	    <div class="hdp">
	        <ul>
	            <c:forEach var="imgUrl" items="${proInfo.companyImgs}">
	                <li><img class="result_img" src="${imgUrl}"/></li>
	            </c:forEach>
	        </ul>
	    </div>
    </div>
</div>

</body>
</html>
