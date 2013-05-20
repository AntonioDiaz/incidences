<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>gestión de técnicos</title>
<style type="text/css">
.listStyle tr:hover td {
	background-color: #ccc;
}
</style>
</head>
<body>
	<table width="80%">
		<tr>
			<td>
				<h1>Lista de técnicos registrados</h1>
			</td>
			<td><a href="/">inicio</a></td>
		</tr>
	</table>
	<button onclick="window.location='gojsp?jsp_target=technicians_new.jsp'"
		style="cursor: pointer;">nuevo</button>
	<hr>
	<table border="0px" cellpadding="0px" cellspacing="0px" width="90%"
		class="listStyle">
		<tr style="font-weight: bold;">
			<td>google id</td>
			<td>nombre</td>
			<td>teléfono</td>
			<td>último registro</td>
			<td>GPS</td>
			<td>&nbsp;</td>
		</tr>
		<c:forEach items="${technicians_list}" var="myTechnician">
			<tr>
				<td><c:out value="${myTechnician.googleAccountId}"></c:out><br></td>
				<td><c:out value="${myTechnician.name}"></c:out><br></td>
				<td><c:out value="${myTechnician.phoneNumber}"></c:out><br></td>
				<td><fmt:formatDate value="${myTechnician.lastRegistrationDate}" pattern="yyyy-MM-dd" /><br></td>
				<td><c:out value="${myTechnician.gpsFormated}"></c:out><br></td>
				<td>
					<c:url value="/deleteTechnician" var="deleteLink">
						<c:param name="key" value="${myTechnician.googleAccountId}" />
					</c:url> 
					<a href='<c:out value="${deleteLink}" escapeXml="true"/>'>borrar</a>
					<c:url value="/updateTechnician" var="updateLink">
						<c:param name="key" value="${myTechnician.googleAccountId}" />
					</c:url> 
					<a href='<c:out value="${updateLink}" escapeXml="true"/>'>modificar</a>
				</td>
			</tr>
		</c:forEach>
	</table>


</body>
</html>