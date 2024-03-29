package com.Kalium.model.userEntities;

import com.Kalium.model.enums.UserRoleEnum;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOTest {

    @Test
    void createFromUser_ShouldMapUserToUserDTO() {
        UUID userId = UUID.randomUUID();
        String username = "testUser";
        String email = "test@example.com";

        UserRole userRole = new UserRole();
        userRole.setRole(UserRoleEnum.USER);
        List<UserRole> roles = new ArrayList<>();
        roles.add(userRole);

        User user = new User(username, email, roles);
        user.setId(userId);

        UserDTO userDTO = UserDTO.createFromUser(user);

        assertEquals(userId, userDTO.getId());
        assertEquals(username, userDTO.getUsername());
        assertEquals(email, userDTO.getEmail());
        assertEquals(roles, userDTO.getRoles());
    }

    @Test
    void getAllRolesAsString_ShouldReturnCommaSeparatedRoles() {
        UserRole userRole1 = new UserRole();
        userRole1.setRole(UserRoleEnum.USER);

        UserRole userRole2 = new UserRole();
        userRole2.setRole(UserRoleEnum.ADMIN);

        List<UserRole> roles = new ArrayList<>();
        roles.add(userRole1);
        roles.add(userRole2);

        UserDTO userDTO = new UserDTO();
        userDTO.setRoles(roles);

        String rolesAsString = userDTO.getAllRolesAsString();

        assertEquals("USER, ADMIN", rolesAsString);
    }
}
