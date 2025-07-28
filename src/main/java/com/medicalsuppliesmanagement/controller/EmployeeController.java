package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.dto.EmployeeDto;
import com.medicalsuppliesmanagement.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final IEmployeeService employeeService;

    @GetMapping("/employee/profile")
    public String viewEmployeeProfile(Model model, Principal principal) {
        String username = "employee01";
        EmployeeDto dto = employeeService.getEmployeeProfile(username);
        model.addAttribute("employee", dto);
        return "employee/profile";
    }
}
