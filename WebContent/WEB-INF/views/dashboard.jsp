<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
			<h1>List of computers</h1>
			<h3 id="homeTitle">${pageManager.getLength() } computer(s) found</h3>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="ListComputer" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> 
						<input type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="AddComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
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
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${pageManager.getCurrentPage()}" var="cpu">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${cpu.id }"></td>
							<td><a href="EditComputer?computerId=${cpu.id }" onclick="">${cpu.name}</a></td>
							<td>${ cpu.introducedDate.toString().substring(9, 19)}</td>
							<!-- substring pour clean l'affichage du optional -->
							<td>${ cpu.discontinuedDate.toString().substring(9, 19)}</td>
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
					href="?startIndex=${ pageManager.getPreviousPageIndex() }&search=${ search }"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				<li><a style="pointer-events: none; cursor: default;">${pageManager.index }-${pageManager.index + pageManager.rowByPage}
				</a></li>
				<li><a href="?startIndex=${ pageManager.getNextPageIndex() }&search=${ search }"
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