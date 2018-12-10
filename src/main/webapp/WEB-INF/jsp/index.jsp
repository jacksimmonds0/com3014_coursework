<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Movies - Group 6</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@include file="styling.jsp" %>
    <link rel="stylesheet" href="<c:url value="/resources/css/index.css" />">
</head>

<body>
<%@include file="navbar.jsp" %>
<div class="container">
    <c:if test="${not empty message}">
      <div class="alert alert-danger">${message}</div>
    </c:if>
    <h1> Movie Recommendations </h1>
    <c:if test="${not empty firstName}">
      <div class="alert alert-success">Account successfully created. Welcome, ${firstName}!</div>
    </c:if>

    <div class="col-md-12">
        <p id="description">
            Welcome to movie recommendations by group 6, where you can find movies, add movies to our site,
            add your comments and ratings to them as well as find nearest cinemas.

            <c:choose>
                <c:when test="${empty currentUser}">
                    Login to show your recommended movies based on what you have previously rated.
                </c:when>
                <c:otherwise>
                    Here are the movies we have recommended for you:
                </c:otherwise>
            </c:choose>

        </p>
    </div>

    <h4>Recently added movies: </h4>
    <div class="row movies-row">
        <c:forEach items="${recentMovies}" var="movie">
            <div class="col-md-2">

                <c:choose>
                    <c:when test="${!empty movie.posterUrl}">
                        <img class="poster" src="${movie.posterUrl}" alt="${movie.title}" />
                    </c:when>
                    <c:otherwise>
                        <img class="poster" src="https://via.placeholder.com/130x195?No+poster+provided" alt="No poster provided"/>
                    </c:otherwise>
                </c:choose>

                <p class="recommended-movie">
                    <a href="/movie?id=${movie.id}">${movie.title} </a> (${movie.year})
                </p>
            </div>
        </c:forEach>
    </div>

    <c:forEach items="${recommendations}"  var="movie" varStatus="map" >
        <h4>Because you liked: <b>${movie.key.title}</b></h4>

        <div class="row movies-row">
            <c:forEach items="${movie.value}" var="recommendedMovie">
                <div class="col-md-2">

                    <c:choose>
                        <c:when test="${!empty recommendedMovie.posterUrl}">
                            <img class="poster" src="${recommendedMovie.posterUrl}" alt="${recommendedMovie.title}" />
                        </c:when>
                        <c:otherwise>
                            <img class="poster" src="https://via.placeholder.com/130x195?No+poster+provided" alt="No poster provided"/>
                        </c:otherwise>
                    </c:choose>

                    <p class="recommended-movie">
                        <a href="/movie?id=${recommendedMovie.id}">${recommendedMovie.title} </a> (${recommendedMovie.year})
                    </p>
                </div>
            </c:forEach>
        </div>
    </c:forEach>


</div>
</body>
</html>
