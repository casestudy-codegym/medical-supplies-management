package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.entity.Customer;
import com.medicalsuppliesmanagement.service.impl.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/management")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // 📄 Danh sách khách hàng với phân trang và tìm kiếm
    @GetMapping("/customers")
    public String listCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type,
            Model model) {

        int size = 10;
        Page<?> customerPage = customerService.searchCustomers(keyword, type, page, size);

        model.addAttribute("customers", customerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("type", type);
        return "customer/list";
    }

    // 🔄 Hiển thị form thêm mới
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("action", "Thêm khách hàng");
        return "customer/add";
    }

    @PostMapping("/new")
    public String addCustomer(@Valid @ModelAttribute Customer customer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("action", "Thêm khách hàng");
            return "customer/add";
        }

        if (customerService.findByCustomerCode(customer.getCustomerCode()).isPresent()) {
            result.rejectValue("customerCode", "error.customer", "Mã khách hàng đã tồn tại");
            model.addAttribute("action", "Thêm khách hàng");
            return "customer/add";
        }

        customerService.save(customer);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm khách hàng thành công!");
        return "redirect:/management/customers";
    }

    // ✏️ Hiển thị form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy khách hàng!");
            return "redirect:/management/customers";
        }

        model.addAttribute("customer", customer);
        model.addAttribute("action", "Chỉnh sửa khách hàng");
        return "customer/update";
    }

    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable Long id, @Valid @ModelAttribute Customer customer,
                                 BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("action", "Chỉnh sửa khách hàng");
            return "customer/update";
        }

        // Kiểm tra trùng mã khách hàng
        if (customerService.findByCustomerCode(customer.getCustomerCode()).isPresent() &&
                !customer.getCustomerId().equals(id)) {
            result.rejectValue("customerCode", "error.customer", "Mã khách hàng đã tồn tại");
            model.addAttribute("action", "Chỉnh sửa khách hàng");
            return "customer/update";
        }

        customer.setCustomerId(id);
        customerService.update(customer);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật khách hàng thành công!");
        return "redirect:/management/customers";
    }

    // 🗑️ Xóa khách hàng đơn lẻ
    @PostMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Customer customer = customerService.findById(id);
            if (customer == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy khách hàng cần xóa!");
                return "redirect:/management/customers";
            }

            customerService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Đã xóa khách hàng: " + customer.getName());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Không thể xóa khách hàng. Có thể khách hàng đang được sử dụng trong hệ thống!");
        }
        return "redirect:/management/customers";
    }

    // 🗑️ Xóa nhiều khách hàng
    @PostMapping("/customers/delete-multiple")
    public String deleteSelected(@RequestParam(value = "selectedIds", required = false) List<Long> ids,
                                 RedirectAttributes redirectAttributes) {
        if (ids == null || ids.isEmpty()) {
            redirectAttributes.addFlashAttribute("warningMessage", "Vui lòng chọn ít nhất một khách hàng để xóa!");
            return "redirect:/management/customers";
        }

        try {
            customerService.deleteByIds(ids);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Đã xóa thành công " + ids.size() + " khách hàng!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Không thể xóa một số khách hàng. Có thể chúng đang được sử dụng trong hệ thống!");
        }

        return "redirect:/management/customers";
    }
}