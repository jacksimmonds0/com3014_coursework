package com3014.coursework.group6.validator;

import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.validator.regex.ValidatorRegex;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for when a details are being updated for a user account
 */
@Component
public class DetailsUpdateValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return DetailsUpdateValidator.class.isAssignableFrom(clazz);
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

        ValidationUtils.rejectIfEmpty(errors, "firstName", "notEmpty.firstName");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "notEmpty.firstName");
        ValidationUtils.rejectIfEmpty(errors, "email", "notEmpty.email");

        if(!user.getEmail().matches(ValidatorRegex.EMAIL)) {
            errors.rejectValue("email", "wrongFormat.email");
        }

    }
}
