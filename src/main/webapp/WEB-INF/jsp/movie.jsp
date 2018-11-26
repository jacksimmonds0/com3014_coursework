<%--
  Created by IntelliJ IDEA.
  User: niall
  Date: 23/11/2018
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${movie.title}</title>
    <%@include file="styling.jsp"%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
    <link rel="stylesheet" href="<c:url value="/resources/css/fontawesome-stars.css" />">
    <script src="<c:url value="/resources/js/jquery.barrating.min.js" />"></script>
    <script type="text/javascript">
        function getAvgRating(){
            $.ajax({
                url:"getAvgRating",
                type:"POST",
                data: $.param({movieID: ${movie.id}}),
                success: function(data){
                    $('#avg-rating').barrating('set', data);
                }
            });
        }

        $(function() {
            <c:choose>
            <c:when test="${!empty currentUser}">
            $('#my-rating').barrating({
                theme: 'fontawesome-stars',
                initialRating:
                    <c:choose>
                    <c:when test="${!empty individualRating}">
                    ${individualRating}
                    </c:when>
                    <c:otherwise>
                    0.0
                </c:otherwise>
                </c:choose>
                ,
                onSelect: function(value, text) {
                    $.ajax({
                        url:"addRating",
                        type:"POST",

                        data: $.param({rating: value,movieID: ${movie.id}}),
                        success: function(data){
                            console.log(data);
                            getAvgRating();
                        }
                    });
                }
            });
            </c:when>
            </c:choose>


            $('#avg-rating').barrating({
                theme: 'fontawesome-stars',
                readonly: true,
                initialRating: getAvgRating()
            });


        });
    </script>
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
