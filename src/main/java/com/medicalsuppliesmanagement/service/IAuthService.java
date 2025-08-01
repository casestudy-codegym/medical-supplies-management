package com.medicalsuppliesmanagement.service;

import com.medicalsuppliesmanagement.entity.UserAccount;
import java.util.Optional;

public interface IAuthService {
    boolean authenticate(String username, String password);
    Optional<UserAccount> findByUsername(String username);
} 