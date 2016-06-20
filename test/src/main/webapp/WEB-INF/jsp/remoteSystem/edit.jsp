<%@page import="com.test.servicemonitor.persistance.RemoteSystem.PeriodUnit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Edit Remote System</title>
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
		<h2>Edit Remote System</h2>
		<form action="<c:url value="/remoteSystem/editSubmit" />" method="post">
			<table id="input-table">
				<tr>
					<td class="label">ID*</td>
					<td>${form.system_id}</td>
				</tr>
				<tr>
					<td class="label">Checker Type*</td>
					<td class="input">
						<select name="checker_type">
							<c:forEach items="${form.supportedCheckerTypes}" var="type">
								<option value="${type}" <c:if test="${type == form.checker_type}">selected</c:if>>${type}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">Connection String*</td>
					<td class="input"><input name="connection_string" value="${form.connection_string}"/></td>
				</tr>
				<tr>
					<td class="label">Hints</td>
					<td class="input"><input name="hints" value="${form.hints}"/></td>
				</tr>
				<tr>
					<td class="label">Check Period*</td>
					<td class="input"><input name="check_period" value="${form.check_period}"/></td>
				</tr>
				<tr>
					<td class="label">Period Unit*</td>
					<td class="input">
						<select name="period_unit">
							<% pageContext.setAttribute("periodUnitValues", PeriodUnit.values());%>
							<c:forEach items="${periodUnitValues}" var="pu">
								<option value="${pu}" <c:if test="${pu == form.period_unit}">selected</c:if>>${pu}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">Auto Start</td>
					<td class="input">
						<select name="auto_start">
							<option value="true" <c:if test="${form.auto_start}">selected</c:if>>true</option>
							<option value="false" <c:if test="${not form.auto_start}">selected</c:if>>false</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label"></td>
					<td id="submit"><input type="submit" /></td>
				</tr>
			</table>
			<input type="hidden" name="system_id" value="${form.system_id}"/>
		</form>
	</div>
</body>
</html>