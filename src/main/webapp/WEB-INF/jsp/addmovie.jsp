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
<h3>Welcome, enter the movie details</h3>
<form:form id="addmovieForm" modelAttribute="movie" method="POST" action="addmovieProcess">
    <table>
        <tr>
            <td><form:label path="year">Year</form:label></td>
            <td><form:input path="year"/></td>
        </tr>
        <tr>
            <td><form:label path="title">Name</form:label></td>
            <td><form:input path="title"/></td>
        </tr>
        <tr>
            <td><form:label path="description">Description</form:label></td>
            <td><form:input path="description"/></td>
        </tr>
        <tr>
            <td><form:label path="genres">Genres</form:label></td>
            <td><form:select multiple="true" path="genres">
                <c:forEach items="${genreList}" var="genre">
                    <form:option value="${genre.id}">${genre.name}</form:option>
                </c:forEach>
                    <form:option value="1" label="Comedy"/>
                    <form:option value="2" label="Sci-Fi"/>
                </form:select></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>

    <select>
        <c:forEach items="${genreList}" var="genre">
            <option value="${genre.id}">${genre.name}</option>
        </c:forEach>
        <option value="horror">Horror</option>
        <option value="comedy">Comedy</option>
    </select>
</form:form>

</body>
</html>
