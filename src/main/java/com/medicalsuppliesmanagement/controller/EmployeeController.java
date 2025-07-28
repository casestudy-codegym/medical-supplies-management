package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/employees")
    public String showEmployeeList(Model model) {
        model.addAttribute("employeeList", service.findAll());
        return "employee/list";
    }
}
