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
    <script src="<c:url value="/resources/js/movie.js" />"></script>
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
                    if(value<=3){
                        $('#recommendation_container').slideUp();
                        $(".recommendation_div").not(":hidden").slideUp();
                    }
                    $.ajax({
                        url:"addRating",
                        type:"POST",

                        data: $.param({rating: value,movieID: ${movie.id}}),
                        success: function(movies){
                            getAvgRating();
                            if(movies.recommendations != null) {
                                var rec_con = $('#recommendation_container');
                                // rec_con.css("display","");

                                var rec_div = $(".recommendation_div").first().clone();
                                $(".recommendation_div").not(":hidden").remove();
                                for (var i = 0; i < movies.recommendations.length; i++) {
                                    var rec_div1 = rec_div.clone();
                                    rec_div1.find('a').attr('href', "/movie?id=" + movies.recommendations[i].id);
                                    rec_div1.find('a').html(movies.recommendations[i].title);
                                    rec_div1.find('p').append(" (" + movies.recommendations[i].year + ")");
                                    rec_div1.css("display","");
                                    rec_con.append(rec_div1);
                                    // rec_div1.slideDown();
                                }
                                rec_con.slideDown();
                            }

                        }
                    });
                }
            });
            </c:when>
            </c:choose>


            // $('#avg-rating').barrating({
            //     theme: 'fontawesome-stars',
            //     readonly: true,
            //     initialRating: getAvgRating()
            // });

            $("#add-comment-form").on("submit",function(e){
                e.preventDefault();
                var data =  $("#add-comment-form").serialize();
                $.ajax({
                    url:"movie/${movie.id}/addcomment",
                    type:"POST",
                    data: data,
                    success: function(data){
                        console.log(data.username);
                        $("#comments-container").append("<article class=\"row\">\n" +
                            "                            <div class=\"col-md-2 col-sm-2 hidden-xs\">\n" +
                            "                            <figure class=\"thumbnail\">\n" +
                            "                            <img class=\"img-responsive\" src=\"http://www.tangoflooring.ca/wp-content/uploads/2015/07/user-avatar-placeholder.png\">\n" +
                            "                            <figcaption class=\"text-center\">"+data.username+"</figcaption>\n" +
                            "                            </figure>\n" +
                            "                            </div>\n" +
                            "                            <div class=\"col-md-10 col-sm-10\">\n" +
                            "                            <div class=\"panel panel-default arrow left\">\n" +
                            "                            <div class=\"panel-body\">\n" +
                            "                            <header class=\"text-left\">\n" +
                            "                            <div class=\"comment-title\"><h5>"+data.title+"</h5></div>\n" +
                            "                        <time class=\"comment-date\" datetime=\""+data.comment_time+"\"><i class=\"fa fa-clock-o\"></i>"+data.comment_time+"</time>\n" +
                            "                        </header>\n" +
                            "                        <div class=\"comment-post\">\n" +
                            "                            <p>\n" +
                            "                            "+data.comment+"\n" +
                            "                        </p>\n" +
                            "                        </div>\n" +
                            "                        <%--<p class=\"text-right\"><a href=\"#\" class=\"btn btn-default btn-sm\"><i class=\"fa fa-reply\"></i> reply</a></p>--%>\n" +
                            "                        </div>\n" +
                            "                        </div>\n" +
                            "                        </div>\n" +
                            "                        </article><br>");

                    }
                });

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
<div id="recommendation_container" class="container" style="display:none; padding-top: 10px">
    <div>
        <h3>We see that you like ${movie.title}!</h3>
        <h4>Here are some similar films you may like...</h4>
    </div>
        <div class="recommendation_div col-md-2" style="display:none;">
            <img src="https://via.placeholder.com/130x195?No+poster+provided"/>
            <p style="padding-top: 10px">
                <a></a></p>
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
                <input class="form-control" id="title" type="text" name="title"/>
            </div>
            <div class="form-group">
                <label for="comment">Comment: </label>
                <textarea class="form-control" id="comment" name="comment"></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Post Comment</button>

        </form>
    </c:when>
    </c:choose>

    </div>
    <div id="comments-container" class="col-12">
        <c:forEach items="${comments}" varStatus="i" var="comment">
            <article class="row">
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
        </c:forEach>
    </div>
</div>


</body>
</html>
