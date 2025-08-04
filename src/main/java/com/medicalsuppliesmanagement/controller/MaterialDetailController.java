package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.entity.Category;
import com.medicalsuppliesmanagement.entity.Invoice;
import com.medicalsuppliesmanagement.entity.Material;
import com.medicalsuppliesmanagement.repository.ICustomerRepository;
import com.medicalsuppliesmanagement.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/material")
public class MaterialDetailController {
    @Autowired
    private ICustomerRepository customerService;

    @Autowired
    private MaterialDetailService materialDetailService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceItemService invoiceItemService;

    @GetMapping("/{id}")
    public String getMaterialDetailPageById(@PathVariable Long id, Model model) {
        Material material = materialDetailService.getMaterialById(id);
        model.addAttribute("material", material);
        return "material/detail";
    }

    @GetMapping("/")
    public String home(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(required = false, name = "category") Long categoryId,
                       @RequestParam(required = false) String search,
                       @RequestParam(required = false) Double minPrice,
                       @RequestParam(required = false) Double maxPrice) {

        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("searchQuery", search);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        Page<Material> materialList = materialService.findWithFilters(
                categoryId, search, minPrice, maxPrice, PageRequest.of(page, 8)
        );
        model.addAttribute("materialList", materialList);

        if (categoryId != null) {
            Optional<Category> category = categoryService.findById(categoryId);
            if (category.isPresent()) {
                model.addAttribute("categoryWord", category.get());
            }
        }

        return "material/list";
    }

    @GetMapping("/category")
    public String getByCategory(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(required = false, name = "category") Long id) {

        Page<Material> materialList = materialService.findByCategoryIdOrderByNameAsc(id, PageRequest.of(page, 8));
        List<Category> categories = categoryService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("materialList", materialList);

        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            model.addAttribute("categoryWord", category.get());
        } else {
            model.addAttribute("error", "Category not found");
            return "redirect:/";
        }

        return "material/list";
    }

    @GetMapping("/materials")
    public String getMaterialDetail(Model model,
                                    @RequestParam Long id) {
        Optional<Material> material = materialService.findById(id);
        if (material.isPresent()) {
            model.addAttribute("material", material.get());
            return "material/detail";
        } else {
            model.addAttribute("error", "Material not found");
            return "redirect:/";
        }
    }

    @PostMapping("/materials/{id}/buy")
    public String buyOrAddToCart(@PathVariable Long id,
                                 @RequestParam int quantity,
                                 @RequestParam String action,
                                 RedirectAttributes redirectAttributes) {
        switch (action) {
            case "buy" -> {
                Invoice invoice = invoiceService.createInvoiceForMaterial(id, quantity);
                redirectAttributes.addFlashAttribute("message", "Mua vật tư y tế thành công!");
                return "redirect:/material/invoice/" + invoice.getId(); // <-- chuyển sang xem chi tiết
            }
            case "addToCart" -> {
                invoiceItemService.addToCart(id, quantity);
                redirectAttributes.addFlashAttribute("message", "Thêm vào giỏ hàng thành công!");
                return "redirect:/material/" + id;
            }
            default -> throw new IllegalArgumentException("Invalid action: " + action);
        }
    }
    @GetMapping("/invoice/{id}")
    public String viewInvoice(@PathVariable Long id, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        model.addAttribute("invoice", invoice);
        return "material/pay";
    }

}



