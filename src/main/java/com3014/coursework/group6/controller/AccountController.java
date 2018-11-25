package com3014.coursework.group6.controller;

import com3014.coursework.group6.model.account.PasswordUpdate;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.UserService;
import com3014.coursework.group6.validator.PasswordUpdateValidator;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

@Controller
public class AccountController {

    private UserService userService;

    private PasswordUpdateValidator passwordUpdateValidator;

    @Autowired
    public AccountController(UserService userService, PasswordUpdateValidator passwordUpdateValidator) {
        this.userService = userService;
        this.passwordUpdateValidator = passwordUpdateValidator;
    }

    /**
     * Controller for updating the details for the logged in user from the <code>/account</code> view
     *
     * @param id
     *          the path variable taken from the url for the corresponding user id
     *          e.g. account/1/update/details is for the user with id 1 in the users table
     * @param req
     *          the JSON request body as a {@link String} of the form:
     *          {
     *              "first_name": "new first name",
     *              "last_name": "new last name",
     *              "email": "new email"
     *          }
     * @param session
     *          the {@link HttpSession} object to retrieve the current user session from
     * @return the response JSON if the request was successful or not i.e.
     *          {
     *              "error": true/false,
     *              "message": "Message associated with the response"
     *          }
     */
    @RequestMapping(value = "/account/{id}/update/details",
                    method = RequestMethod.PUT,
                    consumes = {"application/json"},
                    produces = {"application/json"})
    @ResponseBody
    public String updateAccountDetails(@PathVariable("id") int id, @RequestBody String req, HttpSession session) {

        JSONObject reqJson = new JSONObject(req);

        // ensure the request matches the current session from the client
        if(requestDoesNotMatchSession(session, id)) {
            // TODO use HTTP 400 response instead?
            return responseJSON(true, "User details could not be updated");
        }

        // TODO validate email

        User updatedUser = userService.updateDetails(id,
                reqJson.optString("first_name"),
                reqJson.optString("last_name"),
                reqJson.optString("email"));

        // update the session to the new user
        session.setAttribute("currentUser", updatedUser);

        return responseJSON(true, "Successfully updated user details");
    }


    /**
     * Controller for updating the password for the logged in user from the <code>/account</code> view
     *
     * @param id
     *          the path variable taken from the url for the corresponding user id
     *          e.g. account/1/update/password is for the user with id 1 in the users table
     * @param req
     *          the JSON request body as a {@link String} of the form:
     *          {
     *              "old_password": "old password (validated against database value)",
     *              "new_password": "new password specified",
     *              "confirm_password": "validated to match the new_password"
     *          }
     * @param session
     *          the {@link HttpSession} object to retrieve the current user session from
     * @param response
     *          the {@link HttpServletResponse} object for the HTTP response to the PUT request
     * @param update
     *          the model attribute for the result param to hold the JSON fields
     * @param result
     *          the {@link BindingResult} to hold any errors from validation of the inputs
     * @return the response JSON if the request was successful or not i.e.
     *          {
     *              "error": true/false,
     *              "message": "Message associated with the response"
     *          }
     */
    @RequestMapping(value = "/account/{id}/update/password",
            method = RequestMethod.PUT,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ResponseBody
    public String updateAccountPassword(@PathVariable("id") int id, @RequestBody String req, HttpSession session, HttpServletResponse response,
                                        @ModelAttribute("update") PasswordUpdate update, BindingResult result) {

        JSONObject reqJson = new JSONObject(req);

        if(requestDoesNotMatchSession(session, id)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return responseJSON(true, "Password could not be updated");
        }

        update.setId(id);
        update.setOldPassword(decodeBase64FromJSONField(reqJson, "old_password"));
        update.setNewPassword(decodeBase64FromJSONField(reqJson, "new_password"));
        update.setConfirmPassword(decodeBase64FromJSONField(reqJson, "confirm_password"));

        // TODO validate password against rules (e.g. at least 8 characters, number and letters, uppercase)
        passwordUpdateValidator.validate(update, result);

        if(result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return responseJSON(true, "Password could not be updated");
        }

        userService.updatePasswordForUser(id, decodeBase64FromJSONField(reqJson, "new_password"));

        return responseJSON(false, "Successfully updated password!");
    }

    /**
     * @return true if the user from the {@link HttpSession} has a different id to the id parameter, false otherwise
     */
    private boolean requestDoesNotMatchSession(HttpSession session, int id) {
        User currentUser = (User) session.getAttribute("currentUser");

        return !(currentUser.getId() == id);
    }

    /**
     * @return the response JSON for the error and message params e.g.
     *         {
     *             "error": true,
     *             "message": "Could not update user details"
     *         }
     */
    private String responseJSON(boolean error, String message) {
        JSONObject json = new JSONObject();

        json.put("error", error);
        json.put("message", message);

        return json.toString();
    }

    /**
     * @return the decoded base 64 value from the json field specified
     */
    private String decodeBase64FromJSONField(JSONObject json, String field) {
        return new String(DatatypeConverter.parseBase64Binary(json.optString(field)));
    }
}
