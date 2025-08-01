package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.entity.Customer;
import com.medicalsuppliesmanagement.entity.Material;
import com.medicalsuppliesmanagement.repository.ICustomerRepository;
import com.medicalsuppliesmanagement.service.impl.MaterialDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/material")
public class MaterialDetailController {
    @Autowired
    private ICustomerRepository customerService;

    @Autowired
    private MaterialDetailService materialDetailService;

    @GetMapping("/{id}")
    public String getMaterialDetailPageById(@PathVariable Long id, Model model) {
        Material material = materialDetailService.getMaterialById(id);
        model.addAttribute("material", material);
        return "material/detail";
    }

}

