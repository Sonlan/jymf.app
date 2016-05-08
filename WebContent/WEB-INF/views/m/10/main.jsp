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
    <div class="leftcircle"></div>
    <div class="rightcircle"></div>          
    <div class="pdtSlide">
        <div class="hdp">
        <ul>
            <c:forEach var="imgUrl" items="${proInfo.productImgs}">
                <li><img class="result_img" src="${imgUrl}"/></li>
            </c:forEach>
        </ul>
        </div>
    </div> 


    <div class="product_base_title">产品描述 </div> 
    <div class="leftcircle"></div>
    <div class="rightcircle"></div>          
    <div class="product_base">
        <ul>
            <li>商品名称：${proInfo.productName}</li>
            <c:if test="${proInfo.productOrigin ne null && proInfo.productOrigin ne ''}">
                <li>原产国家：${proInfo.productOrigin}</li>
            </c:if>
            <li>商品类型：${proInfo.productPara.gx[0].value}</li>
            <li>容&nbsp;&nbsp;&nbsp;&nbsp;量：${proInfo.productPara.gx[1].value}</li>
            <li>葡萄品种：${proInfo.productPara.gx[2].value}</li>
            <li>酒精含量：${proInfo.productPara.gx[3].value}</li>
            <li>年&nbsp;&nbsp;&nbsp;&nbsp;份：${proInfo.productPara.gx[4].value}</li>
            <li>色&nbsp;&nbsp;&nbsp;&nbsp;泽：${proInfo.productPara.gx[5].value}</li>
            <li>醒酒时间：${proInfo.productPara.gx[6].value}</li>
            <li>品尝温度：${proInfo.productPara.gx[7].value}</li>
            <c:if test="${proInfo.productDesc ne null && proInfo.productDesc ne ''}">
                <li>商品特点：${proInfo.productDesc}</li>
            </c:if>
            <li>进口商：${proInfo.productPara.gx[8].value}</li>
            <li>经销商：${proInfo.productPara.gx[9].value}</li>
         </ul>
    </div> 
    
    <div class="product_base_title">正品保障 </div> 
    <div class="leftcircle"></div>
    <div class="rightcircle"></div>          
    <div class="product_base">
        <ul>
            <c:if test="${proInfo.jyzUrl ne null and proInfo.jyzUrl ne ''}">
	        <li>
	            <a href="${proInfo.jyzUrl}">
	                <img style="width:100%; height:100%;" src="${proInfo.jyzUrl}"/>
	            </a>
	        </li>
	        </c:if>
	        <c:if test="${proInfo.rgdUrl ne null and proInfo.rgdUrl ne ''}">
	        <li>
	            <a href="${proInfo.rgdUrl}">
	                <img style="width:100%; height:100%;" src="${proInfo.rgdUrl}"/>
	            </a>
	        </li>
	        </c:if>
	        <c:if test="${proInfo.xkzUrl ne null and proInfo.xkzUrl ne ''}">
	        <li>
	            <a href="${proInfo.xkzUrl}">
	                <img style="width:100%; height:50%;" src="${proInfo.xkzUrl}"/>
	            </a>
	        </li>
	        </c:if>
        </ul>
    </div>
</div>
</body>
</html>
