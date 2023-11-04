package com.Kalium.service;

import com.Kalium.model.UserLoginBindingModel;
import com.Kalium.model.UserRegisterBindingModel;

public interface UserService {

    boolean registerUser(UserRegisterBindingModel userRegisterBindingModel);

}
