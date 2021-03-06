<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title><spring:message code="label.title"/></title>
    <meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="https://fonts.googleapis.com/css?family=Montserrat:300,700" rel="stylesheet">
	<link type="text/css" rel="stylesheet" href="css/error.css" media="screen"/>
	
</head>
<body>

	<div id="notfound">
		<div class="notfound">
			<div class="notfound-404">
				<h1>5<span></span><span></span></h1>
			</div>
			<h2><spring:message code="error.500heading"/></h2>
			<p><spring:message code="error.500msg"/></p>
			<a href="ListComputer"><spring:message code="error.backBtn"/></a>
		</div>
	</div>
	
</body><!-- This templates was made by Colorlib (https://colorlib.com) -->
</html>