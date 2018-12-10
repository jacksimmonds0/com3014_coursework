<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<c:url value="/resources/js/navbar.js" />"></script>
<link rel="stylesheet" href="<c:url value="/resources/css/navbar.css" />">

<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">Group 6 Movies</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="/addmovie">Add a Movie</a></li>
            <li><a href="#">Movies</a></li>
            <li><a href="/iplocation">Nearest Cinema</a></li>
            <c:if test="${userRole == 'ROLE_ADMIN'}">
                <li><a href="/admin">Admin</a></li>
            </c:if>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <form id="search-form" class="navbar-form navbar-left" method="get" action="/search">
                <div class="form-group">

                    <div class="dropdown">
                        <input type="text" autocomplete="off" class="form-control" id="search-box" name="term" placeholder="Search">
                        <ul id="search-dropdown" role="menu" class="dropdown-menu" >
                        </ul>
                    </div>

                </div>
                <button type="submit" class="btn btn-primary">
                    <span class="glyphicon glyphicon-search"></span>
                </button>
            </form>

            <c:choose>
                <c:when test="${empty currentUser}">
                    <li><a href="/register"><span class="glyphicon glyphicon-user"></span> Register</a></li>
                    <li><a href="/login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/account"><span class="glyphicon glyphicon-user"></span> ${currentUser.username}</a></li>
                    <li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </c:otherwise>
            </c:choose>

        </ul>
    </div>
</nav>
