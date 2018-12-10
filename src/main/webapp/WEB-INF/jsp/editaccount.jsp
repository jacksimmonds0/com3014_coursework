<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account - ${user.username}</title>
    <%@include file="styling.jsp"%>
    <script src="<c:url value="/resources/js/edit_account_admin.js" />"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/account.css" />">
</head>

<body>
<%@include file="navbar.jsp" %>
<div class="container">
    <h2>Admin - Edit Account</h2>
            <form class="account-form">
                <div class="form-group row">
                    <label for="account-username" class="col-md-2 col-form-label">Username</label>
                    <div class="col-md-4">
                        <input class="form-control" type="text" value="${user.username}" id="account-username" disabled>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="account-first-name" class="col-md-2 col-form-label">First Name</label>
                    <div class="col-md-4">
                        <input class="form-control" type="text" value="${user.firstName}" id="account-first-name">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="account-last-name" class="col-md-2 col-form-label">Last Name</label>
                    <div class="col-md-4">
                        <input class="form-control" type="text" value="${user.lastName}" id="account-last-name">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="account-email" class="col-md-2 col-form-label">Email</label>
                    <div class="col-md-4">
                        <input class="form-control" type="email" value="${user.email}" id="account-email">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="account-role" class="col-md-2 col-form-label">User Role</label>
                    <div class="col-md-4">
                        <select class="form-control" id="user-role">
                            <option value="ROLE_USER" >ROLE_USER</option>
                            <option value="ROLE_ADMIN" <c:if test="${user.role == 'ROLE_ADMIN'}">selected</c:if>>ROLE_ADMIN</option>
                        </select>
                    </div>
                </div>
            </form>
            <button type="button" class="btn btn-default" id="update-details-btn">Update Details</button>
            <p id="response-message-details" class="response-message"></p>
        </div>
    </div>
</div>
</body>
</html>