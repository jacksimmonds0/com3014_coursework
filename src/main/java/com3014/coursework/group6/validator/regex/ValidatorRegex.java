package com3014.coursework.group6.validator.regex;

public class ValidatorRegex {

    /** https://howtodoinjava.com/regex/java-regex-validate-email-address/ */
    public static final String EMAIL = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    /** https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation */
    public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
}
