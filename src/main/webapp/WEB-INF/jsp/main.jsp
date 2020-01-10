<%@ page language="java" pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>欢迎</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/css/jquery-ui.min.css">

<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
</head>

<body>
	<h1>登陆成功</h1>

	<div>username: '${username}'</div>
	<div>password: '${password}'</div>
	<div>rememberMe: '${rememberMe}'</div>

</body>
</html>
