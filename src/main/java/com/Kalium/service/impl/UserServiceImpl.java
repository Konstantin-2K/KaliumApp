package com.Kalium.service.impl;

import com.Kalium.model.userEntities.User;
import com.Kalium.model.userEntities.UserRegisterBindingModel;
import com.Kalium.model.userEntities.UserRole;
import com.Kalium.model.enums.UserRoleEnum;
import com.Kalium.repo.UserRepository;
import com.Kalium.repo.UserRoleRepository;
import com.Kalium.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
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

        User user = map(userRegisterBindingModel);

        UserRole defaultRole = userRoleRepository.findByRole(UserRoleEnum.ADMIN);

        user.getRoles().add(defaultRole);

        userRepository.save(user);

        return true;
    }

    private User map(UserRegisterBindingModel userRegisterBindingModel) {
        return new User()
                .setUsername(userRegisterBindingModel.getUsername())
                .setEmail(userRegisterBindingModel.getEmail())
                .setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
    }
}
