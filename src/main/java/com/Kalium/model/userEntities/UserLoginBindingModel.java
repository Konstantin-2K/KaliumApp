package com.Kalium.model.userEntities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLoginBindingModel {

    @Email
    @NotBlank(message = "Email cannot be empty!")
    private String email;

    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters!")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
