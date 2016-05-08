<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<title>中国产品质量追溯查询系统</title>
<meta content="http://www.cpzhuisu.com/" name="author" />
<meta content="Copyright &copy;http://www.cpzhuisu.com/ 版权所有" name="copyright" />
<meta content="7 days" name="revisit-after" />

<meta content="" name="keywords" />
<meta content="" name="description" />
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<link href="${ctx}/html/css/labelcheck.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/html/js/jquery-1.9.1.min.js" type="text/javascript"></script>

<script type="text/javascript">
function check(){
    $("#search").submit();
}
</script>
</head>
<body>
<form:form id="search" name="search"  modelAttribute="proInfo" method="post" action="${ctx}/app/getTrackRecords" >
<input name="userid" value="qbrj5722">
<input value="提交" type="button" onclick="check()">
</form:form>

</body>
</html>
