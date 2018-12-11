package com3014.coursework.group6.validator;

import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.UserService;
import com3014.coursework.group6.validator.regex.ValidatorRegex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validation for when a user registers with the site
 */
@Component
public class RegisterValidator implements Validator {

    @Autowired
    UserService userService;

    public boolean supports(Class clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    /**
     * Validating the user against specific validation rules
     *
     * @param target
     *          the target object in this case {@link User}
     * @param errors
     *          any errors as a result of the validation
     */
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if(user.getUsername().length()<=4 || user.getUsername().length()>20) {
            errors.rejectValue("username", "invalid.username");
        }

        if(userService.userExists(user.getUsername())){
            errors.rejectValue("username", "exists.username");
        }

        if(!user.getPassword().matches(ValidatorRegex.PASSWORD) || user.getPassword().length()<8 || user.getPassword().length()>= 30) {
            errors.rejectValue("password", "invalid.password");
        }

        if(user.getPassword() != null && user.getConfirmPassword() != null && !user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("confirmPassword", "notMatch.confirmPassword");
        }

        if(user.getFirstName().length()<2 || user.getFirstName().length()>30) {
            errors.rejectValue("firstName", "invalid.name");
        }

        if(user.getLastName().length()<2 || user.getLastName().length()>30) {
            errors.rejectValue("lastName", "invalid.name");
        }

        if(!user.getEmail().matches(ValidatorRegex.EMAIL) || user.getEmail().length()<=4 || user.getEmail().length()>45) {
            errors.rejectValue("email", "invalid.emailAddress");
        }
    }

}