package com.Kalium.service.impl;

import com.Kalium.model.userEntities.*;
import com.Kalium.model.enums.UserRoleEnum;
import com.Kalium.repo.UserRepository;
import com.Kalium.repo.UserRoleRepository;
import com.Kalium.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        UserRole defaultRole = userRoleRepository.findByRole(UserRoleEnum.USER);

        user.getRoles().add(defaultRole);

        userRepository.save(user);

        return true;
    }

    @Override
    public List<UserDTO> getUserData() {
        List<UserDTO> userData = userRepository.findAll()
                .stream().map(UserDTO::createFromUser)
                .toList();

        return userData;
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void changeRole(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(user.getRoles().get(0).getRole().equals(UserRoleEnum.ADMIN)) {
                List<UserRole> userRoleList = new ArrayList<>();
                userRoleList.add(userRoleRepository.findByRole(UserRoleEnum.USER));
                user.setRoles(userRoleList);
            } else {
                List<UserRole> adminRoleList = new ArrayList<>();
                adminRoleList.add(userRoleRepository.findByRole(UserRoleEnum.ADMIN));
                user.setRoles(adminRoleList);
            }
            userRepository.save(user);
        }
    }

    private User map(UserRegisterBindingModel userRegisterBindingModel) {
        return new User()
                .setUsername(userRegisterBindingModel.getUsername())
                .setEmail(userRegisterBindingModel.getEmail())
                .setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
    }
}
