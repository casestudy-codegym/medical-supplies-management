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

import java.util.List;

@Controller
@RequestMapping("/employees")
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
                position != null && !position.equals("ALL") ? position : null,
                pageable
        );

        model.addAttribute("employeePage", employeePage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("position", position);
        model.addAttribute("positions", employeeService.getAllDistinctPositions()); // để render dropdown
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
            employeeService.addEmployeeWithAccount(employee, employee.getUserAccount());
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
                         BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "employee/update";
        }

        try {
            // Đảm bảo ID được set đúng
            employee.setEmployeeId(id);

            // Lấy employee hiện tại để giữ lại một số thông tin
            Employee existingEmployee = employeeService.findById(id)
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

    // Xóa nhiều nhân viên
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