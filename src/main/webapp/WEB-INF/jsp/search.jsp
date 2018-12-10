<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Results</title>
    <%@include file="styling.jsp"%>
    <link rel="stylesheet" href="<c:url value="/resources/css/search.css" />">
</head>

<body>
    <%@include file="navbar.jsp" %>
    <div class="container">
        <h2>Search Results</h2>
        <p>There are ${fn: length(results)} results for search: <b>${term}</b></p>

        <c:forEach items="${results}" varStatus="m" var="movie">
            <c:set var="i" value="${m.index}" />
            <div class="row">
                <div class="col-xs-4 col-sm-4 col-md-2 col-lg-2">
                    <c:choose>
                        <c:when test="${!empty movie.posterUrl}">
                            <img class="poster" src="${movie.posterUrl}" />
                        </c:when>
                        <c:otherwise>
                            <img class="poster" src="https://via.placeholder.com/150x225?No+poster+provided"/>
                        </c:otherwise>
                    </c:choose>

                </div>

                <div class="col-xs-8 col-sm-8 col-md-10 col-lg-10">
                    <div class="col-12">
                        <h3> <a href="/movie?id=${movie.id}">${movie.title}</a> (${movie.year})</h3>
                    </div>
                    <div class="w-100"></div>
                    <div class="col-12">
                        <p>
                            <c:out value="${ratings[i]}" /> <span class="glyphicon glyphicon-star"> </span>
                            <span class="movie-sub-info">${movie.genresForSearchResults}</span>
                            <span class="movie-sub-info">Directed by: <b>${movie.director.firstName} ${movie.director.lastName}</b></span>
                        </p>
                    </div>
                    <div class="w-100"></div>
                    <div class="col-4">
                        <p><i>Featuring: ${movie.actorsForSearchResult}</i></p>
                    </div>
                    <div class="w-100"></div>
                    <div class="col-4">
                        <p>${movie.description}</p>
                    </div>
                </div>
            </div>
            <br>
        </c:forEach>

    </div>
    </body>

</html>
