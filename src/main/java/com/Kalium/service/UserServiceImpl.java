package com.Kalium.service;

import com.Kalium.model.User;
import com.Kalium.model.UserLoginBindingModel;
import com.Kalium.model.UserRegisterBindingModel;
import com.Kalium.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserRegisterBindingModel userRegisterBindingModel) {
        userRepository.save(map(userRegisterBindingModel));
    }

    private User map(UserRegisterBindingModel userRegisterBindingModel) {
        return new User()
                .setUsername(userRegisterBindingModel.getUsername())
                .setEmail(userRegisterBindingModel.getEmail())
                .setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
    }
}
