package com.medicalsuppliesmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "material_code")
    private String materialCode;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "img_url", columnDefinition = "TEXT")
    private String imgUrl;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit")
    private String unit;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Material_Image> images;

    public List<String> getAllImages() {
        List<String> allImages = new ArrayList<>();
        // thêm ảnh chính trước
        if (imgUrl != null && !imgUrl.isEmpty()) {
            allImages.add(imgUrl);
        }
        // thêm các ảnh chi tiết
        if (images != null) {
            allImages.addAll(images.stream()
                    .map(Material_Image::getImageUrl)
                    .toList());
        }
        return allImages;
    }

    public void setImgUrls(List<String> imgUrls) {
        if (images == null) {
            images = new java.util.ArrayList<>();
        }
        images.clear();
        for (String url : imgUrls) {
            Material_Image image = new Material_Image();
            image.setImageUrl(url);
            image.setMaterial(this);
            images.add(image);
        }
    }
    public void addImage(Material_Image image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(image);
        image.setMaterial(this);
    }
    public void removeImage(Material_Image image) {
        if (images != null) {
            images.remove(image);
            image.setMaterial(null);
        }
    }
}
