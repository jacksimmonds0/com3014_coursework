<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login</title>
    <%@include file="styling.jsp"%>
</head>
<body>
<%@include file="navbar.jsp"%>
<div class="col-xs-6 col-xs-offset-3 col-sm-6 col-sm-offset-3 col-md-4 col-md-offset-4 col-lg-4 col-lg-offset-4">
    <form:form id="loginForm" modelAttribute="login" action="loginProcess" method="post">
        <div class="form-group">
            <form:label path="username">Username</form:label>
            <input path="username" name="username" id="username" class="form-control"/>
        </div>
        <div class="form-group">
            <form:label path="password">Password</form:label>
            <form:password path="password" name="password" id="password" cssClass="form-control"/>
        </div>
        <button id="login" name="login" class="btn btn-primary">Login</button>
        <td><a href="/">Home</a>
        </td>
    </form:form>
    <div>
        <c:if test="${not empty message}">
            <div class="alert alert-danger">${message}</div>
        </c:if>
    </div>
</div>
</body>
</html>