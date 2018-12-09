<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Admin - Group 6</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@include file="styling.jsp"%>

</head>

<body>
<%@include file="navbar.jsp"%>
<div class="container">
    <p>This is the admin page.</p>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Username</th>
            <th scope="col">Full Name</th>
            <th scope="col">Email Address</th>
            <th scope="col">User Role</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="u" items="${userList}">
            <tr>
                <td>${u.username}</td>
                <td>${u.name}</td>
                <td>${u.email}</td>
                <td>${u.role}</td>
                <td><a href="admin/account/${u.id}">Edit</a>, <a href="admin/delete/${u.id}">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>