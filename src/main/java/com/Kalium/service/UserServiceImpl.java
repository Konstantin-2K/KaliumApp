package com.Kalium.service;

import com.Kalium.model.User;
import com.Kalium.model.UserLoginBindingModel;
import com.Kalium.model.UserRegisterBindingModel;
import com.Kalium.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    @Override
    public boolean registerUser(UserRegisterBindingModel userRegisterBindingModel) {
        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            return false;
        }

        User userByName = userRepository.findByUsername(userRegisterBindingModel.getUsername());
        Optional<User> userByEmail = userRepository.findByEmail(userRegisterBindingModel.getEmail());

        if (userByName != null || userByEmail.isPresent()) {
            return false;
        }

        userRepository.save(map(userRegisterBindingModel));

        return true;
    }

    private User map(UserRegisterBindingModel userRegisterBindingModel) {
        return new User()
                .setUsername(userRegisterBindingModel.getUsername())
                .setEmail(userRegisterBindingModel.getEmail())
                .setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
    }
}
