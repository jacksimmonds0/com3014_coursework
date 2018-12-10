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
    <div class="col-xs-6 col-xs-offset-3 col-sm-6 col-sm-offset-3 col-md-4 col-md-offset-4 col-lg-4 col-lg-offset-4">
        <div class="form-group">
            <form:label path="year">Year</form:label>
            <form:input path="year" id = "year" value="${year}" cssClass="form-control"/>
        </div>
        <div class="form-group">
            <form:label path="title">Name</form:label>
            <form:input path="title" id = "title" cssClass="form-control"/>
        </div>
        <div class="form-group">
           <form:label path="description">Description</form:label>
            <form:textarea path="description" rows="4" cols="23" id = "description" cssClass="form-control"/>
        </div>
        <div class="form-group">
            <form:label path="genres">Genres</form:label>
            <form:select multiple="true" path="genres" id="select-genre">
                <c:forEach items="${genreList}" var="genre">
                    <form:option value="${genre.id}">${genre.name}</form:option>
                </c:forEach>
            </form:select>
        </div>
        <div class="form-group">
            <form:label path="director">Director</form:label>
            <form:input path="director" id = "director" cssClass="form-control"/>
        </div>
        <div class="form-group">
            <form:label path="actors">Actors</form:label>
            <form:input type="text" path="actors" id="input-tags"/>
        </div>
        <div class="form-group">
            <form:label path="posterUrl">Image URL for Poster:</form:label>
            <form:input type="text" path="posterUrl" id="poster-url" cssClass="form-control"/>
        </div>
        <p id="note">(Note: image must be of <br> size at least 200x300px)</p>
        <input type="submit" class="btn btn-primary" value="Submit"/>
    </div>
</form:form>

</body>
</html>
