package com.medicalsuppliesmanagement.service.impl;

import com.medicalsuppliesmanagement.entity.Material;
import com.medicalsuppliesmanagement.repository.IMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MaterialService {

    @Autowired
    private IMaterialRepository materialRepository;

    public Page<Material> findAll(Pageable pageable) {
        return materialRepository.findAll(pageable);
    }

    public Page<Material> findByCategoryId(Long categoryId, Pageable pageable) {
        return materialRepository.findByCategoryId(categoryId, pageable);
    }

    public Page<Material> findByCategoryIdOrderByNameAsc(Long categoryId, Pageable pageable) {
        return materialRepository.findByCategoryIdOrderByNameAsc(categoryId, pageable);
    }

    public Page<Material> findWithFilters(Long categoryId, String search,
                                          Double minPrice, Double maxPrice,
                                          Pageable pageable) {
        return materialRepository.findWithFilters(categoryId, search, minPrice, maxPrice, pageable);
    }

    public Optional<Material> findById(Long id) {
        return materialRepository.findById(id);
    }

    public Material save(Material material) {
        return materialRepository.save(material);
    }

    public void deleteById(Long id) {
        materialRepository.deleteById(id);
    }
}