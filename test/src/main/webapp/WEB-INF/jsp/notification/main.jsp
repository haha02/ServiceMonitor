<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Notification Overview</title>
<link rel=stylesheet type="text/css"
	href="<c:url value="/css/main.css" />">
</head>
<body>
	<div id="page-wrapper">
		<div id="header">
			<p style="text-align: right;">
				<a href="<c:url value="/index" />">Back</a>
			</p>
		</div>
		<h2>Notification Overview</h2>
		<div id="msg-zone">
			<c:if test="${not empty param.errors}">
				<p style="color: red;">${param.errors}</p>
			</c:if>
			<c:if test="${not empty param.msg}">
				<p>${param.msg}</p>
			</c:if>
		</div>
		<p style="text-align: right;">
			<a href="<c:url value="/notification/add" />">Add</a>
		</p>
		<table>
			<tr>
				<th>System ID</th>
				<th>Notify Type</th>
				<th>User Group</th>
				<th>Level</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
			<c:forEach items="${notifications}" var="notification">
				<c:set var="queryString" value="system_id=${notification.key.system_id}&notify_type=${notification.key.notify_type}&user_group=${notification.key.user_group}"/>
				<tr class="data-row">
					<td>${notification.key.system_id}</td>
					<td>${notification.key.notify_type}</td>
					<td>${notification.key.user_group}</td>
					<td>${notification.level}</td>
					<td><a
						href="<c:url value='/notification/edit?${queryString}' />">edit</a></td>
					<td><a
						href="<c:url value='/notification/delete?${queryString}'/>">delete</a></td>
				</tr>
			</c:forEach>
			<c:if test="${empty notifications}">
				<tr>
					<td colspan="6">No data</td>
				</tr>
			</c:if>
		</table>
	</div>
</body>
</html>