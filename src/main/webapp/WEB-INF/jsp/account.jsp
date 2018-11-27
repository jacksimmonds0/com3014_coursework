<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account - ${user.username}</title>
    <%@include file="styling.jsp"%>
    <script src="<c:url value="/resources/js/account.js" />"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/account.css" />">
</head>

<body>
    <%@include file="navbar.jsp" %>
    <div class="container">
        <h2>Account</h2>

        <ul class="nav flex-column nav-tabs" id="v-pills-tab" role="tablist" aria-orientation="vertical">
            <li role="presentation" class="active">
                <a class="nav-link active" id="v-pills-details-tab" data-toggle="pill" href="#v-pills-details" role="tab" aria-controls="v-pills-details" aria-selected="true">Account details</a>
            </li>
            <li role="presentation">
                <a class="nav-link" id="v-pills-change-password-tab" data-toggle="pill" href="#v-pills-change-password" role="tab" aria-controls="v-pills-change-password" aria-selected="false">Change password</a>
            </li>
        </ul>

        <div class="tab-content" id="v-pills-tabContent">
            <div class="tab-pane fade active in" id="v-pills-details" role="tabpanel" aria-labelledby="v-pills-details-tab">
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
                </form>

                <button type="button" class="btn btn-default" id="update-details-btn">Update Details</button>

                <p id="response-message-details" class="response-message"></p>
            </div>

            <div class="tab-pane fade" id="v-pills-change-password" role="tabpanel" aria-labelledby="v-pills-change-password-tab">
                <form class="account-form">
                    <div class="form-group row">
                        <label for="old-password-input" class="col-md-2 col-form-label">Old Password</label>
                        <div class="col-md-4">
                            <input class="form-control" type="password" value="" id="old-password-input">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="new-password-input" class="col-md-2 col-form-label">New Password</label>
                        <div class="col-md-4">
                            <input class="form-control" type="password" value="" id="new-password-input">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="confirm-new-password-input" class="col-md-2 col-form-label">Confirm Password</label>
                        <div class="col-md-4">
                            <input class="form-control" type="password" value="" id="confirm-new-password-input">
                        </div>
                    </div>
                </form>

                <button type="button" class="btn btn-default" id="change-password-btn">Change Password</button>

                <p id="response-message-password" class="response-message"></p>
            </div>
        </div>
    </div>
    </body>

    <input type="hidden" id="user-id" value="${user.id}"/>

</html>