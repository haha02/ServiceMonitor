<%@page import="com.test.servicemonitor.persistance.RemoteSystem.PeriodUnit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Delete Remote System</title>
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
		<h2>Delete Remote System</h2>
		<form action="<c:url value="/remoteSystem/deleteSubmit" />" method="post">
			<table id="input-table">
				<tr>
					<td class="label">ID*</td>
					<td>${form.system_id}</td>
				</tr>
				<tr>
					<td class="label">Checker Type*</td>
					<td class="input">${form.checker_type}</td>
				</tr>
				<tr>
					<td class="label">Connection String*</td>
					<td class="input">${form.connection_string}</td>
				</tr>
				<tr>
					<td class="label">Hints</td>
					<td class="input">${form.hints}</td>
				</tr>
				<tr>
					<td class="label">Check Period*</td>
					<td class="input">${form.check_period}</td>
				</tr>
				<tr>
					<td class="label">Period Unit*</td>
					<td class="input">${form.period_unit}</td>
				</tr>
				<tr>
					<td class="label">Auto Start</td>
					<td class="input">${form.auto_start}</td>
				</tr>
				<tr>
					<td class="label"></td>
					<td id="submit"><input type="submit" value="Get rid of this remote system"/></td>
				</tr>
			</table>
			<input type="hidden" name="system_id" value="${form.system_id}"/>
		</form>
	</div>
</body>
</html>