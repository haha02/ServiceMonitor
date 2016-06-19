<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Delete User Group</title>
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
		<h2>Delete User Group</h2>
		<form action="<c:url value="/userGroup/deleteSubmit" />" method="post">
			<table id="input-table">
				<tr>
					<td class="label">ID*</td>
					<td>${form.group_id}</td>
				</tr>
				<tr>
					<td class="label">Name</td>
					<td class="input">${form.group_name}</td>
				</tr>
				<tr>
					<td class="label">Users</td>
					<td class="input">
							<c:forEach items="${form.members}" var="member" varStatus="sts">
								${member.user.user_id} - ${member.user.user_name}<c:if test="${not sts.last}"><br /></c:if>
							</c:forEach>
					</td>
				</tr>
				<tr>
					<td class="label"></td>
					<td id="submit"><input type="submit" value="Don't need this group anymore"/></td>
				</tr>
			</table>
			<input type="hidden" name="group_id" value="${form.group_id}" />
		</form>
	</div>
</body>
</html>