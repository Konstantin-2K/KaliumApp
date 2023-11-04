package com.Kalium.init;

import com.Kalium.model.UserRole;
import com.Kalium.model.enums.UserRoleEnum;
import com.Kalium.repo.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserRolesInit implements CommandLineRunner {
    private final UserRoleRepository userRoleRepository;

    public UserRolesInit(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        boolean hasUserRoles = userRoleRepository.count() > 0;

        if (!hasUserRoles) {
            List<UserRole> userRoles = new ArrayList<>();

            Arrays.stream(UserRoleEnum.values())
                    .forEach(userRoleEnum -> {
                        UserRole userRole = new UserRole();
                        userRole.setRole(userRoleEnum);
                        userRoles.add(userRole);
                    });

            userRoleRepository.saveAll(userRoles);
        }
    }
}
