package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.entity.Invoice;
import com.medicalsuppliesmanagement.service.impl.InvoiceItemService;
import com.medicalsuppliesmanagement.service.impl.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.NumberFormat;
import java.util.Locale;

@Controller
public class CartController {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceItemService invoiceItemService;

    @GetMapping("/cart")
    public String getCartPage(Model model) {
        double total = (double) invoiceItemService.getCartTotal();  // <-- lấy tổng trước
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        model.addAttribute("totalFormatted", formatter.format(total));

        model.addAttribute("cartItems", invoiceItemService.getCartItems());
        model.addAttribute("total", total);  // vẫn gửi số gốc nếu cần dùng

        return "material/cart"; // => trả về templates/material/cart.html
    }
    @PostMapping("/cart/action")
    public String handleCartAction(@RequestParam String action,
                                   @RequestParam(required = false) Long id) {
        switch (action) {
            case "removeFromCart" -> {
                if (id != null) invoiceItemService.removeFromCart(id);
            }
            case "clearCart" -> invoiceItemService.clearCart();
            default -> throw new IllegalArgumentException("Invalid action: " + action);
        }
        return "redirect:/cart";
    }
        @PostMapping("/pay")
    public String checkout(@RequestParam Long customerId,
                           @RequestParam Long employeeId,
                           Model model) {
        Invoice invoice = invoiceItemService.createInvoiceForCart(customerId, employeeId, 0.0, 0.0);
        model.addAttribute("invoice", invoice);
        return "material/pay";
    }
    @PostMapping("/invoice/confirmPayment")
    public String confirmPayment(@RequestParam Long invoiceId,
                                 @RequestParam String paymentMethod,
                                 RedirectAttributes redirectAttributes) {
        // Update trạng thái hóa đơn hoặc ghi log thanh toán
        invoiceService.confirmPayment(invoiceId, paymentMethod);

        redirectAttributes.addFlashAttribute("message",
                "Thanh toán thành công bằng " + ("CASH".equals(paymentMethod) ? "Tiền mặt" : "Chuyển khoản"));
        return "redirect:/invoice/" + invoiceId;
    }
    @GetMapping("/cart/invoice/{id}")
    public String viewInvoice(@PathVariable Long id, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        model.addAttribute("invoice", invoice);
        return "invoice/sales/detail";  // file ở templates/invoice/detail.html
    }


}
