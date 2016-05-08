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
        <c:if test="${proInfo.isCanceled eq '0'}">
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
        </c:if>
        <div class="queryDes"><span>您所查询的是湖南省华文食品有限公司生产的劲仔小鱼。</span></div>
    </div>  
    <div class="leftcircle"></div>
    <div class="rightcircle"></div>          
    <div class="pdtSlide">
         <img class="result_img" src="${ctx}/html/img/${proInfo.companyId}/product.png"/>
    </div> 

    <div class="product_base_title">产品详情 </div> 
    <div class="leftcircle"></div>
    <div class="rightcircle"></div>          
    <div class="product_base">
        <ul>
            <li>产品参数：</li>
            <li>生产许可证编号：430622020161</li>
            <li>产品标准号：DB43/163.3-2009</li>
            <li>厂名：湖南省华文食品有限公司</li>
            <li>厂址：岳阳经济及时开发区康王工业园白石岭南路</li>
            <li>厂家联系方式：0730-8652199</li>
            <li>配料表：鱼，辣椒，芝麻，豆瓣酱，食用油，碘盐，味精，白砂糖，香辛料</li>
            <li>储藏方式：避免阳光直射</li>
            <li>保质期：365天</li>
            <li>产品名：劲仔 混装小鱼600g</li>
            <li>净含量：600g</li>
            <li>包装方式：散装</li>
            <li>产地：湖南省岳阳市</li>
         </ul>
   </div> 
</div>
<div style="text-align:center;margin:20px 0 20px 0;">
    <div style="margin-top:10px;"><a href=" http://www.huawenshipin.com/">点击了解更多</a></div>
</div>
</body>
</html>        