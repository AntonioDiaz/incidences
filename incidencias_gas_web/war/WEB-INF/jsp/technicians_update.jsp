<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Nuevo téchnico</title>
</head>
<body>
	<table width="80%">
		<tr>
			<td>
				<h1>Modificar técnico</h1>
			</td>
			<td><a href="/">inicio</a></td>
		</tr>
	</table>
	<table width="100%" cellpadding="0px" cellspacing="0px">
		<tr>
			<td valign="top">
				<form action="saveUpdateTechnician" id="update_technician" method="post">
					<table cellpadding="0px" cellspacing="0px">
						<tr height="50px">
							<td>
								Id:
							</td>				
							<td>
								<input id="id_account" name="id_account" type="text" size="30" 
									maxlength="50" value='<c:out value="${technicianToModify.googleAccountId}"/>'>
							</td>
						</tr>
						<tr height="50px">
							<td>
								Nombre:
							</td>				
							<td>
								<input id="name" name="name" type="text" size="30" maxlength="50"
									value='<c:out value="${technicianToModify.name}"/>'>
							</td>
						</tr>
						<tr height="50px">
							<td>
								Teléfono:
							</td>				
							<td>
								<input id="phone" name="phone" type="text" size="30" maxlength="50"
									value='<c:out value="${technicianToModify.phoneNumber}"/>'>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="left">
								<button type="button" onclick="document.getElementById('update_technician').submit();">guardar</button>
							</td>
						</tr>		
					</table>			
				</form>			
			</td>
		</tr>
	</table>	
</body>
</html>