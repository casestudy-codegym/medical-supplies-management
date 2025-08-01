package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.dto.EmployeeAccountDTO;
import com.medicalsuppliesmanagement.entity.Employee;
import com.medicalsuppliesmanagement.entity.UserAccount;
import com.medicalsuppliesmanagement.service.IEmployeeService;
import com.medicalsuppliesmanagement.util.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private IEmployeeService employeeService;
    
    @GetMapping("/add-employee-account")
    public String addEmployeeAccount(Model model) {
        if (!model.containsAttribute("employeeDTO")) {
            model.addAttribute("employeeDTO", new EmployeeAccountDTO());
        }
        model.addAttribute("roles", UserAccount.Role.values());
        model.addAttribute("genders", UserAccount.Gender.values());
        return "admin/addEmployeeAccount";
    }
    
    @PostMapping("/add-employee-account")
    public String processAddEmployeeAccount(
            @Valid
            @ModelAttribute("employeeDTO") EmployeeAccountDTO employeeDTO,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", UserAccount.Role.values());
            model.addAttribute("genders", UserAccount.Gender.values());
            return "admin/addEmployeeAccount";
        }

        try {
            Employee employee = new Employee();
            employee.setEmployeeCode(employeeDTO.getEmployeeCode());
            employee.setPosition(employeeDTO.getPosition());

            UserAccount userAccount = getUserAccount(employeeDTO);

            employeeService.addEmployeeWithAccount(employee, userAccount);
            
            // Gửi email thông tin tài khoản cho nhân viên mới
            if (employeeDTO.getEmail() != null && !employeeDTO.getEmail().isEmpty()) {
                EmailService.sendEmployeeAccountInfo(employeeDTO);
            }

            redirectAttributes.addFlashAttribute("successMessage", "Thêm tài khoản nhân viên thành công!");
            return "redirect:/admin/add-employee-account";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("employeeDTO", employeeDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.employeeDTO", bindingResult);
            return "redirect:/admin/add-employee-account";
        }
    }

    private static UserAccount getUserAccount(EmployeeAccountDTO employeeDTO) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(employeeDTO.getUsername());
        userAccount.setPassword(employeeDTO.getPassword());
        userAccount.setFullName(employeeDTO.getFullName());
        userAccount.setDateOfBirth(employeeDTO.getDateOfBirth());
        userAccount.setGender(employeeDTO.getGender());
        userAccount.setAddress(employeeDTO.getAddress());
        userAccount.setPhone(employeeDTO.getPhone());
        userAccount.setEmail(employeeDTO.getEmail());
        userAccount.setRole(employeeDTO.getRole());
        return userAccount;
    }
}
