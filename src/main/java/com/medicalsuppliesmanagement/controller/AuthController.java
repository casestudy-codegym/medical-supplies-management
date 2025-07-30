package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.entity.UserAccount;
import com.medicalsuppliesmanagement.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;


    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @GetMapping("/login-success")
    public String loginSuccess(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Spring Security đã xử lý xác thực và lưu thông tin người dùng
        // Ta chỉ cần lấy thông tin chi tiết từ UserDetails nếu cần
        if (userDetails != null) {
            // Có thể lấy thêm thông tin từ service nếu cần
            UserAccount user = authService.findByUsername(userDetails.getUsername())
                    .orElse(null);
            
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        
        return "auth/loginSuccess";
    }
}
