<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register Page</title>
    <%@include file="styling.jsp"%>
</head>
<body>
<%@include file="navbar.jsp" %>
<form class="form-horizontal">
    <div class="form-group">
        <label class="control-label col-sm-2" for="username">Username:</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="username" placeholder="Enter username">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2" for="email">Email:</label>
        <div class="col-sm-10">
            <input type="email" class="form-control" id="email" placeholder="Enter email">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2" for="firstname">First Name:</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="firstname" placeholder="Enter first name">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2" for="lastname">Last Name:</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="lastname" placeholder="Enter email">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2" for="pwd">Password:</label>
        <div class="col-sm-10">
            <input type="password" class="form-control" id="pwd" placeholder="Enter password">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <div class="checkbox">
                <label><input type="checkbox"> I agree to the terms and conditions</label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default">Register</button>
        </div>
    </div>
</form>
</body>
</html>
