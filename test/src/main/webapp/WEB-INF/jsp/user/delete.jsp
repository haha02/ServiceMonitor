<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Delete User</title>
<link rel=stylesheet type="text/css"
	href="<c:url value="/css/add.css" />">
</head>
<body>
	<div id="page-wrapper">
		<div id="header">
			<p style="text-align: right;">
				<a href="<c:url value="/user/" />">Back</a>
			</p>
		</div>
		<h2>Delete User</h2>
		<form action="<c:url value="/user/deleteSubmit" />" method="post">
			<table id="input-table">
				<tr>
					<td class="label">ID*</td>
					<td>${form.userInfo.user_id}</td>
				</tr>
				<tr>
					<td class="label">Name</td>
					<td>${form.userInfo.user_name}</td>
				</tr>
				<tr>
					<td class="label">Email</td>
					<td>${form.userInfo.email}</td>
				</tr>
				<tr>
					<td class="label">SMS</td>
					<td>${form.userInfo.sms}</td>
				</tr>
				<tr>
					<td class="label"></td>
					<td id="submit"><input type="submit" value="Get rid of this user"/></td>
				</tr>
			</table>
			<input type="hidden" name="userInfo.user_id" value="${form.userInfo.user_id}" />
		</form>
	</div>
</body>
</html>