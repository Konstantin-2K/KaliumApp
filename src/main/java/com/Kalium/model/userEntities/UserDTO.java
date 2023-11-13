package com.Kalium.model.userEntities;

import com.Kalium.model.enums.UserRoleEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserDTO {

    private UUID id;
    private String username;

    private String email;

    private List<UserRole> roles = new ArrayList<>();

    public UserDTO() {
    }

    public UserDTO(UUID id, String username, String email, List<UserRole> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
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

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public static UserDTO createFromUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }

    public String getAllRolesAsString() {
        return roles.stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.joining(", "));
    }
}
