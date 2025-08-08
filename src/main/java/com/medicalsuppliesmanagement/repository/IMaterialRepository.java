package com.medicalsuppliesmanagement.repository;

import com.medicalsuppliesmanagement.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IMaterialRepository extends JpaRepository<Material, Long> {

    Page<Material> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Material> findByCategoryIdOrderByNameAsc(Long categoryId, Pageable pageable);

    @Query("SELECT m FROM Material m WHERE " +
            "(:categoryId IS NULL OR m.category.id = :categoryId) AND " +
            "(:search IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(m.description) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:minPrice IS NULL OR m.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR m.price <= :maxPrice) " +
            "ORDER BY m.name ASC")
    Page<Material> findWithFilters(@Param("categoryId") Long categoryId,
                                   @Param("search") String search,
                                   @Param("minPrice") Double minPrice,
                                   @Param("maxPrice") Double maxPrice,
                                   Pageable pageable);

    @Query("SELECT c.id, COUNT(m) FROM Material m RIGHT JOIN m.category c GROUP BY c.id")
    List<Object[]> countMaterialsByCategory();

}