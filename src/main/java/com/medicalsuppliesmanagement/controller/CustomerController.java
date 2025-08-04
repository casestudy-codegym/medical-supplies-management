package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.entity.Customer;
import com.medicalsuppliesmanagement.dto.CustomerDto;
import com.medicalsuppliesmanagement.service.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/management")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

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
    public String addCustomer(@Valid @ModelAttribute Customer customer,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {

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
        redirectAttributes.addFlashAttribute("success", "Thêm khách hàng thành công!");
        return "redirect:/management/customers";
    }

    // 👤 Hiển thị trang hồ sơ khách hàng
    @GetMapping("/profile")
    public String showCustomerProfile(Model model, Principal principal) {
        String username = principal.getName();
        CustomerDto dto = customerService.getCustomerProfile(username);
        model.addAttribute("customer", dto);
        return "customer/profile";
    }

    // ✏️ Hiển thị form chỉnh sửa hồ sơ khách hàng
    @GetMapping("/edit")
    public String showEditForm(Model model, Principal principal) {
        String username = principal.getName();
        CustomerDto dto = customerService.getCustomerProfile(username);
        model.addAttribute("customer", dto);
        return "customer/edit";
    }

    // 💾 Cập nhật thông tin hồ sơ khách hàng
    @PostMapping("/edit")
    public String updateCustomer(@ModelAttribute("customer") CustomerDto dto,
                                 Principal principal,
                                 RedirectAttributes redirect) {
        dto.setUsername(principal.getName());
        customerService.updateCustomer(dto);
        redirect.addFlashAttribute("success", "Cập nhật thông tin thành công!");
        return "redirect:/management/profile";
    }
}
