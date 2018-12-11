<%--
  Created by IntelliJ IDEA.
  User: niall
  Date: 23/11/2018
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="commentCount" value="0" scope="page"/>
<html>
<head>
    <title>${movie.title}</title>
    <%@include file="styling.jsp"%>
    <%@include file="movie_script.jsp"%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
    <link rel="stylesheet" href="<c:url value="/resources/css/fontawesome-stars.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/movie.css" />">
    <script src="<c:url value="/resources/js/jquery.barrating.min.js" />"></script>
    <script src="<c:url value="/resources/js/movie.js" />"></script>
</head>
<body>
<%@include file="navbar.jsp" %>
<div class="container">
    <h2>${movie.title} (${movie.year})</h2>
            <div class="col-xs-4 col-sm-4 col-md-2 col-lg-2">
                <c:choose>
                    <c:when test="${!empty movie.posterUrl}">
                        <img class="poster" src="${movie.posterUrl}" alt="${movie.title}" />
                    </c:when>
                    <c:otherwise>
                        <img class="poster" src="https://via.placeholder.com/200x300?No+poster+provided" alt="No poster provided"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <div id="movie-div" class="col-xs-8 col-sm-8 col-md-10 col-lg-10">
                <div class="w-100"></div>
                <div class="col-12">
                    <c:choose>
                        <c:when test="${!empty currentUser}">
                            <div class="rating-container">
                                <h3>My Rating: </h3>
                                <select id="my-rating">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                </select>
                            </div>
                        </c:when>
                    </c:choose>
                    <div class="rating-container">
                        <h3>Average Rating: </h3>
                        <select id="avg-rating">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    </div>

                    <p>
                        <span style="padding-left: 15px;">${movie.genresForSearchResults}</span>
                        <span style="padding-left: 15px;">Directed by: <b>${movie.director.firstName} ${movie.director.lastName}</b></span>
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
        <br>

</div>
<div id="recommendation_container" class="container" style="display:none; padding-top: 10px">
    <div>
        <h3>We see that you like ${movie.title}!</h3>
        <h4>Here are some similar films you may like...</h4>
    </div>
        <div class="recommendation_div col-md-2" style="display:none;">
            <a>
                    <img class="poster-recommended" src="https://via.placeholder.com/130x195?No+poster+provided" alt="No poster provided"/>
            </a>
                <p id="text">
                <a id="title-link"></a></p>
        </div>
</div>
<br>
<div class="container">
    <div class="col-12">
    <c:choose>
    <c:when test="${!empty currentUser}">
        <form action="/movie/${movie.id}/addcomment" id="add-comment-form">
            <div class="form-group">
                <label for="title">Title: </label>
                <input class="form-control" id="title" type="text" name="title" required/>
            </div>
            <div class="form-group">
                <label for="comment">Comment: </label>
                <textarea class="form-control" id="comment" name="comment" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Post Comment</button>

        </form>
    </c:when>
    </c:choose>

    </div>
    <div id="comments-container" class="col-12">
        <c:forEach items="${comments}" varStatus="i" var="comment">
            <article id="comment${commentCount}"class="row">
                <div class="col-md-2 col-sm-2 hidden-xs">
                    <figure class="thumbnail">
                        <img class="img-responsive" src="http://www.tangoflooring.ca/wp-content/uploads/2015/07/user-avatar-placeholder.png">
                        <figcaption class="text-center">${comment.user.username}</figcaption>
                    </figure>
                </div>
                <div class="col-md-10 col-sm-10">
                    <div class="panel panel-default arrow left">
                        <div class="panel-body">
                            <header class="text-left">
                                <div class="comment-title"><h5>${comment.title}</h5></div>
                                <time class="comment-date" datetime="${comment.timestamp}"><i class="fa fa-clock-o"></i>${comment.timestamp}</time>
                            </header>
                            <div class="comment-post">
                                <p>
                                    ${comment.comment}
                                </p>
                            </div>
                                <%--<p class="text-right"><a href="#" class="btn btn-default btn-sm"><i class="fa fa-reply"></i> reply</a></p>--%>
                        </div>
                    </div>
                </div>
            </article>
            <br>
            <c:set var="commentCount" value="${commentCount + 1}" scope="page"/>
        </c:forEach>
    </div>
</div>


</body>
</html>
