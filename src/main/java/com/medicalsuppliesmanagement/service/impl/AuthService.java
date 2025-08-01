package com.medicalsuppliesmanagement.service.impl;

import com.medicalsuppliesmanagement.entity.UserAccount;
import com.medicalsuppliesmanagement.repository.IUserRepository;
import com.medicalsuppliesmanagement.service.IAuthService;
import com.medicalsuppliesmanagement.dto.ChangePasswordDto;
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

    @Override
    public void save(UserAccount userAccount) {
        userRepository.save(userAccount);
    }

    @Override
    public boolean changePassword(String username, ChangePasswordDto dto) {
        Optional<UserAccount> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            UserAccount user = optionalUser.get();

            // Kiểm tra mật khẩu cũ
            if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
                return false;
            }

            // Kiểm tra xác nhận mật khẩu mới
            if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
                return false;
            }

            // Cập nhật mật khẩu mới (đã mã hoá)
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            userRepository.save(user);
            return true;
        }

        return false;
    }
} 