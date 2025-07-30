package com.medicalsuppliesmanagement.service.impl;
import com.medicalsuppliesmanagement.entity.Material;
import com.medicalsuppliesmanagement.exception.ResourceNotFoundException;
import com.medicalsuppliesmanagement.service.IMaterialDetailServiceI;
import com.medicalsuppliesmanagement.repository.IMaterialDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IMaterialDetailService implements IMaterialDetailServiceI {
    @Autowired
    private IMaterialDetailRepository IMaterialDetailRepository;
    @Override
    public Material getMaterialById(Long id) {
        return IMaterialDetailRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm có mã: " + id));
    }
}