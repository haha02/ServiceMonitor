<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Edit User</title>
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
		<h2>Edit User</h2>
		<form action="<c:url value="/user/editSubmit" />" method="post">
			<table id="input-table">
				<tr>
					<td class="label">ID*</td>
					<td>${form.user_id}</td>
				</tr>
				<tr>
					<td class="label">Name</td>
					<td class="input"><input name="user_name" value="${form.user_name}" /></td>
				</tr>
				<tr>
					<td class="label">Email</td>
					<td class="input"><input name="email" value="${form.email}" /></td>
				</tr>
				<tr>
					<td class="label">SMS</td>
					<td class="input"><input name="sms" value="${form.sms}" /></td>
				</tr>
				<tr>
					<td class="label"></td>
					<td id="submit"><input type="submit" /></td>
				</tr>
			</table>
			<input type="hidden" name="user_id" value="${form.user_id}" />
		</form>
	</div>
</body>
</html>