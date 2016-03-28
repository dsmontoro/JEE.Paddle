<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<title>Spring MVC.CreateSuccess</title>
</head>

<body>
	<H1>Creación de pista correcto</H1>

	<h3>La pista ${id} ha sido creada satisfactoriamente</h3>

	<p><a href="<c:url value='/show-courts' />">Ir a Lista de Pistas</a></p>

	<p>UPM-MIW --- ${now}</p>

</body>
</html>