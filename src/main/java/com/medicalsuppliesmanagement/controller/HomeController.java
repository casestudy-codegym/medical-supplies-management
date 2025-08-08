package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.service.ICustomerService;
import com.medicalsuppliesmanagement.service.impl.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/home")
    public String showHomePage(Model model) {
        long customerCount = customerService.countCustomers();
        long employeeCount = employeeService.countEmployees();
        model.addAttribute("employeeCount", employeeCount);
        model.addAttribute("customerCount", customerCount);
        return "home/homePage";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/home";
    }
}