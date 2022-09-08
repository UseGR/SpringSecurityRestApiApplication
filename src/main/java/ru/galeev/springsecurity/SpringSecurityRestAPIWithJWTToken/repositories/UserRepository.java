package ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);
}
