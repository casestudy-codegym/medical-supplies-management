package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.dto.CustomerDto;
import com.medicalsuppliesmanagement.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/profile")
    public String showCustomerProfile(Model model, Principal principal) {
        String username = principal.getName();
        CustomerDto dto = customerService.getCustomerProfile(username);
        model.addAttribute("customer", dto);
        return "customer/profile";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model, Principal principal) {
        String username = principal.getName();
        CustomerDto dto = customerService.getCustomerProfile(username);
        model.addAttribute("customer", dto);
        return "customer/edit";
    }

    @PostMapping("/edit")
    public String updateCustomer(@ModelAttribute("customer") CustomerDto dto,
                                 Principal principal,
                                 RedirectAttributes redirect) {
        dto.setUsername(principal.getName());
        customerService.updateCustomer(dto);
        redirect.addFlashAttribute("success", "Cập nhật thông tin thành công!");
        return "redirect:/customer/profile";
    }
}
