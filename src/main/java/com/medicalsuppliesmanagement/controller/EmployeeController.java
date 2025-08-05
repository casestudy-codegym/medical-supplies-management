package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.entity.Employee;
import com.medicalsuppliesmanagement.dto.EmployeeDto;
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
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @GetMapping
    public String listEmployees(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(required = false) String keyword,
                                @RequestParam(required = false) String position,
                                Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage = employeeService.searchEmployees(
                keyword != null ? keyword.trim() : null,
                position != null && !"ALL".equals(position) ? position : null,
                pageable
        );

        model.addAttribute("employeePage", employeePage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("position", position);
        model.addAttribute("positions", employeeService.getAllDistinctPositions());
        return "employee/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        Employee employee = new Employee();
        employee.setUser(new UserAccount());
        model.addAttribute("employee", employee);
        return "employee/add";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("employee") Employee employee,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "employee/add";
        }

        try {
            employeeService.addEmployeeWithAccount(employee, employee.getUser());
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

    @GetMapping("/profile")
    public String showEmployeeProfile(Model model, Principal principal) {
        String username = principal.getName();
        EmployeeDto dto = employeeService.getEmployeeProfile(username);
        model.addAttribute("employee", dto);
        return "employee/profile";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model, Principal principal) {
        String username = principal.getName();
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
        return "redirect:/employees/profile";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Employee employee = employeeService.findById(id)
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
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "employee/update";
        }

        try {
            employee.setEmployeeId(id);

            Employee existingEmployee = employeeService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

            UserAccount existingUser = existingEmployee.getUser();
            UserAccount updatedUser = employee.getUser();

            updatedUser.setUserId(existingUser.getUserId());
            updatedUser.setUsername(existingUser.getUsername());
            updatedUser.setPassword(existingUser.getPassword());
            updatedUser.setStatus(existingUser.getStatus());
            updatedUser.setCreatedAt(existingUser.getCreatedAt());

            employeeService.updateEmployee(employee);
            redirectAttributes.addFlashAttribute("success", "Cập nhật nhân viên thành công!");
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "employee/update";
        }

        return "redirect:/employees";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Đã xóa nhân viên thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa nhân viên này. Vui lòng kiểm tra ràng buộc dữ liệu!");
        }
        return "redirect:/employees";
    }

    @PostMapping("/delete-multiple")
    public String deleteMultipleEmployees(@RequestParam(value = "selectedIds", required = false) List<Long> selectedIds,
                                          RedirectAttributes redirectAttributes) {
        if (selectedIds == null || selectedIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng chọn ít nhất một nhân viên để xóa!");
            return "redirect:/employees";
        }

        try {
            employeeService.deleteMultiple(selectedIds);
            redirectAttributes.addFlashAttribute("success", "Đã xóa " + selectedIds.size() + " nhân viên!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa một số nhân viên. Vui lòng kiểm tra ràng buộc dữ liệu!");
        }
        return "redirect:/employees";
    }
}
