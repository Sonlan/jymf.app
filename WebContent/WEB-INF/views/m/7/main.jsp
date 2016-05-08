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
		    <li>产品名称：${proInfo.productName}</li>
	        <c:if test="${proInfo.productProducer ne null and proInfo.productProducer ne ''}">
	            <li>产品厂商：${proInfo.productProducer}</li>
	        </c:if>
           <c:if test="${proInfo.productOrigin ne null and proInfo.productOrigin ne ''}">
               <li>产品产地：${proInfo.productOrigin}</li>
           </c:if>
           <c:if test="${proInfo.productUrl ne null and proInfo.productUrl ne ''}">
               <li>产品网址： <a href="${proInfo.productUrl}">${proInfo.productUrl}</a></li>
           </c:if>
           <c:if test="${proInfo.productDesc ne null and proInfo.productDesc ne ''}">
               <li>产品描述：${proInfo.productDesc}</li>
           </c:if>
         </ul>
   </div> 
           
    <c:if test="${proInfo.productPara.gx ne null}">
	    <div class="product_base_title">规格参数</div>
	    <div class="leftcircle"></div>
        <div class="rightcircle"></div>          
        <div class="product_base">
	        <ul>
	        <c:forEach var="gx" items="${proInfo.productPara.gx}">
	            <li>${gx.name}：${gx.value}</li>
	        </c:forEach>
	        </ul>
	    </div>
    </c:if>
               
    <c:if test="${proInfo.productPara.bz ne null}">
        <div class="product_base_title">工艺流程</div>
        <div class="leftcircle"></div>
        <div class="rightcircle"></div>          
        <div class="product_base">
            <ul>
            <c:forEach var="bz" items="${proInfo.productPara.bz}">
                <li>${bz.name}：${bz.value}</li>
            </c:forEach>
            </ul>
        </div>
    </c:if>
               
    <c:if test="${proInfo.productPara.yl ne null}">
	    <div class="product_base_title">其他信息</div>
	    <div class="leftcircle"></div>
	    <div class="rightcircle"></div>          
	    <div class="product_base">
        <ul>
            <c:forEach var="yl" items="${proInfo.productPara.yl}">
            <li>${yl.name}：${yl.value}</li>
            </c:forEach>
        </ul>
        </div>
    </c:if>
</div>
</body>
</html>
