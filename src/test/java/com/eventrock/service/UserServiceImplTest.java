package com.eventrock.service;

import com.eventrock.model.User;
import com.eventrock.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private User user;

    @InjectMocks
    UserServiceImpl subject = new UserServiceImpl();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void save_shouldCallSetPassword() throws Exception {
        subject.save(user);
        verify(user).setPassword(any());
    }

    @Test
    public void save_shouldEncryptPassword() throws Exception {
        subject.save(user);
        verify(bCryptPasswordEncoder).encode(user.getPassword());
    }

    @Test
    public void save_shouldCallRepoSaveWithUser() throws Exception {
        subject.save(user);
        verify(userRepository).save(user);
    }

    @Test
    public void findByUsername_ShouldCallRepoFindByUsername() throws Exception {
        subject.findByUsername(user.getUsername());
        verify(userRepository).findByUsername(user.getUsername());
    }

    @Test
    public void loadUserByUsername_ShouldCallRepoFindByUsername() throws Exception {
        subject.loadUserByUsername(user.getUsername());
        verify(userRepository).findByUsername(user.getUsername());
    }
}