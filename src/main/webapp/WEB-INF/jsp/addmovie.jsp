<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Add a movie</title>
    <%@include file="styling.jsp"%>
</head>
<body>
<%@include file="navbar.jsp"%>
<script src
                ="https://code.jquery.com/jquery-3.3.1.min.js">
</script>
<script src="<c:url value="/resources/js/selectize.min.js" />"></script>
<link rel="stylesheet" href="<c:url value="/resources/css/selectize.css" />">

<script>
    $(function() {
        $('#input-tags').selectize({
            delimiter: ',',
            persist: false,
            create: function(input) {
                return {
                    value: input,
                    text: input
                }
            }
        });
    });

</script>

<script>
    $(function() {
        $('#select-genre').selectize({
            maxItems: 15
        });
    });
</script>

<script>
    $(function() {
        $('#addmovieForm').on('submit', function(e) {
            var fyear = $('#year').val();
            var ftitle = $('#title').val();
            var fdescription = $('#description').val();
            var fgenre = $('#select-genre').val();
            var fdirector = $('#director').val();
            var factors = $('#input-tags').val();

            if(isNaN(fyear) || fyear < 1900 || fyear > (new Date()).getFullYear()+2 ){
                e.preventDefault();
                alert("Please enter a valid year");
            }
        });
    });
</script>

<style type="text/css">
    td {
        padding-top:5px;
        padding-bottom:5px;
        padding-right:5px;
    }

    td:first-child {
        padding-left:20px;
        padding-right:0;
    }
</style>

<h3 align="center">Welcome, enter the movie details</h3>
<form:form id="addmovieForm" modelAttribute="movie" method="POST" action="addmovieProcess">
    <table align="center">
        <tr>
            <td><form:label path="year">Year</form:label></td>
            <td><form:input path="year" id = "year"/></td>
        </tr>
        <tr>
            <td><form:label path="title">Name</form:label></td>
            <td><form:input path="title" id = "title"/></td>
        </tr>
        <tr>
            <td><form:label path="description">Description</form:label></td>
            <td><form:textarea path="description" rows="4" cols="23" id = "description"/></td>
        </tr>
        <tr>
            <td><form:label path="genres">Genres</form:label></td>
            <td><form:select multiple="true" path="genres" id="select-genre">
                <c:forEach items="${genreList}" var="genre">
                    <form:option value="${genre.id}">${genre.name}</form:option>
                </c:forEach>
                </form:select></td>
        </tr>
        <tr>
            <td><form:label path="director">Director</form:label></td>
            <td><form:input path="director" id = "director"/></td>
        </tr>
        <tr>
            <td><form:label path="actors">Actors</form:label></td>
            <td><form:input type="text" path="actors" id="input-tags"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>

</body>
</html>
