$(document).ready(function () {

    var userId = window.location.pathname.split("/").pop(),
        updateDetailsBtn = $("#update-details-btn");

    updateDetailsBtn.click(function () {
        $.ajax({
            url: "/admin/account/" + userId + "/edit",
            type: "PUT",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify({
                "first_name": $("#account-first-name").val(),
                "last_name": $("#account-last-name").val(),
                "email": $("#account-email").val(),
                "user_role": $("#user-role").val()
            }),
            success: function (data) {
                var responseMessage = $("#response-message-details");

                responseMessage.text(data.message);

                if(data.error) {
                    responseMessage.css("color", "red");
                }
                else {
                    responseMessage.css("color", "black");
                }

            }
        })
    });

});