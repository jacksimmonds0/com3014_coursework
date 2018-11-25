/**
 * Note: this uses a hidden field for the user ID but this is validated on the server-side
 * against the id of the current session user object
 */

$(document).ready(function () {

    var userId = $("#user-id").val(),
        updateDetailsBtn = $("#update-details-btn"),
        changePasswordBtn = $("#change-password-btn"),
        oldPassword = $("#old-password-input"),
        newPassword = $("#new-password-input"),
        confirmPassword = $("#confirm-new-password-input");

    updateDetailsBtn.click(function () {
        $.ajax({
            url: "account/" + userId + "/update/details",
            type: "PUT",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify({
                "first_name": $("#account-first-name").val(),
                "last_name": $("#account-last-name").val(),
                "email": $("#account-email").val()
            }),
            success: function (data) {
                handleResponse(data, "details");
            }
        })
    });

    changePasswordBtn.click(function () {
        $.ajax({
            url: "account/" + userId + "/update/password",
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
            success: function (data) {
                handleResponse(data, "password");
            }
        })
    });

    /**
     * Handling the response from the server by adding the message to the screen in the correct place/format
     *
     * @param data
     *          the JSON response from the server
     * @param type
     *          the type of response i.e. details or password update
     */
    function handleResponse(data, type) {
        var responseMessage;

        if(type === "password") {
            responseMessage = $("#response-message-password");
        }
        else if(type === "details") {
            responseMessage = $("#response-message-details");
        }

        responseMessage.text(data.message);

        if(data.error) {
            responseMessage.css("color", "red");
        }
        else {
            responseMessage.css("color", "black");

            if(type === "password") {
                oldPassword.val("");
                newPassword.val("");
                confirmPassword.val("");
            }
        }

    }
});

