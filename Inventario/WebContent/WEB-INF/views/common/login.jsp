<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<link rel="shortcut icon" href="../img/common/favicon.png" type="image/x-icon" />
</head>
<body>
<form action="../j_spring_security_check" method="post">
	<div align="justify" >
<div style="width: 1000px;height: 555px; background:transparent url(../img/common/authB1ackground.png)">
		<div style="padding-top: 15px"></div>
		<div align="center" style="width: 300px;height: 150px;"></div> 
		<div style="padding-top:70px"></div>
		<div style="text-align: left; padding-top:35px;">
			<div style="padding-left: 560px; width: 100px; float: left"><b><font color="#000000" size="3pt">Usuario:</font></b></div>
			<div><input align= "top" type="text" name="j_username" maxlength="20" value="${errors.user}" size="18"></div>
		</div>
		<div style="padding-top: 5px"></div>
		<div style="text-align: left;">
			<div style="padding-left: 560px; width: 100px; float: left"><b><font color="#000000" size="3pt">Contraseña:</font></b></div>
			<div><input type="password" name="j_password" maxlength="20" size="18"></div>
		</div>
		<div id="errors">

	<div style="padding-left: 575px; text-align: center"><font color="#CC0000" size="3.5pt">
	<c:if test="${errors.sessionActiva == true}" >
	La session ya se encuentra activa
	</c:if>
	<c:if test="${errors.loginError == true}" >
	El usuario y/o la contraseña no son validos
	</c:if>
	<c:if test="${errors.sessionMultiple == true}" >
	Ya existe una session abierta en este equipo
	</c:if>
	<c:if test="${errors.accountDisabled == true}" >
	La cuenta esta desabilitada
	</c:if>
	</font>
	</div>

</div>
		<div style="padding-top: 10px"></div>
		<div style="text-align: left;">
			<div style="padding-left: 750px;" ><input type="submit" value="Aceptar"></div>
		</div>
	</div>
</div>
</form>

</body>
</html>