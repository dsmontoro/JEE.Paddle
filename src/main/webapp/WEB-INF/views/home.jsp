<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8" />
<title>Spring 4 MVC. Home</title>
</head>
<body>
	<center><H1>Página principal de Paddle con vista JSP</H1></center>
	<h3>Funcionalidades</h3>
	<p><a href="<c:url value='/show-courts'/>">- Lista de pistas</a></p>
	<p><a href="<c:url value='/create-court'/>">- Crear pista</a></p>
	<p><a href="<c:url value='/register-user'/>">- Registrar usuario</a></p>
	
	
    <p>UPM-MIW --- ${now}</p>
</body>
</html>