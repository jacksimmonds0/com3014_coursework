package com3014.coursework.group6.controller;

import com3014.coursework.group6.model.account.PasswordUpdate;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.UserService;
import com3014.coursework.group6.validator.DetailsUpdateValidator;
import com3014.coursework.group6.validator.PasswordUpdateValidator;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountControllerTest {

    private static final String CURRENT_USER = "currentUser";

    private static final String EMPTY_JSON = "{}";

    private AccountController accountController;

    private UserService mockUserService;

    private DetailsUpdateValidator mockDetailsUpdateValidator;

    private PasswordUpdateValidator mockPasswordUpdateValidator;

    private HttpSession mockHttpSession;

    private BindingResult mockBindingResult;

    private User user1;

    private User user2;

    @Before
    public void setUp() throws Exception {

        // set-up mocked classes and inject them into the class under test
        mockUserService = mock(UserService.class);
        mockDetailsUpdateValidator = mock(DetailsUpdateValidator.class);
        mockPasswordUpdateValidator = mock(PasswordUpdateValidator.class);
        accountController = new AccountController(mockUserService, mockDetailsUpdateValidator, mockPasswordUpdateValidator);

        // set-up parameter mocks
        mockHttpSession = mock(HttpSession.class);
        mockBindingResult = mock(BindingResult.class);

        user1 = new User();
        user1.setId(1);

        user2 = new User();
        user2.setId(2);
    }

    @Test
    public void test_user_not_logged_in_redirect_to_login_page() {
        when(mockHttpSession.getAttribute(CURRENT_USER)).thenReturn(null);

        String expected = "redirect:login";
        String actual = accountController.account(mockHttpSession).getViewName();

        assertEquals(expected, actual);
    }

    @Test
    public void test_user_logged_in_returns_account_page() {
        when(mockHttpSession.getAttribute(CURRENT_USER)).thenReturn(user1);

        String expected = "account";
        String actual = accountController.account(mockHttpSession).getViewName();

        assertEquals(expected, actual);
    }

    @Test
    public void test_error_response_when_user_not_logged_in() {
        when(mockHttpSession.getAttribute(CURRENT_USER)).thenReturn(null);

        String expected = new JSONObject()
                .put("error", true)
                .put("message", "User details could not be updated")
                .toString();

        String actual = accountController.updateAccountDetails(0, EMPTY_JSON, mockHttpSession, new User(), mockBindingResult);

        assertEquals(expected, actual);
    }

    @Test
    public void test_email_validation_fails_returns_error_response() {
        when(mockHttpSession.getAttribute(CURRENT_USER)).thenReturn(user1);

        String expected = new JSONObject()
                .put("error", true)
                .put("message", "Email is not of the correct format, please try again")
                .toString();

        String requestBody = new JSONObject()
                .put("first_name", "Test")
                .put("last_name", "User")
                .put("email", "test123.com")
                .toString();

        User user = new User();

        FieldError fieldError = new FieldError("user", "email", "failed validation");
        when(mockBindingResult.getFieldError("email")).thenReturn(fieldError);

        String actual = accountController.updateAccountDetails(1, requestBody, mockHttpSession, user, mockBindingResult);

        assertEquals(expected, actual);
    }

    @Test
    public void test_user_gets_updated_in_session() {
        when(mockHttpSession.getAttribute(CURRENT_USER)).thenReturn(user1);

        String expectedResponse = new JSONObject()
                .put("error", false)
                .put("message", "Successfully updated user details")
                .toString();

        String requestBody = new JSONObject()
                .put("first_name", "Test")
                .put("last_name", "User")
                .put("email", "test123.com")
                .toString();

        User expectedUser = new User();
        expectedUser.setFirstName("Test-change");
        expectedUser.setLastName("User-change");
        expectedUser.setEmail("test@123.com");

        when(mockUserService.updateDetails(user1)).thenReturn(expectedUser);

        String actual = accountController.updateAccountDetails(1, requestBody, mockHttpSession, user1, mockBindingResult);

        verify(mockHttpSession, times(1)).setAttribute(CURRENT_USER, expectedUser);
        verify(mockDetailsUpdateValidator, times(1)).validate(user1, mockBindingResult);

        assertNotNull(actual);
        assertEquals(expectedResponse, actual);
    }

    @Test
    public void test_error_response_when_user_not_logged_in_update_password() {
        when(mockHttpSession.getAttribute(CURRENT_USER)).thenReturn(null);

        String expected = new JSONObject()
                .put("error", true)
                .put("message", "Password could not be updated")
                .toString();

        String actual = accountController.updateAccountPassword(0, EMPTY_JSON, mockHttpSession, new PasswordUpdate(), mockBindingResult);

        assertEquals(expected, actual);
    }

    @Test
    public void test_new_password_error_returns_error_response() {
        when(mockHttpSession.getAttribute(CURRENT_USER)).thenReturn(user1);

        String expected = new JSONObject()
                .put("error", true)
                .put("message", "Password does not match our guidelines, must be alphanumeric " +
                        "and contain at least 8 characters with 1 uppercase and 1 special character")
                .toString();

        FieldError fieldError = new FieldError("passwordUpdate", "newPassword", "validation error");

        when(mockBindingResult.hasErrors()).thenReturn(true);
        when(mockBindingResult.getFieldError("newPassword")).thenReturn(fieldError);

        String actual = accountController.updateAccountPassword(1, EMPTY_JSON, mockHttpSession, new PasswordUpdate(), mockBindingResult);

        assertEquals(expected, actual);
    }

    @Test
    public void test_password_update_success_response() {
        when(mockHttpSession.getAttribute(CURRENT_USER)).thenReturn(user1);

        String expected = new JSONObject()
                .put("error", false)
                .put("message", "Successfully updated password!")
                .toString();

        when(mockBindingResult.hasErrors()).thenReturn(false);
        PasswordUpdate update = new PasswordUpdate();

        String actual = accountController.updateAccountPassword(1, EMPTY_JSON, mockHttpSession, update, mockBindingResult);

        verify(mockPasswordUpdateValidator, times(1)).validate(update, mockBindingResult);
        assertEquals(expected, actual);
    }

    @Test
    public void test_endpoints_return_error_if_user_id_doesnt_match() {
        when(mockHttpSession.getAttribute(CURRENT_USER)).thenReturn(user1);

        String expectedDetails = new JSONObject()
                .put("error", true)
                .put("message", "User details could not be updated")
                .toString();

        String expectedPassword = new JSONObject()
                .put("error", true)
                .put("message", "Password could not be updated")
                .toString();

        String actualDetails = accountController.updateAccountDetails(2, EMPTY_JSON, mockHttpSession, new User(), mockBindingResult);
        String actualPassword = accountController.updateAccountPassword(2, EMPTY_JSON, mockHttpSession, new PasswordUpdate(), mockBindingResult);

        assertEquals(expectedDetails, actualDetails);
        assertEquals(expectedPassword, actualPassword);
    }

}