<%@page import="com.test.servicemonitor.persistance.RemoteSystem.PeriodUnit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Add Remote System</title>
<link rel=stylesheet type="text/css"
	href="<c:url value="/css/add.css" />">
</head>
<body>
	<div id="page-wrapper">
		<div id="header">
			<p style="text-align: right;">
				<a href="<c:url value="/remoteSystem/" />">Back</a>
			</p>
		</div>
		<h2>Add Remote System</h2>
		<form action="<c:url value="/remoteSystem/addSubmit" />" method="post">
			<table id="input-table">
				<tr>
					<td class="label">ID*</td>
					<td class="input"><input name="system_id" /></td>
				</tr>
				<tr>
					<td class="label">Checker Type*</td>
					<td class="input">
						<select name="checker_type">
							<c:forEach items="${form.supportedCheckerTypes}" var="type">
							<option value="${type}">${type}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">Connection String*</td>
					<td class="input"><input name="connection_string" /></td>
				</tr>
				<tr>
					<td class="label">Hints</td>
					<td class="input"><input name="hints" /></td>
				</tr>
				<tr>
					<td class="label">Check Period*</td>
					<td class="input"><input name="check_period" /></td>
				</tr>
				<tr>
					<td class="label">Period Unit*</td>
					<td class="input">
						<select name="period_unit">
							<% pageContext.setAttribute("periodUnitValues", PeriodUnit.values());%>
							<c:forEach items="${periodUnitValues}" var="pu">
								<option value="${pu}">${pu}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">Disabled</td>
					<td class="input">
						<select name="disabled">
							<option value="true">true</option>
							<option value="false">false</option>
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