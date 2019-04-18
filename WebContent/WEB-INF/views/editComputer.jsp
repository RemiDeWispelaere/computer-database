<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="ListComputer"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id: ${computerDto.id }
					</div>
					<h1>Edit Computer</h1>

					<form:form id="editForm" action="EditComputer" method="POST" modelAttribute="computerDto">
						<form:input path="id" type="hidden" value="${computerDto.id }" id="id"
							name="computerId" />
						<fieldset>
							<div class="form-group">
								<form:label path="name">Computer name</form:label> <form:input
									path="name" type="text" class="form-control" id="computerName"
									name="computerName" required="true"/>
							</div>
							<div class="form-group">
								<form:label path="introducedDate">Introduced date</form:label> <form:input
									path="introducedDate" type="date" class="form-control" id="introduced"
									name="introduced" value="${computerDto.introducedDate }"/>
							</div>
							<div class="form-group">
								<form:label path="discontinuedDate">Discontinued date</form:label> <form:input
									path="discontinuedDate" type="date" class="form-control" id="discontinued"
									name="discontinued" value="${computerDto.discontinuedDate}"/>
							</div>
							<div class="form-group">
								<form:label path="companyId">Company</form:label> <form:select
									path="companyId" class="form-control" id="companyId" name="companyId">
										<form:options itemValue="id" items="${ companies }" itemLabel="name"></form:options>
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="ListComputer" class="btn btn-default">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script src="js/jquery.min.js"></script>
	<script
		src="//cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.js"></script>
	<script src="js/formValidation.js"></script>
</body>
</html>