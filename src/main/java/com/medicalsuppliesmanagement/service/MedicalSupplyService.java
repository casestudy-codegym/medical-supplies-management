package com.medicalsuppliesmanagement.service;

import com.medicalsuppliesmanagement.entity.MedicalSupply;
import com.medicalsuppliesmanagement.repository.IMedicalSupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MedicalSupplyService {
    @Autowired
    IMedicalSupplyRepository medicalSupplyRepository;

    public Page<MedicalSupply> findAll(Pageable pageable) {
        return medicalSupplyRepository.findAll(pageable);
    }

    public Page<MedicalSupply> findByCategoryId(Long categoryId, Pageable pageable){
        return medicalSupplyRepository.findByCategoryId(categoryId, pageable);
    }

    public Page<MedicalSupply> findByCategoryIdOrderByNameAsc(Long categoryId, Pageable pageable){
        return medicalSupplyRepository.findByCategoryIdOrderByNameAsc(categoryId, pageable);
    }

    public Page<MedicalSupply> findWithFilters(Long categoryId, String search,
                                               BigDecimal minPrice, BigDecimal maxPrice,
                                               Pageable pageable) {
        return medicalSupplyRepository.findWithFilters(categoryId, search, minPrice, maxPrice, pageable);
    }

}