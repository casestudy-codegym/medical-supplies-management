package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.entity.Category;
import com.medicalsuppliesmanagement.entity.Material;
import com.medicalsuppliesmanagement.repository.ICustomerRepository;
import com.medicalsuppliesmanagement.service.impl.CategoryService;
import com.medicalsuppliesmanagement.service.impl.MaterialService;
import com.medicalsuppliesmanagement.service.impl.MaterialDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public String getMaterialDetailPageById(@PathVariable Long id, Model model) {
        try {
            Material material = materialDetailService.getMaterialById(id);
            if (material == null) {
                model.addAttribute("error", "Material not found");
                return "redirect:/material/";
            }
            model.addAttribute("material", material);
            return "material/detail";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading material details");
            return "redirect:/material/";
        }
    }

    @GetMapping("")
    public String home(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(required = false, name = "category") Long categoryId,
                       @RequestParam(required = false) String search,
                       @RequestParam(required = false) Double minPrice,
                       @RequestParam(required = false) Double maxPrice) {
        try {
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
                category.ifPresent(cat -> model.addAttribute("categoryWord", cat));
            }

            return "material/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading materials");
            return "error/500";
        }
    }

    @GetMapping("/category")
    public String getByCategory(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(required = false, name = "category") Long id) {
        try {
            if (id == null) {
                return "redirect:/material/";
            }

            Page<Material> materialList = materialService.findByCategoryIdOrderByNameAsc(id, PageRequest.of(page, 8));
            List<Category> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("materialList", materialList);

            Optional<Category> category = categoryService.findById(id);
            if (category.isPresent()) {
                model.addAttribute("categoryWord", category.get());
                return "material/list";
            } else {
                model.addAttribute("error", "Category not found");
                return "redirect:/material/";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error loading category materials");
            return "redirect:/material/";
        }
    }

    @GetMapping("/materials")
    public String getMaterialDetail(Model model, @RequestParam Long id) {
        try {
            Optional<Material> material = materialService.findById(id);
            if (material.isPresent()) {
                model.addAttribute("material", material.get());
                return "material/detail";
            } else {
                model.addAttribute("error", "Material not found");
                return "redirect:/material/";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error loading material details");
            return "redirect:/material/";
        }
    }
}

