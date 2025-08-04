package com.medicalsuppliesmanagement.service.impl;

import com.medicalsuppliesmanagement.entity.*;
import com.medicalsuppliesmanagement.repository.*;

import com.medicalsuppliesmanagement.service.IinvoiceItemService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class InvoiceItemService implements IinvoiceItemService {

    private final IMaterialRepository materialRepository;
    private final ICustomerRepository customerRepository;
    private final IEmployeeRepository employeeRepository;
    private final IinvoiceRepository invoiceRepository;
    private final IinvoiceItemRepository invoiceItemRepository;
    private final HttpSession session;

    private static final String CART_SESSION_KEY = "CART";

    @SuppressWarnings("unchecked")
    public Map<Long, Integer> getCart() {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    @Override
    public void addToCart(Long materialId, int quantity) {
        Map<Long, Integer> cart = getCart();
        cart.put(materialId, cart.getOrDefault(materialId, 0) + quantity);
    }

    @Override
    public void removeFromCart(Long materialId) {
        getCart().remove(materialId);
    }

    @Override
    public void clearCart() {
        session.removeAttribute(CART_SESSION_KEY);
    }

    @Override
    public Invoice createInvoiceForCart(Long customerId, Long employeeId, Double discountPercent, Double vatPercent) {
        Map<Long, Integer> cart = getCart();
        if (cart.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống!");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));

        Invoice invoice = new Invoice();
        invoice.setInvoiceCode("INV-" + System.currentTimeMillis());
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setCustomer(customer);
        invoice.setEmployee(employee);
        invoice.setDiscountPercent(discountPercent != null ? discountPercent : 0.0);
        invoice.setVatPercent(vatPercent != null ? vatPercent : 0.0);

        List<InvoiceItem> items = new ArrayList<>();
        double total = 0;

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            Material material = materialRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Vật tư y tế không tồn tại với ID: " + entry.getKey()));
            int qty = entry.getValue();
            double price = material.getPrice() * qty;
            total += price;

            InvoiceItem item = new InvoiceItem();
            item.setInvoice(invoice);
            // Update the `InvoiceItem` class to accept `Material` or convert `Material` to `MedicalSupply`
            item.setMaterial(material.toMedicalSupply());
            item.setQuantity(qty);
            item.setUnitPrice(material.getPrice());
            item.setTotalPrice(price);
            items.add(item);
        }

        double discountAmount = total * (invoice.getDiscountPercent() / 100);
        double vatAmount = (total - discountAmount) * (invoice.getVatPercent() / 100);

        invoice.setDiscountAmount(discountAmount);
        invoice.setVatAmount(vatAmount);
        invoice.setTotalAmount(total - discountAmount + vatAmount);
        invoice.setInvoiceItems(items);

        invoiceRepository.save(invoice); // cascade sẽ lưu luôn InvoiceItem

        clearCart();
        return invoice;
    }

    @Override
    public Object getCartTotal() {
        Map<Long, Integer> cart = getCart();
        if (cart.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            Material material = materialRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Vật tư y tế không tồn tại với ID: " + entry.getKey()));
            total += material.getPrice() * entry.getValue();
        }
        return total;
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại với ID: " + id));
    }

    public Object getCartItems() {
        Map<Long, Integer> cart = getCart();
        if (cart.isEmpty()) {
            return Collections.emptyList();
        }

        List<InvoiceItem> items = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            Material material = materialRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Vật tư y tế không tồn tại với ID: " + entry.getKey()));
            int quantity = entry.getValue();

            InvoiceItem item = new InvoiceItem();
            item.setMaterial(material.toMedicalSupply());
            item.setQuantity(quantity);
            item.setUnitPrice(material.getPrice());
            item.setTotalPrice(material.getPrice() * quantity);
            items.add(item);
        }
        return items;
    }

    public int getCartCount() {
        Map<Long, Integer> cart = getCart();
        if (cart == null || cart.isEmpty()) {
            return 0;
        }
        return cart.values().stream().mapToInt(Integer::intValue).sum();
    }

    public Invoice checkout(Long id) {
        Map<Long, Integer> cart = getCart();
        if (cart.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống!");
        }

        // Giả sử bạn có ID của khách hàng và nhân viên
        Long customerId = (Long) session.getAttribute("customerId");
        Long employeeId = (Long) session.getAttribute("employeeId");

        if (customerId == null || employeeId == null) {
            throw new RuntimeException("Thông tin khách hàng hoặc nhân viên không hợp lệ");
        }

        // Tạo hóa đơn từ giỏ hàng
        createInvoiceForCart(customerId, employeeId, 0.0, 0.0);
        // Sau khi tạo hóa đơn, bạn có thể xóa giỏ hàng
        clearCart();
        return getInvoiceById(id);
    }
}
