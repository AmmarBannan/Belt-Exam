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
	<div class="container">
	 	<h1>Create a Show!</h1>
	    </hr>
	    <p><form:errors path="show.*"/></p>
	    
	    <form:form method="POST" action="/shows/new" modelAttribute="show">
	     	<p>
	            <form:label path="title">Name:</form:label>
	            <form:input type="text" path="title"/>
	        </p>
	        <p>
	        	<form:label path="network">network:</form:label>
	            <form:select  path="network">
                    <c:forEach items="${ states }" var="state">
                        <option value="${ state }">${ state }</option>
                    </c:forEach>
                </form:select>
	        </p>
	        <button type="create">Create</button>
	    </form:form>
	    <a href="/shows">back</a>
    </div>
</body>
</html>