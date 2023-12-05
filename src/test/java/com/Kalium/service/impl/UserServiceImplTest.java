package com.Kalium.service.impl;

import com.Kalium.model.enums.UserRoleEnum;
import com.Kalium.model.userEntities.User;
import com.Kalium.model.userEntities.UserRegisterBindingModel;
import com.Kalium.model.userEntities.UserRole;
import com.Kalium.repo.UserRepository;
import com.Kalium.repo.UserRoleRepository;
import com.Kalium.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Test
    void registerUser_ShouldRegisterNewUser_WhenValidData() {
        UserRepository userRepositoryMock = mock(UserRepository.class);
        UserRoleRepository userRoleRepositoryMock = mock(UserRoleRepository.class);
        PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);

        UserService userService = new UserServiceImpl(userRepositoryMock, passwordEncoderMock, userRoleRepositoryMock);

        UserRegisterBindingModel userRegisterBindingModel = new UserRegisterBindingModel();
        userRegisterBindingModel.setUsername("testuser");
        userRegisterBindingModel.setEmail("testuser@example.com");
        userRegisterBindingModel.setPassword("password");
        userRegisterBindingModel.setConfirmPassword("password");

        when(userRepositoryMock.findByUsername("testuser")).thenReturn(null);
        when(userRepositoryMock.findByEmail("testuser@example.com")).thenReturn(Optional.empty());
        when(userRoleRepositoryMock.findByRole(UserRoleEnum.USER)).thenReturn(new UserRole(UserRoleEnum.USER));
        when(passwordEncoderMock.encode("password")).thenReturn("hashedPassword");

        when(userRepositoryMock.save(any(User.class))).thenReturn(null);

        boolean result = userService.registerUser(userRegisterBindingModel);

        assertTrue(result);
        verify(userRepositoryMock, times(1)).save(any(User.class));
    }


    @Test
    void registerUser_ShouldNotRegisterUser_WhenPasswordsDoNotMatch() {
        UserRepository userRepositoryMock = mock(UserRepository.class);
        UserRoleRepository userRoleRepositoryMock = mock(UserRoleRepository.class);
        PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);

        UserService userService = new UserServiceImpl(userRepositoryMock, passwordEncoderMock, userRoleRepositoryMock);

        UserRegisterBindingModel userRegisterBindingModel = new UserRegisterBindingModel();
        userRegisterBindingModel.setPassword("password");
        userRegisterBindingModel.setConfirmPassword("differentpassword");

        boolean result = userService.registerUser(userRegisterBindingModel);

        assertFalse(result);
        verify(userRepositoryMock, never()).save(any(User.class));
    }

    @Test
    void registerUser_ShouldNotRegisterUser_WhenUsernameOrEmailAlreadyExists() {
        UserRepository userRepositoryMock = mock(UserRepository.class);
        UserRoleRepository userRoleRepositoryMock = mock(UserRoleRepository.class);
        PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);

        UserService userService = new UserServiceImpl(userRepositoryMock, passwordEncoderMock, userRoleRepositoryMock);

        UserRegisterBindingModel userRegisterBindingModel = new UserRegisterBindingModel();
        userRegisterBindingModel.setUsername("existinguser");
        userRegisterBindingModel.setEmail("existinguser@example.com");
        userRegisterBindingModel.setPassword("password");
        userRegisterBindingModel.setConfirmPassword("password");

        when(userRepositoryMock.findByUsername("existinguser")).thenReturn(new User());
        when(userRepositoryMock.findByEmail("existinguser@example.com")).thenReturn(Optional.of(new User()));

        boolean result = userService.registerUser(userRegisterBindingModel);

        assertFalse(result);
        verify(userRepositoryMock, never()).save(any(User.class));
    }
}
