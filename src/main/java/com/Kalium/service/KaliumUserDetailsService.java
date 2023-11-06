package com.Kalium.service;

import com.Kalium.model.userEntities.User;
import com.Kalium.model.userEntities.UserRole;
import com.Kalium.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class KaliumUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public KaliumUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(KaliumUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found!"));
    }

    private static UserDetails map(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(KaliumUserDetailsService::map).toList())
                .build();
    }

    private static GrantedAuthority map(UserRole userRole) {
        return new SimpleGrantedAuthority(
                "ROLE_" + userRole.getRole().name()
        );
    }
}
