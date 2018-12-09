<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
  <meta charset="utf-8">
  <title>Movies - Group 6</title>

  <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <%@include file="styling.jsp"%>

</head>

<body>
  <%@include file="navbar.jsp"%>
  <div class="container">
    <b>${message}</b>
    <p> Hello world! </p>
    <c:if test="${not empty firstName}">
      <div class="alert alert-success">Account successfully created. Welcome, ${firstName}!</div>
    </c:if>
  </div>
</body>
</html>
