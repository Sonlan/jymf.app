<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<title>中国产品质量追溯查询系统</title>
<link href="${ctx}/html/css/labelcheck.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/html/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
function check(){
    $("#search").submit();
}
</script>
</head>
<body>
<form:form id="search" name="search"  modelAttribute="proInfo" method="post" action="${ctx}/m/consCode" >
<div style="margin:10px;overflow:hidden;">
    <input type="hidden" id="labelId" name="labelId" value="${proInfo.labelId}"/>
	<div class="query_result">
        <div class="labelId">追溯码:${proInfo.labelIdSpace}</div>
    </div>
    <div class="leftcircle"></div>
    <div class="rightcircle"></div> 
    <div class="product_base queryDes"><span>您所查询的是${proInfo.queryDes}</span></div>
      
    <div class="p-content">
        <span style="color: #e9503b;">请输入验证码以获取更多信息</span><br/>
        <table style="width:100%">
            <tr>
               <td width="70%">
                   <input class="text radius" id="consId" name="consId"
                          value="验证码" onblur="if(this.value == '')this.value='验证码';" 
                          onclick="if(this.value == '验证码')this.value='';"/>
               </td>
               <td width="30%">
                  <a class="check" href="javascript:check()"></a>
               </td>
            </tr>
        </table>
        <div id="message" class="error">
        <c:if test="${not empty error}">
            ${error}
        </c:if>
    </div>
    </div>  
</div>
</form:form>

</body>
</html>
