package com.store.api.service;

import com.store.api.dto.Register;

import com.store.api.entity.User;
import com.store.api.exceptions.UserExistException;
import com.store.api.repository.UserRepository;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final UserRepository userRepository;

    AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        return "Successfuly registered user";
    }
}
