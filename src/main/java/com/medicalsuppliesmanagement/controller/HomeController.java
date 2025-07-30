package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.service.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/home")
    public String showHomePage(Model model) {
        long customerCount = customerService.countCustomers();
        model.addAttribute("customerCount", customerCount);
        return "home/homePage";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/home";
    }
}
