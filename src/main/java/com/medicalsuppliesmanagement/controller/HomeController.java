package com.medicalsuppliesmanagement.controller;
import com.medicalsuppliesmanagement.entity.Category;
import com.medicalsuppliesmanagement.entity.Material;
import com.medicalsuppliesmanagement.service.CategoryService;
import com.medicalsuppliesmanagement.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.medicalsuppliesmanagement.service.impl.CustomerService;
import com.medicalsuppliesmanagement.service.impl.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

//    @Autowired
//    private CategoryService categoryService;
//
//    @Autowired
//    private MaterialService materialService;
//
//    @GetMapping("/material")
//    public String home(Model model,
//                       @RequestParam(defaultValue = "0") int page,
//                       @RequestParam(required = false, name = "category") Long categoryId,
//                       @RequestParam(required = false) String search,
//                       @RequestParam(required = false) Double minPrice,
//                       @RequestParam(required = false) Double maxPrice) {
//
//        List<Category> categories = categoryService.findAll();
//        model.addAttribute("categories", categories);
//        model.addAttribute("searchQuery", search);
//        model.addAttribute("minPrice", minPrice);
//        model.addAttribute("maxPrice", maxPrice);
//
//        Page<Material> materialList = materialService.findWithFilters(
//                categoryId, search, minPrice, maxPrice, PageRequest.of(page, 8)
//        );
//        model.addAttribute("materialList", materialList);
//
//        if (categoryId != null) {
//            Optional<Category> category = categoryService.findById(categoryId);
//            if (category.isPresent()) {
//                model.addAttribute("categoryWord", category.get());
//            }
//        }
//
//        return "material/list";
//    }
//
//    @GetMapping("/material/category")
//    public String getByCategory(Model model,
//                                @RequestParam(defaultValue = "0") int page,
//                                @RequestParam(required = false, name = "category") Long id) {
//
//        Page<Material> materialList = materialService.findByCategoryIdOrderByNameAsc(id, PageRequest.of(page, 8));
//        List<Category> categories = categoryService.findAll();
//
//        model.addAttribute("categories", categories);
//        model.addAttribute("materialList", materialList);
//
//        Optional<Category> category = categoryService.findById(id);
//        if (category.isPresent()) {
//            model.addAttribute("categoryWord", category.get());
//        } else {
//            model.addAttribute("error", "Category not found");
//            return "redirect:/";
//        }
//
//        return "material/list";
//    }
//
//    @GetMapping("/materials")
//    public String getMaterialDetail(Model model,
//                                    @RequestParam Long id) {
//        Optional<Material> material = materialService.findById(id);
//        if (material.isPresent()) {
//            model.addAttribute("material", material.get());
//            return "material/detail";
//        } else {
//            model.addAttribute("error", "Material not found");
//            return "redirect:/";
//        }
//    }
}