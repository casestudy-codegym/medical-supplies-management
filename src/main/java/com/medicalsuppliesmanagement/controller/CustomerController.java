package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.dto.CustomerDto;
import com.medicalsuppliesmanagement.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final ICustomerService customerService;

    @GetMapping("/profile")
    public String showCustomerProfile(Model model, Principal principal) {
        String username = "customer01";
        CustomerDto dto = customerService.getCustomerProfile(username);
        model.addAttribute("customer", dto);
        return "customer/profile";
    }
}
