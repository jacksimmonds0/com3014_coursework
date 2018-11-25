<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account - ${user.username}</title>
    <%@include file="styling.jsp"%>
</head>

<body>
    <%@include file="navbar.jsp" %>
    <div class="container">
        <h2>Account</h2>

        <ul class="nav flex-column nav-tabs" id="v-pills-tab" role="tablist" aria-orientation="vertical" style="padding-top: 10px">
            <li role="presentation" class="active">
                <a class="nav-link active" id="v-pills-details-tab" data-toggle="pill" href="#v-pills-details" role="tab" aria-controls="v-pills-details" aria-selected="true">Account details</a>
            </li>
            <li role="presentation">
                <a class="nav-link" id="v-pills-change-password-tab" data-toggle="pill" href="#v-pills-change-password" role="tab" aria-controls="v-pills-change-password" aria-selected="false">Change password</a>
            </li>
        </ul>

        <div class="tab-content" id="v-pills-tabContent">
            <div class="tab-pane fade active in" id="v-pills-details" role="tabpanel" aria-labelledby="v-pills-details-tab">
                <form style="padding-top: 20px">
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

                <p id="response-message-details" style="padding-top: 25px;"></p>
            </div>

            <div class="tab-pane fade" id="v-pills-change-password" role="tabpanel" aria-labelledby="v-pills-change-password-tab">
                <form style="padding-top: 20px">
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

                <p id="response-message-password" style="padding-top: 25px;"></p>
            </div>
        </div>
    </div>
    </body>

<script>
    var updateDetailsBtn = $("#update-details-btn"),
        changePasswordBtn = $("#change-password-btn"),
        oldPassword = $("#old-password-input"),
        newPassword = $("#new-password-input"),
        confirmPassword = $("#confirm-new-password-input");

    updateDetailsBtn.click(function () {
        $.ajax({
            url: "account/" + ${user.id} + "/update/details",
            type: "PUT",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data:  JSON.stringify({
                "first_name": $("#account-first-name").val(),
                "last_name": $("#account-last-name").val(),
                "email": $("#account-email").val()
            }),
            success: function(data) {
                var responseMessage = $("#response-message-details");

                responseMessage.fadeIn('fast');
                responseMessage.text(data.message);

                if(data.error) {
                    responseMessage.css("color", "red");
                }
                else {
                    responseMessage.css("color", "");
                }

                responseMessage.delay(10000).fadeOut('slow');
            }
        })
    });

    changePasswordBtn.click(function () {
        $.ajax({
            url: "account/" + ${user.id} + "/update/password",
            type: "PUT",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify({
                "old_password": btoa(oldPassword.val()),
                "new_password": btoa(newPassword.val()),
                "confirm_password": btoa(confirmPassword.val())
            }),
            success: function(data) {
                var responseMessage = $("#response-message-password");

                responseMessage.text(data.message);

                if(data.error) {
                    responseMessage.css("color", "red");
                }
                else {
                    responseMessage.css("color", "");

                    oldPassword.val("");
                    newPassword.val("");
                    confirmPassword.val("");
                }
            }
        })
    });
</script>

</html>