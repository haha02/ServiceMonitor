<%@page import="com.test.servicemonitor.persistance.Notification"%>
<%@page import="com.test.servicemonitor.check.FailLevel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Add Notification</title>
<link rel=stylesheet type="text/css"
	href="<c:url value="/css/add.css" />">
</head>
<body>
	<div id="page-wrapper">
		<div id="header">
			<p style="text-align: right;">
				<a href="<c:url value="/notification/" />">Back</a>
			</p>
		</div>
		<h2>Add Notification</h2>
		<form action="<c:url value="/notification/addSubmit" />" method="post">
			<table id="input-table">
				<tr>
					<td class="label">System ID*</td>
					<td class="input">
						<select name="system_id">
							<c:forEach items="${remoteSystems}" var="remoteSystem">
							<option value="${remoteSystem.system_id}">${remoteSystem.system_id}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">Notify Type*</td>
					<td class="input">
					<% pageContext.setAttribute("types", Notification.Types.values()); %>
						<select name="notify_type">
							<c:forEach items="${types}" var="type">
							<option value="${type}">${type}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">User Group*</td>
					<td class="input">
						<select name="user_group">
							<c:forEach items="${userGroups}" var="userGroup">
							<option value="${userGroup.group_id}">${userGroup.group_id}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">Level</td>
					<td class="input">
					<% pageContext.setAttribute("levels", FailLevel.valuesByWeight()); %>
						<select name="level">
							<c:forEach items="${levels}" var="level">
							<option value="${level}">${level}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label"></td>
					<td id="submit"><input type="submit" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>