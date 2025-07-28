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
@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // 📄 Danh sách khách hàng với phân trang và tìm kiếm
    @GetMapping
    public String listCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword,
            Model model) {

        Page<Customer> customerPage = customerService.findByNameContainingIgnoreCase(keyword, PageRequest.of(page, 5));

        model.addAttribute("customers", customerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        return "customer/list"; // thymeleaf template
    }

    // 🔄 Hiển thị form thêm mới
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("action", "Thêm khách hàng");
        return "customer/add";
    }
    @PostMapping("/new")
    public String addCustomer(@Valid @ModelAttribute Customer customer, BindingResult result, Model model) {
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
        return "redirect:/customers";
    }

    // ✏️ Hiển thị form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id);
        if (customer == null) return "redirect:/customers";
        model.addAttribute("customer", customer);
        model.addAttribute("action", "Chỉnh sửa khách hàng");
        return "customer/update";
    }
    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable Long id, @Valid @ModelAttribute Customer customer, BindingResult result, Model model) {
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
        return "redirect:/customers";
    }

    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
        return "redirect:/customers";
    }

}
