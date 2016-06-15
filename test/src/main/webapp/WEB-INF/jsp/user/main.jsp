<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>User Overview</title>
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
		<h2>User Overview</h2>
		<div id="msg-zone">
			<c:if test="${not empty param.errors}">
				<p style="color: red;">${errors}</p>
			</c:if>
			<c:if test="${not empty param.msg}">
				<p>${param.msg}</p>
			</c:if>
		</div>
		<p style="text-align: right;">
			<a href="<c:url value="/user/add" />">Add</a>
		</p>
		<table>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Email</th>
				<th>SMS</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
			<c:forEach items="${users}" var="user">
				<tr class="data-row">
					<td>${user.user_id}</td>
					<td>${user.user_name}</td>
					<td>${user.email}</td>
					<td>${user.sms}</td>
					<td><a
						href="<c:url value='/user/edit?userInfo.user_id=${user.user_id}' />">edit</a></td>
					<td><a
						href="<c:url value='/user/delete?userInfo.user_id=${user.user_id}'/>">delete</a></td>
				</tr>
			</c:forEach>
			<c:if test="${empty users}">
				<tr>
					<td colspan="6">No data</td>
				</tr>
			</c:if>
		</table>
	</div>
</body>
</html>