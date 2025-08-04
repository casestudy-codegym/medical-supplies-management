package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.dto.ChangePasswordDto;
import com.medicalsuppliesmanagement.entity.UserAccount;
import com.medicalsuppliesmanagement.service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/logout")
    public String logout(@AuthenticationPrincipal UserDetails userDetails) {
        // Xử lý đăng xuất nếu cần
        return "redirect:/auth/login";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("changePasswordDto", new ChangePasswordDto());
        return "auth/changePassword";
    }

    @PostMapping("/change-password")
    public String processChangePassword(@Valid @ModelAttribute("changePasswordDto") ChangePasswordDto dto,
                                        BindingResult bindingResult,
                                        Model model,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            return "auth/changePassword";
        }

        String username = userDetails.getUsername();
        boolean success = authService.changePassword(username, dto);

        if (success) {
            return "redirect:/auth/logout";
        } else {
            model.addAttribute("error", "Mật khẩu hiện tại không đúng hoặc mật khẩu mới không khớp.");
            return "auth/changePassword";
        }
    }
}
