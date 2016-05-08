<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<title>中国产品质量追溯查询系统</title>
</head>
<body>
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
    <div class="leftcircle"></div>
    <div class="rightcircle"></div> 
    <div class="pdtSlide">
	    <div class="hdp">
	        <ul>
	            <c:forEach var="imgUrl" items="${proInfo.companyImgs}">
	                <li><img class="result_img" src="${imgUrl}"/></li>
	            </c:forEach>
	        </ul>
	    </div>
    </div>

    <div class="product_base_title">企业描述 </div> 
    <div class="product_base">
        <ul>
            <li><p>${proInfo.companyDesc}</p></li>
        </ul>
    </div>

</div>
</body>
</html>
