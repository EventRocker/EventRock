package com.eventrock.validator;

import com.eventrock.model.User;
import com.eventrock.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by peerapat on 27/12/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {
    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserValidator subject= new UserValidator();

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
    }

//    @Test
//    public void validate_WhenGivenExistingUsername_ErrorShouldHasFieldUsernameWithCodeDuplicateUsername() throws Exception {
//        user.setUsername("username");
//        user.setPassword("password2");
//        user.setPasswordConfirm("password2");
//        Errors errors = new BeanPropertyBindingResult(user,"");
//
//     when(userService.findByUsername("username")).thenReturn(new User());
//
//        subject.validate(user,errors);
//        assertTrue(errors.hasFieldErrors("username"));
//        assertThat(errors.getFieldError("username").getCode(),is("DuplicateUsername"));
//    }

    @Test
    public void validate_WhenGivenNullUsername_ErrorShouldHasFieldUsernameWithCodeNotEmpty() throws Exception {
        user.setUsername("");
        user.setPassword("password2");
        user.setPasswordConfirm("password2");
        Errors errors = new BeanPropertyBindingResult(user,"");

        subject.validate(user,errors);
        assertTrue(errors.hasFieldErrors("username"));
        assertThat(errors.getFieldError("username").getCode(),is("NotEmpty"));
    }

    @Test
    public void validate_WhenGivenNullPassword_ErrorShouldHasFieldPasswordWithCodeNotEmpty() throws Exception {
        user.setUsername("username");
        user.setPassword("");
        user.setPasswordConfirm("");
        Errors errors = new BeanPropertyBindingResult(user,"");

        subject.validate(user,errors);
        assertTrue(errors.hasFieldErrors("password"));
        assertThat(errors.getFieldError("password").getCode(),is("NotEmpty"));
    }

    @Test
    public void validate_WhenGivenPasswordNotEqualPasswordConfirm_ErrorShouldHasFieldPasswordConfirmWithCodePasswordAndPasswordConfirmNotMatch() throws Exception {
        user.setUsername("username");
        user.setPassword("password");
        user.setPasswordConfirm("password2");
        Errors errors = new BeanPropertyBindingResult(user,"");

        subject.validate(user,errors);
        assertTrue(errors.hasFieldErrors("passwordConfirm"));
        assertThat(errors.getFieldError("passwordConfirm").getCode(),is("PasswordAndPasswordConfirmNotMatch"));
    }

}