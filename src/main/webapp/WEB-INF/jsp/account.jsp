<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account - ${user.username}</title>
    <%@include file="styling.jsp"%>
</head>

<body>
    <%@include file="navbar.jsp" %>
    <div class="container">
        <h2>${user.firstName} ${user.lastName}</h2>
        <h3>${user.username}</h3>
    </div>
    </body>

</html>
