<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Movies - Group 6</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@include file="styling.jsp" %>

</head>

<body>
<%@include file="navbar.jsp" %>
<div class="container">
    <b>${message}</b>
    <p> Hello world! </p>
    <p>Welcome ${firstName}</p>
    <p>${currentUser.firstName}</p>

    <c:forEach items="${recommendations}"  var="movie" varStatus="map" >
        <p>Because you liked: ${movie.key.title}</p>

        <div class="row" style="padding-top: 10px">
            <c:forEach items="${movie.value}" var="recommendedMovie">
                <div class="col-md-2">
                    <img src="https://via.placeholder.com/130x195?No+poster+provided"/>
                    <p style="padding-top: 10px">
                        <a href="/movie?id=${recommendedMovie.id}">${recommendedMovie.title} </a> (${recommendedMovie.year})</p>
                </div>
            </c:forEach>
        </div>
    </c:forEach>



</div>
</body>
</html>
