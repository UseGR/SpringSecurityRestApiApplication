package ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
