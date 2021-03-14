<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
<meta charset="UTF-8">
<title>Home</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.1/css/bulma.min.css">
<script defer
	src="https://use.fontawesome.com/releases/v5.1.0/js/all.js"></script>
</head>
<body>

	<div class="container">
		<div class="myheader">
			<p class="cleardisplayblock">
				<c:out value="${user.name}" />
			</p>
			<p class="mini_nav">
				 <a class="logout" href="/logout" ><button style="width: 100px;height: 35px">Logout</button></a>
			</p>

		</div>
		<div >
			<a href="/tasks/new" class="button">Create Task</a>
		</div>
		<div style="display: inline-flex">
			<div style="width: 700px">
			<table class="table table-striped">
				<thead>
					<tr>
						<td>Task</td>
						<td>Creator</td>
						<td>Assignee</td>
						<td>Priority <a class="link" href="/highlow">▼</a>
							<a class="link" href="/lowhigh">▲</a></td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${tasks}"  var="task" >
						<tr>
							<td><a href="/tasks/${task.id}">${task.taskName }</a></td>
							<td>${task.creator.getName()}</td>
							<td>${task.assignee.getName()}</td>
							<c:if test="${task.priority==1}">
								<td>Low</td>
							</c:if>
							<c:if test="${task.priority==2}">
								<td>Medium</td>
							</c:if>
							<c:if test="${task.priority==3}">
								<td>High</td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>

		</div>
	</div>
</body>
</html>