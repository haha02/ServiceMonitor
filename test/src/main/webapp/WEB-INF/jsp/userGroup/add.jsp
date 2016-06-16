<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Add User Group</title>
<link rel=stylesheet type="text/css"
	href="<c:url value="/css/add.css" />">
</head>
<body>
	<div id="page-wrapper">
		<div id="header">
			<p style="text-align: right;">
				<a href="<c:url value="/userGroup/" />">Back</a>
			</p>
		</div>
		<h2>Add User Group</h2>
		<form action="<c:url value="/userGroup/addSubmit" />" method="post">
			<table id="input-table">
				<tr>
					<td class="label">ID*</td>
					<td class="input"><input name="group_id" value="" /></td>
				</tr>
				<tr>
					<td class="label">Name</td>
					<td class="input"><input name="group_name" value="" /></td>
				</tr>
				<tr>
					<td class="label">Users</td>
					<td class="input">
						<select name="userIds" multiple="multiple">
							<c:forEach items="${userInfos}" var="userInfo">
							<option value="${userInfo.user_id}">${userInfo.user_id} - ${userInfo.user_name}</option>
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