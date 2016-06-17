<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>User Overview</title>
<style>
#page-wrapper {
	width: 60%;
	margin: auto;
}

.highlight {
	font-weight: bold;
}
h1 {
	text-align: center;
}

table.content td, table.content th {
	text-align: left;
	padding: 1px 3px 1px 3px;
	border: 1px solid black;
}
table.content {
	width: 100%;
	border: 1px solid black;
	border-collapse: collapse;
}

</style>
</head>
<body>
	<div id="page-wrapper">
		<h1>Service Monitor</h1>
		<table style="width: 80%; margin: auto; font-size: 1.2em;">
			<tr>
				<td class="highlight"><a href="<c:url value="/remoteSystem/" />">Remote System</a></td>
				<td class="highlight"><a href="<c:url value="/notification/" />">Notification</a></td>
				<td><a href="<c:url value="/user/" />">User</a></td>
				<td><a href="<c:url value="/userGroup/" />">User Group</a></td>
			</tr>
		</table>
		<hr />
		<h2>System Status</h2>
		<table class="content" style="width: 100%; margin: auto;">
			<tr>
				<th>ID</th>
				<th>Monitored</th>
				<th>Alive</th>
				<th>Last Check Time</th>
				<th>Operation</th>
			</tr>
			<c:forEach items="${statusList}" var="status">
			<tr>
				<td>${status.system_id}</td>
				<td>${status.monitoring}</td>
				<td>${status.monitoring ? status.alive : '-'}</td>
				<td>${status.monitoring ? status.last_check_time : '-'}</td>
				<td>
				<c:if test="${not status.monitoring}">	
					<a href="./?start=${status.system_id}">Start</a>
				</c:if>
				<c:if test="${status.monitoring}">
					<a href="./?stop=${status.system_id}">Stop</a>
				</c:if>
				</td>
			</tr>
			</c:forEach>
			<c:if test="${empty statusList}">
				<td colspan="4">No data</td>
			</c:if>
		</table>
	</div>
</body>
</html>
