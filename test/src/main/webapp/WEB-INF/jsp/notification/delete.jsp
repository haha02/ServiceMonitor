<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Delete Notification</title>
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
		<h2>Delete Notification</h2>
		<form action="<c:url value="/notification/deleteSubmit" />" method="post">
			<table id="input-table">
				<tr>
					<td class="label">ID*</td>
					<td>${form.system_id}</td>
				</tr>
				<tr>
					<td class="label">Notify Type*</td>
					<td class="input">${form.notify_type}</td>
				</tr>
				<tr>
					<td class="label">User Group*</td>
					<td class="input">${form.user_group}</td>
				</tr>
				<tr>
					<td class="label">Level</td>
					<td class="input">${form.level}</td>
				</tr>
				<tr>
					<td class="label"></td>
					<td id="submit"><input type="submit" /></td>
				</tr>
			</table>
			<input type="hidden" name="system_id" value="${form.system_id}" />
			<input type="hidden" name="notify_type" value="${form.notify_type}" />
			<input type="hidden" name="user_group" value="${form.user_group}" />
		</form>
	</div>
</body>
</html>