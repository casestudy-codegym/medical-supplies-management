package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.entity.Material;
import com.medicalsuppliesmanagement.service.impl.IMaterialDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/material")
public class MaterialDetailController {

    @Autowired
    private IMaterialDetailService materialDetailService;

    @GetMapping("/{id}")
    public String getMaterialDetailPageById(@PathVariable Long id, Model model) {
        Material material = materialDetailService.getMaterialById(id);
        model.addAttribute("material", material);
        return "material/detail";
    }
}

