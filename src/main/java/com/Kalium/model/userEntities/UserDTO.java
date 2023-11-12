package com.Kalium.model.userEntities;

import com.Kalium.model.enums.UserRoleEnum;

import java.util.UUID;

public class UserDTO {

    private UUID id;
    private String username;

    private String email;

    private UserRoleEnum role;

    public UserDTO() {
    }

    public UserDTO(UUID id, String username, String email, UserRoleEnum role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public static UserDTO createFromUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRoles().get(0).getRole());
        return userDTO;
    }

}
