<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logged out</title>
    <%@include file="styling.jsp"%>
</head>

<body>
    <%@include file="navbar.jsp" %>
    <div class="container">
        <div class="alert alert-success"><b>${username}</b> has been successfully logged out.</div>
    </div>
    </body>

</html>
