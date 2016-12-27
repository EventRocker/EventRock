package com.eventrock.controller;

import com.eventrock.model.User;
import com.eventrock.validator.UserValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserValidator userValidator;

    @Mock
    private User user;

    @Mock
    private Model model;

    @InjectMocks
    private UserController subject = new UserController();

    @Test
    public void registration_shouldReturnRegistrationPage() throws Exception {
        assertThat(subject.registration(model), is("registration"));
    }

    @Test
    public void registration_shouldCallAddAttributeWithUserForm() throws Exception {
        subject.registration(model);
        Mockito.verify(model).addAttribute(eq("userForm"),any(User.class));
    }

    @Test
    public void createUser_WhenGivenBindingResultHasErrors_shouldReturnRegistrationPage() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(subject.createUser(user, bindingResult, new ExtendedModelMap()), is("registration"));
    }

    @Test
    public void createUser_WhenGivenBindingResultHasNoErrors_shouldReturnRegistrationPage() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(false);
        assertThat(subject.createUser(user, bindingResult, new ExtendedModelMap()), is("redirect:/home"));
    }

    @Test
    public void login_shouldReturnLoginPage() throws Exception {
        assertThat(subject.login(model,"",""), is("login"));
    }

    @Test
    public void login_WhenErrorNotNull_shouldAddAttributeWithError() throws Exception {
        subject.login(model,"","Error");
        Mockito.verify(model).addAttribute(eq("error"),any(String.class));
    }

    @Test
    public void login_WhenMessageNotNull_shouldAddAttributeWithMessage() throws Exception {
        subject.login(model,"message","");
        Mockito.verify(model).addAttribute(eq("message"),any(String.class));
    }
}
