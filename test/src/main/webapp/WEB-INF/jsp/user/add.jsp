<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Add User</title>
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
		<h2>Add User</h2>
		<form action="<c:url value="/user/addSubmit" />" method="post">
			<table id="input-table">
				<tr>
					<td class="label">ID*</td>
					<td class="input"><input name="userInfo.user_id" value="" /></td>
				</tr>
				<tr>
					<td class="label">Name</td>
					<td class="input"><input name="userInfo.user_name" value="" /></td>
				</tr>
				<tr>
					<td class="label">Email</td>
					<td class="input"><input name="userInfo.email" value="" /></td>
				</tr>
				<tr>
					<td class="label">SMS</td>
					<td class="input"><input name="userInfo.sms" value="" /></td>
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