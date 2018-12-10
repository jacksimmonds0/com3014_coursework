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

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    private static final String USER_ROLE = "userRole";

    @Autowired
    UserService userService;

    @Autowired
    private DetailsUpdateValidator detailsUpdateValidator;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView getUserList(HttpSession session) {

        if (requestDoesNotMatchSession(session)) {
            ModelAndView mav = new ModelAndView("index");
            mav.addObject("message", "Error: Restricted access");
            return mav;
        } else {
            User user = (User) session.getAttribute("currentUser");

            ModelAndView mav = new ModelAndView("admin");
            mav.addObject("userList", userService.getUserList(user.getId()));
            return mav;
        }

    }

    @RequestMapping(value = "admin/account/{id}", method = RequestMethod.GET)
    public ModelAndView editAccount(@PathVariable("id") int id, HttpSession session) {
        ModelAndView mav = new ModelAndView("editaccount");

        if(requestDoesNotMatchSession(session)) {
            mav = new ModelAndView("index");
            mav.addObject("message", "Error: Restricted access");
            return mav;
        }

        User user = userService.getUserById(id);

        mav.addObject("user", user);

        return mav;
    }


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

        User updatedUser = userService.updateDetails(user);

        // update the session to the new user
        session.setAttribute("currentUser", updatedUser);

        return responseJSON(false, "Successfully updated user details");
    }

    @RequestMapping(value = "admin/inactive/{id}", method = RequestMethod.GET)
    public ModelAndView makeAccountInactive(@PathVariable int id) {
        userService.changeUserStatus(id, "INACTIVE");
        ModelAndView mav = new ModelAndView("redirect:/admin");

        return mav;
    }

    @RequestMapping(value = "admin/active/{id}", method = RequestMethod.GET)
    public ModelAndView makeAccountActive(@PathVariable int id) {
        userService.changeUserStatus(id, "ACTIVE");
        ModelAndView mav = new ModelAndView("redirect:/admin");

        return mav;
    }

    private String responseJSON(boolean error, String message) {
        JSONObject json = new JSONObject();

        json.put("error", error);
        json.put("message", message);

        return json.toString();
    }

    private boolean requestDoesNotMatchSession(HttpSession session) {
        String userRole = (String) session.getAttribute(USER_ROLE);

        return userRole == null || !userRole.equals("ROLE_ADMIN");
    }


}
