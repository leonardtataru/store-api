package com.store.api.utils;

import com.store.api.entity.User;
import com.store.api.repository.UserRepository;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Initializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository) {

        return args -> {

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(Base64Util.encode("admin"));
            admin.setRole_id(1L);

            userRepository.save(admin);
        };
    }

}
