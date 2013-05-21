<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<% UserService userService = UserServiceFactory.getUserService(); %>
<% String thisUrl = request.getRequestURI(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Incidencias</title>
	<style type="text/css">
	
	</style>
</head>
<body>
	<table width="100%" cellpadding="0px" cellspacing="0px">
		<tr>
			<td><h1>Administración de incidencias</h1></td>
			<td width="30%">
				conectado con: <b><%=request.getUserPrincipal().getName()%></b> 
				<a href="<%=userService.createLogoutURL("/")%>">desconectar</a> 
			</td>
		</tr>
		<tr><td colspan="2"><hr></td></tr>	
	</table>
	<h4>Menú</h4>
	<ul>
		<li><a href="incidenceNew">nueva incidencia</a></li>
		<li><a href="home">lista dispositivos</a></li>
		<li><a href="incidencesList">lista incidencias</a></li>
		<li><a href="techniciansList">técnicos registrados</a></li>
	</ul>
</body>
</html>