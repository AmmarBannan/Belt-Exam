<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page isErrorPage="true" %>
       <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
       <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 	<a href="/shows">Go Back</a>
 	<hr>
	<div class="container">
	    <h1>Show: ${show.title}</h1>
		<p>Network: ${show.network}</p>
		<h3>User who rated this show</h3>
		<thead>
            <tr>
                <th>Name</th>
                <th>Rating</th>
            </tr>
        </thead>
        <tbody>
        	<c:forEach items="${rates }" var="rate">
	            <tr>
	                <td>${rate.user.firstName} ${rate.user.lastName}               </td>
	                <td>${rate.star}</td>
	            </tr>
            </c:forEach>
        </tbody>
        
        
        
        <form:form method="POST" action="/shows/${show.id}" modelAttribute="rate">
        <p>
         <form:label path="star">addrate</form:label>
       		<form:select  path="star">
       				<option value=1>1</option>
		           <option value=2>2</option>
		           <option value=3>3</option>
		           <option value=4>4</option>
		           <option value=5>5</option>
       		</form:select>
        </p>
		<button type="create">Rate!</button>
	    </form:form>
	    
	    
	     <a href="/shows/${show.id }/edit">Edit</a>
    </div>
</body>
</html>