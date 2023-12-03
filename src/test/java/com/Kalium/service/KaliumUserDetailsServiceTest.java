package com.Kalium.service;

import com.Kalium.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class KaliumUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private KaliumUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_WithNonExistingUser_ShouldThrowException() {
        String userEmail = "nonexistent@example.com";

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(userEmail));

        verify(userRepository, times(1)).findByEmail(userEmail);
    }
}
