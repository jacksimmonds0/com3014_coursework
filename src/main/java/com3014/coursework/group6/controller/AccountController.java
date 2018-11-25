package com3014.coursework.group6.controller;

import com3014.coursework.group6.model.account.PasswordUpdate;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.UserService;
import com3014.coursework.group6.validator.DetailsUpdateValidator;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

/**
 * Controller for the account page for the user to update details and/or password
 * Contains REST API for PUT requests to update the respective details and password fields in the database
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    private static final String CURRENT_USER = "currentUser";

    private UserService userService;

    private DetailsUpdateValidator detailsUpdateValidator;

    private PasswordUpdateValidator passwordUpdateValidator;

    @Autowired
    public AccountController(UserService userService, DetailsUpdateValidator detailsUpdateValidator,
                             PasswordUpdateValidator passwordUpdateValidator) {
        this.userService = userService;
        this.detailsUpdateValidator = detailsUpdateValidator;
        this.passwordUpdateValidator = passwordUpdateValidator;
    }

    /**
     * On the /account endpoint for directing the user to the account jsp page if they are logged in,
     * otherwise re-direct to the login page
     *
     * @param session
     *          the current {@link HttpSession} for the logged in user
     * @return the appropriate view depending on if a user is logged in or not
     */
    @RequestMapping(value = "")
    public ModelAndView account(HttpSession session) {
        ModelAndView mav = new ModelAndView("account");
        User currentUser = (User) session.getAttribute(CURRENT_USER);

        // if no user is logged in redirect to the login page
        if(currentUser == null) {
            return new ModelAndView("redirect:login");
        }

        mav.addObject("user", currentUser);

        return mav;
    }

    /**
     * Controller for updating the details for the logged in user from the /account view
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
     * @param user
     *          the model attribute for the result param to hold the JSON fields
     * @param result
     *          the {@link BindingResult} to hold any errors from validation of the inputs
     * @param session
     *          the {@link HttpSession} object to retrieve the current user session from
     * @return the response JSON if the request was successful or not i.e.
     *          {
     *              "error": true/false,
     *              "message": "Message associated with the response"
     *          }
     */
    @RequestMapping(value = "/{id}/update/details",
                    method = RequestMethod.PUT,
                    consumes = {"application/json"},
                    produces = {"application/json"})
    @ResponseBody
    public String updateAccountDetails(@PathVariable("id") int id, @RequestBody String req, HttpSession session,
                                       @ModelAttribute("user") User user, BindingResult result) {

        JSONObject reqJson = new JSONObject(req);

        // ensure the request matches the current session from the client
        if(requestDoesNotMatchSession(session, id)) {
            return responseJSON(true, "User details could not be updated");
        }

        // get the fields from the JSON request and create user object for validation and updating the DB
        String firstName = reqJson.optString("first_name");
        String lastName = reqJson.optString("last_name");
        String email = reqJson.optString("email");

        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        detailsUpdateValidator.validate(user, result);

        if(result.getFieldError("email") != null) {
            return responseJSON(true, "Email is not of the correct format, please try again");
        }

        User updatedUser = userService.updateDetails(user);

        // update the session to the new user
        session.setAttribute("currentUser", updatedUser);

        return responseJSON(false, "Successfully updated user details");
    }


    /**
     * Controller for updating the password for the logged in user from the /account view
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
    @RequestMapping(value = "/{id}/update/password",
            method = RequestMethod.PUT,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ResponseBody
    public String updateAccountPassword(@PathVariable("id") int id, @RequestBody String req, HttpSession session,
                                        @ModelAttribute("update") PasswordUpdate update, BindingResult result) {

        JSONObject reqJson = new JSONObject(req);

        if(requestDoesNotMatchSession(session, id)) {
            return responseJSON(true, "Password could not be updated");
        }

        update.setId(id);
        update.setOldPassword(decodeBase64FromJSONField(reqJson, "old_password"));
        update.setNewPassword(decodeBase64FromJSONField(reqJson, "new_password"));
        update.setConfirmPassword(decodeBase64FromJSONField(reqJson, "confirm_password"));

        passwordUpdateValidator.validate(update, result);

        if(result.hasErrors()) {

            // ensure password matches the guidelines, notify the user otherwise
            if(result.getFieldError("newPassword") != null) {
                return responseJSON(true, "Password does not match our guidelines, must be alphanumeric " +
                        "and contain at least 8 characters with 1 uppercase and 1 special character");
            }

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

        return currentUser == null || !(currentUser.getId() == id);
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
