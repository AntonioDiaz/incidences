<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Lista de Incidencias</title>
	<style type="text/css">
		.listStyle tr:hover td{
    		background-color:#ccc;
		}
</style>	
</head>
<body>
	<table width="80%">
		<tr>
			<td>
				<h1>Lista de dispositivos registrados:</h1>			
			</td>
			<td>
				<a href="/">inicio</a>			
			</td>
		</tr>
	</table>
	<hr>
	<table border="0px" cellpadding="0px" cellspacing="0px" width="90%" class="listStyle">
		<tr style="font-weight: bold;">
			<td>fecha</td>
			<td>contacto</td>
			<td>teléfono</td>
			<td>dirección</td>
			<td>descripción</td>
			<td>&nbsp;</td>
		</tr>
		<c:forEach items="${incidences_list}" var="myIncidence">
			<tr>
				<td><fmt:formatDate pattern="dd/MM/yyyy hh:mm" value="${myIncidence.incidenceDate}"/></td>
				<td><c:out value="${myIncidence.contactName}"></c:out><br></td>
				<td><c:out value="${myIncidence.contactPhone}"></c:out><br></td>
				<td><c:out value="${myIncidence.incidenceAddressNoGPS}"></c:out><br></td>
				<td><c:out value="${myIncidence.incidenceDesc}"></c:out><br></td>
				<td>
					<c:url value="/deleteIncidence" var="deleteLink">
						<c:param name="key" value="${myIncidence.id}"/>
					</c:url>
					<a href='<c:out value="${deleteLink}" escapeXml="true"/>'>borrar</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	
</body>
</html>