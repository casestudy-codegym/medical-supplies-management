package com.medicalsuppliesmanagement.service;

import com.medicalsuppliesmanagement.entity.UserAccount;
import com.medicalsuppliesmanagement.dto.ChangePasswordDto;
import java.util.Optional;

public interface IAuthService {
    boolean authenticate(String username, String password);
    Optional<UserAccount> findByUsername(String username);
    void save(UserAccount userAccount);
    boolean changePassword(String username, ChangePasswordDto dto);
} 