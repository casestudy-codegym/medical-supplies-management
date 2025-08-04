package com.medicalsuppliesmanagement.service;


import com.medicalsuppliesmanagement.entity.Invoice;

public interface IinvoiceItemService {
    void addToCart(Long materialId, int quantity);
    void removeFromCart(Long materialId);
    void clearCart();
    Invoice createInvoiceForCart(Long customerId, Long employeeId, Double discountPercent, Double vatPercent);

    Object getCartTotal();

    Invoice getInvoiceById(Long id);

    Object getCartItems();
    Object getCart(); // Thêm phương thức này để lấy giỏ hàng từ session

}
