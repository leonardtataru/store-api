package com.store.api.service;

import com.store.api.dto.Login;
import com.store.api.dto.Register;

import com.store.api.entity.User;
import com.store.api.exceptions.UserExistException;
import com.store.api.exceptions.WrongUsernameOrPassword;
import com.store.api.repository.UserRepository;
import com.store.api.utils.JwtUtil;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    AuthorizationService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String register(Register register) {

        User user = new User();
        user.setUsername(register.getUsername());
        user.setPassword(Base64Util.encode(register.getPassword()));
        user.setRole(2L);

        boolean userExist = userRepository.existsByUsername(user.getUsername());
        if (userExist) {
            throw new UserExistException("Username already exists.");
        }
        userRepository.save(user);

        return "Successfully registered user";
    }

    public String login(Login login) {
        User user = new User();
        user.setUsername(login.getUsername());
        user.setPassword(Base64Util.encode(login.getPassword()));

        Optional<User> userExist = userRepository.findByUsername(user.getUsername());

        if (user.getUsername().equals(userExist.get().getUsername()) && user.getPassword().equals(userExist.get().getPassword())) {
            return jwtUtil.generateToken(userExist);
        } else {
            throw new WrongUsernameOrPassword("Username or password is incorrect.");
        }
    }
}
