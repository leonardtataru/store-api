package com.store.api.service;

import com.store.api.dto.Login;
import com.store.api.dto.Register;

import com.store.api.entity.User;
import com.store.api.exceptions.UserExistException;
import com.store.api.exceptions.WrongUsernameOrPassword;
import com.store.api.repository.UserRepository;
import com.store.api.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthorizationService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    AuthorizationService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public String register(Register register) {
        log.debug("Register : {}", register);
        User user = new User();
        user.setUsername(register.getUsername());
        user.setPassword(Base64Util.encode(register.getPassword()));
        user.setRole_id(2L);

        boolean userExist = userRepository.existsByUsername(user.getUsername());
        if (userExist) {
            log.error("Username {} already exists", user.getUsername());
            throw new UserExistException("Username already exists.");
        }
        log.debug("Register : {}", register);
        userRepository.save(user);

        return "Successfully registered user";
    }

    public String login(Login login) {
        log.debug("Login with username: {}", login.getUsername());
        User user = new User();
        user.setUsername(login.getUsername());
        user.setPassword(Base64Util.encode(login.getPassword()));

        User userExist = userRepository.findByUsername(user.getUsername());

        if (userExist != null && user.getUsername().equals(userExist.getUsername()) && user.getPassword().equals(userExist.getPassword())) {
            log.debug("Login successful");
            return jwtUtil.generateToken(userExist);
        } else {
            log.error("Invalid username or password");
            throw new WrongUsernameOrPassword("Username or password is incorrect.");
        }
    }
}
