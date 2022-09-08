package ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.secutiry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.models.User;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.repositories.UserRepository;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.secutiry.jwt.JwtAuthenticationException;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.secutiry.jwt.JwtUser;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.secutiry.jwt.JwtUserFactory;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Autowired
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new JwtAuthenticationException("User with username: " + username + " not found"));

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);

        return jwtUser;
    }
}
