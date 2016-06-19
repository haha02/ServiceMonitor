<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>User Group Overview</title>
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
		<h2>User Group Overview</h2>
		<div id="msg-zone">
			<c:if test="${not empty param.errors}">
				<p style="color: red;">${param.errors}</p>
			</c:if>
			<c:if test="${not empty param.msg}">
				<p>${param.msg}</p>
			</c:if>
		</div>
		<p style="text-align: right;">
			<a href="<c:url value="/userGroup/add" />">Add</a>
		</p>
		<table>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Users</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
			<c:forEach items="${userGroups}" var="userGroup">
				<tr class="data-row">
					<td>${userGroup.group_id}</td>
					<td>${userGroup.group_name}</td>
					<td>
						<c:forEach items="${userGroup.members}" var="member" varStatus="sts">${member.user.user_id}<c:if test="${not sts.last}">,</c:if></c:forEach>
					</td>
					<td><a
						href="<c:url value='/userGroup/edit?group_id=${userGroup.group_id}' />">edit</a></td>
					<td><a
						href="<c:url value='/userGroup/delete?group_id=${userGroup.group_id}'/>">delete</a></td>
				</tr>
			</c:forEach>
			<c:if test="${empty userGroups}">
				<tr>
					<td colspan="5">No data</td>
				</tr>
			</c:if>
		</table>
	</div>
</body>
</html>