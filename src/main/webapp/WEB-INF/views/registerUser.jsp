<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<title>Spring MVC. registerUser</title>
<style>.error {	color: red;}</style>
</head>
<body>
	<h1>Registrar un usuario</h1>
	<form:form modelAttribute="user" action="register-user" >
		<p>Nombre de usuario:
			<form:input path="username" placeholder="Username" required="required" />
			<form:errors path="username" cssClass="error" />
		</p>		
		<p>Email:
			<form:input path="email" placeholder="Email" required="required" />
			<form:errors path="email" cssClass="error" />
		</p>
		<p>Password:
			<form:password path="password" placeholder="Password" required="required" showPassword="true" />
		</p>
		<p>Fecha de nacimiento:
		    <input type="date" name="birthdate">
		</p>		
		<p><input type="submit" value="Crear"></p>
	</form:form>

	<a href="<c:url value="/home"/>">Home</a>

	<p>UPM-MIW --- ${now}</p>

</body>
</html>