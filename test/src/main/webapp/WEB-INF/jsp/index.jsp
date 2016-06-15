<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>User Overview</title>
<style type="text/css">
#page-wrapper {
	width: 60%;
	margin: auto;
}

.highlight {
	color: blue;
	font-weight: bold;
}
</style>
</head>
<body>
	<div id="page-wrapper">
		<h1 style="text-align: center;">Service Monitor</h1>
		<table style="width: 80%; margin: auto; font-size: 1.2em;">
			<tr>
				<td class="highlight"><a href="<c:url value="/remoteSystem/" />">Remote System</a></td>
				<td class="highlight"><a href="<c:url value="/notification/" />">Notification</a></td>
				<td><a href="<c:url value="/user/" />">User</a></td>
				<td><a href="<c:url value="/userGroup/" />">User Group</a></td>
			</tr>
		</table>
	</div>
</body>
</html>
