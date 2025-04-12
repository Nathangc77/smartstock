package com.moreira.smartstock.services;

import com.moreira.smartstock.entities.User;
import com.moreira.smartstock.projections.UserDetailsProjection;
import com.moreira.smartstock.repositories.UserRepository;
import com.moreira.smartstock.tests.factories.UserDetailsProjectionFactory;
import com.moreira.smartstock.tests.factories.UserFactory;
import com.moreira.smartstock.utils.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private UserUtil userUtil;

    private String existingUsername, nonExistingUsername;
    private List<UserDetailsProjection> userDetails;
    private User user;

    @BeforeEach
    void setUp() throws Exception {
        existingUsername = "maria@gmail.com";
        nonExistingUsername = "user@gmail.com";

        userDetails = UserDetailsProjectionFactory.createCustomUser(existingUsername);

        user = UserFactory.createCustomUser(existingUsername);

        Mockito.when(repository.searchUserByUsername(existingUsername)).thenReturn(userDetails);
        Mockito.when(repository.searchUserByUsername(nonExistingUsername)).thenReturn(new ArrayList<>());

        Mockito.when(repository.findByEmail(existingUsername)).thenReturn(Optional.of(user));
        Mockito.when(repository.findByEmail(nonExistingUsername)).thenReturn(Optional.empty());
    }

    @Test
    public void loadUserByUsernameShouldReturnUserDetailsWhenUsernameExists() {
        UserDetails result = service.loadUserByUsername(existingUsername);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingUsername, result.getUsername());
    }

    @Test
    public void loadUserByUsernameShouldThrowsUsernameNotFoundExceptionWhenUsernameDoesNotExist() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(nonExistingUsername));
    }

    @Test
    public void getUserLoggedShouldReturnUserWhenUserLogged() {
        Mockito.when(userUtil.getLoggedUsername()).thenReturn(existingUsername);
        User result = service.getUserLogged();

        Assertions.assertEquals(existingUsername, result.getUsername());
    }

    @Test
    public void getUserLoggedShouldThrowsUsernameNotFoundExceptionWhenUserDoesNotLogged() {
        Mockito.doThrow(ClassCastException.class).when(userUtil).getLoggedUsername();
        Assertions.assertThrows(UsernameNotFoundException.class, () -> service.getUserLogged());
    }
}
