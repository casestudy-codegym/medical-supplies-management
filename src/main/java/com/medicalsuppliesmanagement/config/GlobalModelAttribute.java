package com.medicalsuppliesmanagement.config;

import com.medicalsuppliesmanagement.service.impl.InvoiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttribute {

    @Autowired
    private InvoiceItemService invoiceItemService;

    @ModelAttribute("cartCount")
    public int cartCount() {
        return invoiceItemService.getCartCount();
    }
}
