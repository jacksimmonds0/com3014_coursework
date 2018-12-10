<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Movies - Group 6</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@include file="styling.jsp"%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="<c:url value="/resources/js/nearestcinema.js" />"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2jB5GWdw0t7JKry4Du6-c0PFgwjNe6rc&libraries=places" async defer></script>
</head>

<body>
<%@include file="navbar.jsp"%>
<div class="container">
    <div id="city"></div>
    <div id="map" style="height: 500px; width:100%;"></div>

    <div class="list-group">

    </div>
</div>
</body>
</html>
