<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>SpringShiro</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/css/jquery-ui.min.css">

<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
</head>

<body>
	<h1>SpringShiro</h1>

	<form id="formID" method="post" action="index.do">
		<div>
			<span>账号:</span> <input type="text" id="usernameID" name="username"
				value="root" />
		</div>
		<div>
			<span>密码:</span> <input type="text" id="passwordID" name="password"
				value="11111111" />
		</div>
		<div>
			<span>记住我?</span> <input type="checkbox" id="rememberMeID"
				name="rememberMe" checked />
		</div>
		<div>
			<input type="submit" id="loginID" name="login" value="登录" />
		</div>
	</form>

	<script type="text/javascript">
		window.onload = function() {
			if (window != top) {
				window.top.location = "/login.do";
			}
		}
	</script>
</body>
</html>
