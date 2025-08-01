package com.medicalsuppliesmanagement.service.impl;

import com.medicalsuppliesmanagement.entity.UserAccount;
import com.medicalsuppliesmanagement.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng: " + username);
        }

        UserAccount user = userOptional.get();

        // Kiểm tra trạng thái tài khoản
        if (user.getStatus() == null || !user.getStatus().equalsIgnoreCase("ACTIVE")) {
            throw new UsernameNotFoundException("Tài khoản đã bị vô hiệu hóa hoặc không hợp lệ: " + username);
        }

        // Xác định vai trò người dùng
        String role = "ROLE_USER"; // mặc định

        if (user.getRole() != null) {
            switch (user.getRole()) {
                case ADMIN:
                    role = "ROLE_ADMIN";
                    break;
                case ACCOUNTANT:
                    role = "ROLE_ACCOUNTANT";
                    break;
                case SALES:
                    role = "ROLE_SALES";
                    break;
                case CUSTOMER:
                    role = "ROLE_CUSTOMER";
                    break;
            }
        }

        return new User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}
