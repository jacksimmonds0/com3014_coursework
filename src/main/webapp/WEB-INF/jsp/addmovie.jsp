<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Add a movie</title>
    <%@include file="styling.jsp"%>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <jsp:useBean id="now" class="java.util.Date" />
    <fmt:formatDate var="year" value="${now}" pattern="yyyy" />
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="<c:url value="/resources/js/selectize.min.js" />"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/selectize.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/addmovie.css" />">

    <script type="text/javascript" src="/resources/js/addmovie.js"></script>
</head>
<body>
<%@include file="navbar.jsp"%>

<h3 align="center">Welcome, enter the movie details</h3>
<form:form id="addmovieForm" modelAttribute="movie" method="POST" action="addmovieProcess">
    <table align="center">
        <tr>
            <td><form:label path="year">Year</form:label></td>
            <td><form:input path="year" id = "year" value="${year}"/></td>
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
            <td><form:label path="posterUrl">Image URL for Poster:</form:label></td>
            <td><form:input type="text" path="posterUrl" id="poster-url" /></td>
        </tr>
        <tr>
            <td>
                <p id="note">(Note: image must be of <br> size at least 200x300px)</p>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>

</body>
</html>
