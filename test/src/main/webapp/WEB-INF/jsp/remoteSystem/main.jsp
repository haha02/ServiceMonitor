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
		<h2>Remote System Overview</h2>
		<div id="msg-zone">
			<c:if test="${not empty param.errors}">
				<p style="color: red;">${param.errors}</p>
			</c:if>
			<c:if test="${not empty param.msg}">
				<p>${param.msg}</p>
			</c:if>
		</div>
		<p style="text-align: right;">
			<a href="<c:url value="/remoteSystem/add" />">Add</a>
		</p>
		<table>
			<tr>
				<th>ID*</th>
				<th>Checker Type</th>
				<th>Connection String</th>
				<th>Hints</th>
				<th>Check Period</th>
				<th>Period Unit</th>
				<th>Auto Start</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
			<c:forEach items="${remoteSystems}" var="remoteSystem">
				<tr class="data-row">
					<td>${remoteSystem.system_id}</td>
					<td>${remoteSystem.checker_type}</td>
					<td>${remoteSystem.connection_string}</td>
					<td>${remoteSystem.hints}</td>
					<td>${remoteSystem.check_period}</td>
					<td>${remoteSystem.period_unit}</td>
					<td>${remoteSystem.auto_start}</td>
					<td><a
						href="<c:url value='/remoteSystem/edit?system_id=${remoteSystem.system_id}' />">edit</a></td>
					<td><a
						href="<c:url value='/remoteSystem/delete?system_id=${remoteSystem.system_id}'/>">delete</a></td>
				</tr>
			</c:forEach>
			<c:if test="${empty remoteSystems}">
				<tr>
					<td colspan="9">No data</td>
				</tr>
			</c:if>
		</table>
	</div>
</body>
</html>