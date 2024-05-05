package com.edu.integracaochatgpt.service;

import com.edu.integracaochatgpt.domain.User;
import com.edu.integracaochatgpt.domain.UserRole;
import com.edu.integracaochatgpt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class DbService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void instantiateDataBase() {
        String encodedPassword = passwordEncoder.encode("admin");
        User user1 = new User("admin", encodedPassword, "admin@example.com",UserRole.ADMIN);
        userRepository.save(user1);
    }
}

