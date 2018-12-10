<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registration</title>
    <%@include file="styling.jsp"%>
    <script src="<c:url value="/resources/css/account.css" />"></script>
</head>
<body>
<%@include file="navbar.jsp"%>
<form:form id="regForm" modelAttribute="user" action="registerProcess" method="post">
    <div class="col-xs-6 col-xs-offset-3 col-sm-6 col-sm-offset-3 col-md-4 col-md-offset-4 col-lg-4 col-lg-offset-4">
        <div class="form-group">
            <form:label path="username">Username</form:label>
            <form:input path="username" name="username" id="username" cssClass="form-control"/>
            <form:errors path="username" cssClass="error"/>
        </div>
        <div class="form-group">
            <form:label path="password">Password</form:label>
            <form:password path="password" name="password" id="password" cssClass="form-control"/>
            <form:errors path="password" cssClass="error"/>
        </div>
        <div class="form-group">
            <form:label path="confirmPassword">Confirm Password</form:label>
            <form:password path="confirmPassword" name="confirmPassword" id="confirmPassword" cssClass="form-control"/>
            <form:errors path="confirmPassword" cssClass="error"/>
        </div>
        <div class="form-group">
            <form:label path="firstName">First Name</form:label>
            <form:input path="firstName" name="firstName" id="firstName" cssClass="form-control"/>
            <form:errors path="firstName" cssClass="error"/>
        </div>
        <div class="form-group">
            <form:label path="lastName">Last Name</form:label>
            <form:input path="lastName" name="lastName" id="lastName" cssClass="form-control"/>
            <form:errors path="lastName" cssClass="error"/>
        </div>
        <div class="form-group">
            <form:label path="email">Email Address</form:label>
            <form:input path="email" name="email" id="email" cssClass="form-control"/>
            <form:errors path="email" cssClass="error"/>
        </div>
                <button id="register" name="register" class="btn btn-primary">Register</button>
            </td>
        </tr>
        <tr></tr>l
    </table>
    </div>
</form:form>
</body>
</html>