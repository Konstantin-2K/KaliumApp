package com.Kalium.repo;

import com.Kalium.model.UserRole;
import com.Kalium.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole(UserRoleEnum userRoleEnum);
}
