<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
<title><spring:message code="label.title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top main-navbar">
		<div class="container">
			<a class="navbar-brand navbar-title" href="ListComputer"><spring:message code="label.title"/> </a>
			<div class="navbar-actions">
				<a href="ListComputer?startIndex=${ pageManager.index }&search=${ search }&sort=${ sort }&lang=fr"><img class="flag flag-fr"></a>
				<a href="ListComputer?startIndex=${ pageManager.index }&search=${ search }&sort=${ sort }&lang=en"><img class="flag flag-en"></a>
				<sec:authorize access="hasRole('ADMIN')">
					<a href="RegisterAdmin" class="btn btn-default navbar-logout-btn"><spring:message code="label.createAdmin"/></a>
				</sec:authorize>
				<a href="logout" class="btn btn-default navbar-logout-btn"><spring:message code="label.logout"/></a>
			</div>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1><spring:message code="label.heading"/></h1>
			<h3 id="homeTitle">${ nbOfComputers } <spring:message code="label.cpuFound"/></h3>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="ListComputer" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> 
						<input type="submit" id="searchsubmit" class="btn btn-primary" value="<spring:message code="label.searchBtn"/>"/>
					</form>
				</div>
				<sec:authorize access="hasRole('ADMIN')">
					<div class="pull-right">
						<a class="btn btn-success" id="addComputer" href="AddComputer">
						<spring:message code="label.addBtn"/></a> 
						<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">
						<spring:message code="label.editBtn"/></a>
					</div>
				</sec:authorize>
			</div>
		</div>

		<form id="deleteForm" action="DeleteComputer" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><spring:message code="label.cpuName"/>
						<c:choose>
						<c:when test="${ sort == 'nameDesc' }">
							<a class="pull-right" href="?startIndex=${ pageManager.getIndex() }&search=${ search }&sort=nameAsc"><i class="fa fa-sort-desc"></i></a>
						</c:when>
						<c:otherwise>
							<a class="pull-right" href="?startIndex=${ pageManager.getIndex() }&search=${ search }&sort=nameDesc"><i class="fa fa-sort-asc"></i></a>
						</c:otherwise>
						</c:choose>
						</th>
						<th><spring:message code="label.introDate"/></th>
						<!-- Table header for Discontinued Date -->
						<th><spring:message code="label.disconDate"/></th>
						<!-- Table header for Company -->
						<th><spring:message code="label.company"/></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${pageManager.getCurrentPage()}" var="cpu">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${cpu.id }"></td>
							<td>
								<sec:authorize access="hasRole('ADMIN')">
									<a href="EditComputer?computerId=${cpu.id }" onclick="">${cpu.name}</a>
								</sec:authorize>
								<sec:authorize access="hasRole('USER')">
									${cpu.name}
								</sec:authorize>
							</td>
							<td>${ cpu.introducedDate}</td>
							<td>${ cpu.discontinuedDate}</td>
							<td>${ cpu.companyName }</td>

						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><a
					href="?startIndex=${ pageManager.getPreviousPageIndex() }&search=${ search }&sort=${ sort }"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				<li><a style="pointer-events: none; cursor: default;">${pageManager.index }-${pageManager.index + pageManager.rowByPage}
				</a></li>
				<li><a href="?startIndex=${ pageManager.getNextPageIndex() }&search=${ search }&sort=${ sort }"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
		</div>

	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>