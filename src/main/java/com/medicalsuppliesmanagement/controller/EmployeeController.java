package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.dto.EmployeeDto;
import com.medicalsuppliesmanagement.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final IEmployeeService employeeService;

    @GetMapping("/profile")
    public String showEmployeeProfile(Model model, Principal principal) {
        String username = principal.getName();
        EmployeeDto dto = employeeService.getEmployeeProfile(username);
        model.addAttribute("employee", dto);
        return "employee/profile";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model, Principal principal) {
        String username = principal.getName(); // ✅ lấy username
        EmployeeDto dto = employeeService.getEmployeeProfile(username);
        model.addAttribute("employee", dto);
        return "employee/edit";
    }

    @PostMapping("/edit")
    public String updateEmployee(@ModelAttribute("employee") EmployeeDto dto,
                                 RedirectAttributes redirect,
                                 Principal principal) {
        dto.setUsername(principal.getName());
        employeeService.updateEmployee(dto);
        redirect.addFlashAttribute("success", "Cập nhật thông tin thành công!");
        return "redirect:/employee/profile";
    }
}
