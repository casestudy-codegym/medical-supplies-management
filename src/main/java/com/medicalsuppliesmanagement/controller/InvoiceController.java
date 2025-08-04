package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.entity.Invoice;
import com.medicalsuppliesmanagement.service.impl.InvoiceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceItemService invoiceItemService;

    @PostMapping("/checkout")
    public String checkout(@RequestParam(required = false) Long customerId,
                           @RequestParam(required = false) Long employeeId,
                           RedirectAttributes redirectAttributes) {
        if (customerId == null) customerId = 1L;
        if (employeeId == null) employeeId = 1L;
        Invoice invoice = invoiceItemService.createInvoiceForCart(customerId, employeeId, 0.0, 0.0);
        redirectAttributes.addFlashAttribute("success", "Thanh toán thành công!");
        return "redirect:/invoice/" + invoice.getId();
    }
    @GetMapping("/{id}")
    public String getInvoiceDetail(@PathVariable Long id, Model model) {
        Invoice invoice = invoiceItemService.getInvoiceById(id);
        if (invoice == null) {
            model.addAttribute("error", "Không tìm thấy hóa đơn với ID: " + id);
            return "error"; // Trả về trang lỗi nếu không tìm thấy hóa đơn
        }
        model.addAttribute("invoice", invoice);
        return "invoice/sales/detail"; // Trả về trang chi tiết hóa đơn
    }
}
