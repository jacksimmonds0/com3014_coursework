<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registration</title>
    <%@include file="styling.jsp"%>
</head>
<body>
<%@include file="navbar.jsp"%>
<form:form id="regForm" modelAttribute="user" action="registerProcess" method="post">
    <table align="center">
        <tr>
            <td>
                <form:label path="username">Username</form:label>
            </td>
            <td>
                <form:input path="username" name="username" id="username" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="password">Password</form:label>
            </td>
            <td>
                <form:password path="password" name="password" id="password" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="confirmPassword">Confirm Password</form:label>
            </td>
            <td>
                <form:password path="confirmPassword" name="confirmPassword" id="confirmPassword" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="firstName">First Name</form:label>
            </td>
            <td>
                <form:input path="firstName" name="firstName" id="firstName" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="lastName">Last Name</form:label>
            </td>
            <td>
                <form:input path="lastName" name="lastName" id="lastName" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="email">Email Address</form:label>
            </td>
            <td>
                <form:input path="email" name="email" id="email" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <form:button id="register" name="register">Register</form:button>
            </td>
        </tr>
        <tr></tr>
        <tr>
            <td></td>
            <td><a href="index.jsp">Home</a>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>