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
    @GetMapping("/")
    public String homePage(Model model) {
        return "home/HomePage";
    }
}