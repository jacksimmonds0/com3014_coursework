<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Results</title>
    <%@include file="styling.jsp"%>
</head>

<body>
    <%@include file="navbar.jsp" %>
    <div class="container">
        <h2>Search Results</h2>
        <p>There are ${fn: length(results)} results for search: <b>${term}</b></p>
        <c:forEach items="${results}" varStatus="i" var="item">
            <p>${item.title}</p>
        </c:forEach>

    </div>
    </body>

</html>
