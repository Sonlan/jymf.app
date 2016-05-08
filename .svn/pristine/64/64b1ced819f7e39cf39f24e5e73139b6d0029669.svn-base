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
        <div class="queryDes"><span>您所查询的是湖南省玉峰食品实业有限公司生产的渔乐天天（酱汁糖醋鱼）。</span></div>
    </div>  
    <div class="leftcircle"></div>
    <div class="rightcircle"></div>          
    <div class="pdtSlide">
         <img class="result_img" src="${ctx}/html/img/${proInfo.companyId}/100006.jpg"/>
    </div> 

    <div class="product_base_title">产品详情 </div> 
    <div class="leftcircle"></div>
    <div class="rightcircle"></div>          
    <div class="product_base">
        <ul>
            <li>产品参数：</li>
            <li>品名：渔乐天天（酱汁糖醋鱼）</li>
            <li>配料表：干鱼、植物油、食用盐、味精、白砂糖、白醋、豆瓣酱、香辛料、5‘呈味核苷酸二钠、实用香精香料</li>
            <li>执行标准号：DBS43/006-2013</li>
            <li>使用方法：开袋即食</li>
            <li>储存方法：低温、干燥、通风</li>
            <li>保质期：9个月</li>
            <li>生产日期：见喷码</li>
            <li>电话：0731-89847777</li>
            <li>传真：0731-89672122</li>
            <li>网址：www.yufengshipin.com</li>
            <li>产地：湖南·岳阳</li>
            <li>生厂商：湖南省玉峰食品实业有限公司</li>
            <li>公司地址：湖南省平江工业园</li>
            <li>邮政编码：414500</li>
            <li>温馨提示：如有胀包，请勿食用！</li>
            <li>消费者服务热线：400-0730-878</li>
            <li>包装设计专利：ZL201230438158.9仿冒必究！</li>
         </ul>
   </div> 
</div>
<div style="text-align:center;margin:20px 0 20px 0;">
    <div style="margin-top:10px;"><a href="http://m.yufengshipin.com/">点击了解更多</a></div>
</div>
</body>
</html>        