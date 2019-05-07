<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title><spring:message code="label.title" /></title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="limiter">
		<div class="container-login100"
			style="background-image: url('img/background.jpg');">
			<div class="wrap-login100 p-t-190 p-b-30">
				<form:form id="registerForm" action="Register" method="POST" modelAttribute="user" class="login100-form validate-form">
					<div class="login100-form-avatar">
						<img src="img/register.png" alt="REGISTER">
					</div>

					<span class="login100-form-title p-t-20 p-b-45"> <spring:message
							code="login.create" />
					</span>

					<div class="wrap-input100 validate-input m-b-10"
						data-validate="Username is required">
						<spring:message	code="login.username" var="namePlaceholder"/>
						<form:label path="username"/><form:input path="username" class="input100" type="text" name="username"
							placeholder="${ namePlaceholder }"/> <span class="focus-input100"></span>
						<span class="symbol-input100"> <i class="fa fa-user"></i>
						</span>
					</div>

					<div class="wrap-input100 validate-input m-b-10"
						data-validate="Password is required">
						<spring:message	code="login.password" var="passwordPlaceholder"/>
						<form:label path="password"/><form:input path="password" class="input100" type="password" name="password"
							placeholder="${ passwordPlaceholder }"/> <span class="focus-input100"></span>
						<span class="symbol-input100"> <i class="fa fa-lock"></i>
						</span>
					</div>

					<div class="container-login100-form-btn p-t-10">
						<input type="submit" class="login100-form-btn register-form-btn register-form-btn-validate" value="<spring:message code="label.validateBtn" />">
						<div style="width:10%;"></div>
						<a href="Login" class="login100-form-btn register-form-btn register-form-btn-cancel"><spring:message code="label.cancelBtn"/></a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<script src="js/jquery.min.js"></script>
	<script
		src="//cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.js"></script>
	<script src="js/formValidation.js"></script>
</body>
</html>