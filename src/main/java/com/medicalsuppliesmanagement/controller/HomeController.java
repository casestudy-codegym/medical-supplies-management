package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.entity.Category;
import com.medicalsuppliesmanagement.entity.MedicalSupply;
import com.medicalsuppliesmanagement.service.CategoryService;
import com.medicalsuppliesmanagement.service.MedicalSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MedicalSupplyService medicalSupplyService;

    @GetMapping("/")
    public String home(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(required = false, name = "category") Long categoryId,
                       @RequestParam(required = false) String search,
                       @RequestParam(required = false) BigDecimal minPrice,
                       @RequestParam(required = false) BigDecimal maxPrice) {

        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        model.addAttribute("searchQuery", search);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        Page<MedicalSupply> supplyList;

        supplyList = medicalSupplyService.findWithFilters(
                categoryId, search, minPrice, maxPrice, PageRequest.of(page, 8)
        );

        model.addAttribute("supplyList", supplyList);

        if (categoryId != null) {
            Optional<Category> category = categoryService.findById(categoryId);
            if (category.isPresent()) {
                model.addAttribute("categoryWord", category.get());
            }
        }

        return "medical/list";
    }

    @GetMapping("/category")
    public String getByCategory(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(required = false, name = "category") Long id) {
        Page<MedicalSupply> supplyList = medicalSupplyService.findByCategoryIdOrderByNameAsc(id, PageRequest.of(page, 8));
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("supplyList", supplyList);
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            model.addAttribute("categoryWord", category.get());
        } else {
            model.addAttribute("error", "Category not found");
            return "redirect:/";
        }
        return "medical/list";
    }

}