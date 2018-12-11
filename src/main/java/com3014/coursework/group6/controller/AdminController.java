package com3014.coursework.group6.controller;

import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.UserService;
import com3014.coursework.group6.validator.DetailsUpdateValidator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * The controller for the admin page and editing other user accounts as an administrator
 * of the system
 */
@Controller
public class AdminController {

    private static final String USER_ROLE = "userRole";

    @Autowired
    UserService userService;

    @Autowired
    private DetailsUpdateValidator detailsUpdateValidator;

    /**
     * The mapping for the admin endpoint to show the admin page containing all the other users
     * in the system to edit/change active status
     *
     * @param session
     *          the {@link HttpSession} object containing the currently logged in user
     * @param redirectAttributes
     *          the redirect attributes for when redirecting to the home page
     * @return the {@link ModelAndView} depending on if the user is authorized to access this page or not
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView getUserList(HttpSession session, RedirectAttributes redirectAttributes) {

        if (requestDoesNotMatchSession(session)) {
            ModelAndView mav = new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("message", "Error: Restricted access");
            return mav;
        } else {
            User user = (User) session.getAttribute("currentUser");

            ModelAndView mav = new ModelAndView("admin");
            mav.addObject("userList", userService.getUserList(user.getId()));
            return mav;
        }

    }

    /**
     * The edit account page for the administrator edit another users account details
     *
     * @param id
     *          the id of the user to edit, based on the path variable in the request URL
     * @param session
     *          the {@link HttpSession} object containing the currently logged in user
     * @param redirectAttributes
     *          the redirect attributes for when redirecting to the home page
     * @return the {@link ModelAndView} for either the home page or the edit account view
     */
    @RequestMapping(value = "admin/account/{id}", method = RequestMethod.GET)
    public ModelAndView editAccount(@PathVariable("id") int id, HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("editaccount");

        if(requestDoesNotMatchSession(session)) {

            mav = new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("message", "Error: Restricted access");
            return mav;
        }

        User user = userService.getUserById(id);

        mav.addObject("user", user);

        return mav;
    }


    /**
     * The endpoint for when administrators are editing another users details, triggered when
     * the update details button is clicked on the page via AJAX
     *
     * @param id
     *          the id of the user to edit, based on the path variable in the request URL
     * @param req
     *          the request JSON string containing the user details from the form fields on the page
     * @param session
     *          the {@link HttpSession} object containing the currently logged in user
     * @param user
     *          the user {@link ModelAttribute} to be used for validation
     * @param result
     *          the {@link BindingResult} result of the validation
     * @return the JSON response if an error occurred or not, and the corresponding message i.e.
     *          {
     *              "error": true/false,
     *              "message": "Message associated with the response"
     *          }
     */
    @RequestMapping(value = "admin/account/{id}/edit",
                    method = RequestMethod.PUT,
                    consumes = {"application/json"},
                    produces = {"application/json"})
    @ResponseBody
    public String editAccountDetails(@PathVariable("id") int id, @RequestBody String req, HttpSession session,
                              @ModelAttribute("user") User user, BindingResult result) {

        if(requestDoesNotMatchSession(session)) {
            return responseJSON(true, "An administrator is not signed in.");
        }

        JSONObject request = new JSONObject(req);
        String firstName = request.optString("first_name");
        String lastName = request.optString("last_name");
        String email = request.optString("email");
        String role = request.optString("user_role");

       user.setFirstName(firstName);
       user.setLastName(lastName);
       user.setEmail(email);
       user.setRole(role);

       detailsUpdateValidator.validate(user, result);

       if(result.getFieldError("email") != null) {
           return responseJSON(true, "Email is not of the correct format, please try again");
       }

       // update details without response as we dont need to update the session
       userService.updateDetails(user);

       return responseJSON(false, "Successfully updated user details");
    }

    /**
     * The method to make a user account inactive
     *
     * @param id
     *          the user id to make inactive, from the path variable
     * @return the {@link ModelAndView} for the admin view
     */
    @RequestMapping(value = "admin/inactive/{id}", method = RequestMethod.GET)
    public ModelAndView makeAccountInactive(@PathVariable int id) {
        userService.changeUserStatus(id, "INACTIVE");
        ModelAndView mav = new ModelAndView("redirect:/admin");

        return mav;
    }

    /**
     * The method to make a user account active
     *
     * @param id
     *          the user id to make active, from the path variable
     * @return the {@link ModelAndView} for the admin view
     */
    @RequestMapping(value = "admin/active/{id}", method = RequestMethod.GET)
    public ModelAndView makeAccountActive(@PathVariable int id) {
        userService.changeUserStatus(id, "ACTIVE");
        ModelAndView mav = new ModelAndView("redirect:/admin");

        return mav;
    }

    /**
     * Builds a response JSON as a string from the parameters
     *
     * @param error
     *          the error true/false to be added as a JSON field
     * @param message
     *          the message to be added as a JSON field
     * @return the response JSON as a string
     */
    private String responseJSON(boolean error, String message) {
        JSONObject json = new JSONObject();

        json.put("error", error);
        json.put("message", message);

        return json.toString();
    }

    /**
     * Verifying if the request matches the session object contained with the cookie
     *
     * @param session
     *          the {@link HttpSession} object containing the currently logged in user
     * @return true if the user role does not exist or is not admin, false otherwise
     */
    private boolean requestDoesNotMatchSession(HttpSession session) {
        String userRole = (String) session.getAttribute(USER_ROLE);

        return userRole == null || !userRole.equals("ROLE_ADMIN");
    }


}
