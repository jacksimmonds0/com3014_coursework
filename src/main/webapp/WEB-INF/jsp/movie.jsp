<%--
  Created by IntelliJ IDEA.
  User: niall
  Date: 23/11/2018
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${movie.title}</title>
    <%@include file="styling.jsp"%>
</head>
<body>
<%@include file="navbar.jsp" %>
<div class="container">
    <h2>${movie.title} (${movie.year})</h2>
            <div class="col-xs-4 col-sm-4 col-md-2 col-lg-2">
                <img src="https://via.placeholder.com/150x225"/>
            </div>

            <div class="col-xs-8 col-sm-8 col-md-10 col-lg-10">
                <div class="w-100"></div>
                <div class="col-12">
                    <p>
                        4.2 <span class="glyphicon glyphicon-star"> </span>
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
<div class="container">

</div>
</body>
</html>
