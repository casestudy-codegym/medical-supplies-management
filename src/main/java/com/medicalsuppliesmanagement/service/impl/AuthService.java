package com.medicalsuppliesmanagement.service.impl;

import com.medicalsuppliesmanagement.entity.UserAccount;
import com.medicalsuppliesmanagement.repository.IUserRepository;
import com.medicalsuppliesmanagement.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public boolean authenticate(String username, String password) {
        Optional<UserAccount> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isPresent()) {
            UserAccount user = userOptional.get();
            return passwordEncoder.matches(password, user.getPassword());
        }
        
        return false;
    }
    
    @Override
    public Optional<UserAccount> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
} 