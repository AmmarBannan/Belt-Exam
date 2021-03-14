<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page isErrorPage="true" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h3>Welcome ${user.firstName} ${user.lastName}</h3>
<h3>TV Shows</h3>
	<div class="container">
	<div class="top">
	 <a href="/logout" style="float:right">Log Out</a>
	</div>
	<div class="mid">
	 	  <table table-striped>
        <thead>
            <tr>
                <th>Show</th>
                <th>Network</th>
                <th>Avg Rating</th>
            </tr>
        </thead>
        <tbody>
        	<c:forEach items="${shows }" var="item">
	            <tr>
	                <td><a href="/shows/${item.id}">${item.title}</a></td>
	                <td>${item.network}</td>
	                <td>${item.avg}</td>
	            </tr>
            </c:forEach>
            
        </tbody>
    </table>
    </div>
    <div class="bottom">
    <a class="navbar-brand" href="/shows/new">Add a show</a>
    </div>
    </div>
    
    
    
    
   
</body>
</html>