package com3014.coursework.group6.validator;

import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegisterValidator implements Validator {

    @Autowired
    UserService userService;

    public boolean supports(Class clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmpty(errors, "username", "notEmpty.username");
        ValidationUtils.rejectIfEmpty(errors, "password", "notEmpty.password");
        ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "notEmpty.confirmPassword");
        ValidationUtils.rejectIfEmpty(errors, "firstName", "notEmpty.firstName");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "notEmpty.lastName");
        ValidationUtils.rejectIfEmpty(errors, "email", "notEmpty.emailAddress");

        if(user.getPassword() != null && user.getConfirmPassword() != null &&
                !user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("password", "notMatch.confirmPassword");
        }

        if(userService.userExists(user.getUsername())){
            errors.rejectValue("username", "exists.username");
        }
    }

}