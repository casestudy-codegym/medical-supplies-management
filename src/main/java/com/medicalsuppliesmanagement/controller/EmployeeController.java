package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.entity.Employee;
import com.medicalsuppliesmanagement.entity.UserAccount;
import com.medicalsuppliesmanagement.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService service;

    @GetMapping
    public String listEmployees(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(required = false) String keyword,
                                Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            employeePage = service.searchByFullName(keyword.trim(), pageable);
        } else {
            employeePage = service.findAll(pageable);
        }

        model.addAttribute("employeePage", employeePage);
        model.addAttribute("keyword", keyword);
        return "employee/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        Employee employee = new Employee();
        employee.setUserAccount(new UserAccount());
        model.addAttribute("employee", employee);
        return "employee/add";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("employee") Employee employee,
                       BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        // Kiểm tra validation errors
        if (result.hasErrors()) {
            return "employee/add";
        }

        // Kiểm tra các trường bắt buộc
        if (employee.getUserAccount().getUsername() == null || employee.getUserAccount().getUsername().trim().isEmpty()) {
            model.addAttribute("error", "Tên đăng nhập không được để trống");
            return "employee/add";
        }

        if (employee.getUserAccount().getPassword() == null || employee.getUserAccount().getPassword().trim().isEmpty()) {
            model.addAttribute("error", "Mật khẩu không được để trống");
            return "employee/add";
        }

        if (employee.getEmployeeCode() == null || employee.getEmployeeCode().trim().isEmpty()) {
            model.addAttribute("error", "Mã nhân viên không được để trống");
            return "employee/add";
        }

        if (employee.getUserAccount().getFullName() == null || employee.getUserAccount().getFullName().trim().isEmpty()) {
            model.addAttribute("error", "Họ tên không được để trống");
            return "employee/add";
        }

        try {
            service.addEmployeeWithAccount(employee, employee.getUserAccount());
            redirectAttributes.addFlashAttribute("success", "Thêm nhân viên thành công!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "employee/add";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "employee/add";
        }

        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Employee employee = service.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với ID: " + id));
            model.addAttribute("employee", employee);
            return "employee/update";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/employees";
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("employee") Employee employee,
                         BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "employee/update";
        }

        try {
            // Đảm bảo ID được set đúng
            employee.setEmployeeId(id);

            // Lấy employee hiện tại để giữ lại một số thông tin
            Employee existingEmployee = service.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

            // Giữ lại thông tin quan trọng từ user account hiện tại
            UserAccount existingUser = existingEmployee.getUserAccount();
            UserAccount updatedUser = employee.getUserAccount();

            // Giữ nguyên username và password
            updatedUser.setUserId(existingUser.getUserId());
            updatedUser.setUsername(existingUser.getUsername());
            updatedUser.setPassword(existingUser.getPassword());
            updatedUser.setStatus(existingUser.getStatus());
            updatedUser.setCreatedAt(existingUser.getCreatedAt());

            service.updateEmployee(employee);
            redirectAttributes.addFlashAttribute("success", "Cập nhật nhân viên thành công!");
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "employee/update";
        }

        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Xóa nhân viên thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa: " + e.getMessage());
        }
        return "redirect:/employees";
    }
}