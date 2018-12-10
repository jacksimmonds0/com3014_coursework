<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%--
      Created by IntelliJ IDEA.
      User: niall
      Date: 10/12/2018
      Time: 19:13
      To change this template use File | Settings | File Templates.
    --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    $(function(){
        $('#avg-rating').barrating({
            theme: 'fontawesome-stars',
            readonly: true,
            initialRating: getAvgRating()
        });





    });
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