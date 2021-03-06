package com3014.coursework.group6.validator;

import com3014.coursework.group6.model.account.PasswordUpdate;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.UserService;
import com3014.coursework.group6.validator.regex.ValidatorRegex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validation for when a user is updating their password
 */
@Component
public class PasswordUpdateValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class clazz) {
        return PasswordUpdate.class.isAssignableFrom(clazz);
    }

    /**
     * Validating the user against specific validation rules
     *
     * @param target
     *          the target object in this case {@link PasswordUpdate}
     * @param errors
     *          any errors as a result of the validation
     */
    @Override
    public void validate(Object target, Errors errors) {
        PasswordUpdate update = (PasswordUpdate) target;

        ValidationUtils.rejectIfEmpty(errors, "oldPassword", "notEmpty.oldPassword");
        ValidationUtils.rejectIfEmpty(errors, "newPassword", "notEmpty.newPassword");
        ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "notEmpty.confirmPassword");

        if(!userService.correctPasswordForUser(update.getId(), update.getOldPassword())) {
            errors.rejectValue("oldPassword", "exists.oldPassword");
        }

        if(!update.getNewPassword().matches(ValidatorRegex.PASSWORD)) {
            errors.rejectValue("newPassword", "wrongFormat.newPassword");
        }

        if(!update.getNewPassword().equals(update.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "notMatch.confirmPassword");
        }
    }
}
