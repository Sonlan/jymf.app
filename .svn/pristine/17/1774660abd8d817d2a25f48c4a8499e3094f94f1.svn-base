<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<title>中国产品质量追溯查询系统</title>
</head>
<body>

<div data-role="page" id="pageone"><br/>
    <input type="hidden" id="labelId" name="labelId" value="${proInfo.labelId}"/>
	<div class="list p-sort radius">
	    <div class="mc">
	        <ul>
	             <li><span class="labelId">追溯码:${proInfo.labelIdSpace}</span></li>
	             <li><span class="queryDes">您所查询的是${proInfo.queryDes}</span></li>
	        </ul>
	    </div>
	</div>
    <div id="checked"></div>
    <c:if test="${proInfo.tel ne null && proInfo.tel ne ''}">
        <div class="reporttel">
            <span> 举报电话:<a href="tel:${proInfo.tel}">${proInfo.tel}</a></span>
        </div>
    </c:if>
    <br/>
    <br/>
        
    <div class="p-content chk_right">
        <span>国家防伪工程技术研究中心产品追溯委员会监制</span><br/>
        <span>中国产品质量追溯系统网络平台出品</span><br/>
        <a href="http://www.cpzs.net.cn/">www.中国产品质量追溯.com</a><br/>
    </div>  
    
</div>

</body>
</html>
