package com.Kalium.service;

import com.Kalium.model.userEntities.UserDTO;
import com.Kalium.model.userEntities.UserRegisterBindingModel;

import java.util.List;
import java.util.UUID;

public interface UserService {

    boolean registerUser(UserRegisterBindingModel userRegisterBindingModel);

    List<UserDTO> getUserData();

    void deleteUser(UUID userId);


    void changeRole(UUID userId);
}
