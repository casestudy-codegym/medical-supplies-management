package com.medicalsuppliesmanagement.controller;

import com.medicalsuppliesmanagement.entity.Material;
import com.medicalsuppliesmanagement.entity.Material_Image;
import com.medicalsuppliesmanagement.exception.ResourceNotFoundException;
import com.medicalsuppliesmanagement.repository.IMaterialImageRepository;
import com.medicalsuppliesmanagement.service.impl.IMaterialDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/materials")
public class MaterialImageApiController {

    @Autowired
    private IMaterialDetailService materialDetailService;

    @Autowired
    private IMaterialImageRepository materialImageRepository;

    @PostMapping("/{id}/images")
    public ResponseEntity<String> addImageApi(@PathVariable Long id,
                                              @RequestParam String imageUrl) {
        Material material = materialDetailService.getMaterialById(id);
        if (material == null) {
            return ResponseEntity.notFound().build();
        }
        Material_Image image = new Material_Image();
        image.setImageUrl(imageUrl);
        image.setMaterial(material);
        materialImageRepository.save(image);

        return ResponseEntity.ok("Thêm ảnh thành công");
    }
    @PostMapping("/{id}/images/delete")
    public ResponseEntity<String> deleteImageApi(@PathVariable Long id,
                                                 @RequestParam Long imageId) {
        Material material = materialDetailService.getMaterialById(id);
        if (material == null) {
            return ResponseEntity.notFound().build();
        }
        Material_Image image = materialImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy ảnh với ID: " + imageId));

        if (!image.getMaterial().getId().equals(id)) {
            return ResponseEntity.badRequest().body("Ảnh không thuộc về sản phẩm này");
        }

        materialImageRepository.delete(image);
        return ResponseEntity.ok("Xóa ảnh thành công");
    }
}
