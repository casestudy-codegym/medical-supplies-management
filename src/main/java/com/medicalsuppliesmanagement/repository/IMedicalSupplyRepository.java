package com.medicalsuppliesmanagement.repository;

import com.medicalsuppliesmanagement.entity.MedicalSupply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface IMedicalSupplyRepository extends JpaRepository<MedicalSupply, Long> {

    Page<MedicalSupply> findByCategoryId(Long categoryId, Pageable pageable);
    Page<MedicalSupply> findByCategoryIdOrderByNameAsc(Long categoryId, Pageable pageable);

    @Query("SELECT m FROM MedicalSupply m WHERE " +
            "(:categoryId IS NULL OR m.category.id = :categoryId) AND " +
            "(:search IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(m.type) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:minPrice IS NULL OR m.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR m.price <= :maxPrice) " +
            "ORDER BY m.name ASC")
    Page<MedicalSupply> findWithFilters(@Param("categoryId") Long categoryId,
                                        @Param("search") String search,
                                        @Param("minPrice") BigDecimal minPrice,
                                        @Param("maxPrice") BigDecimal maxPrice,
                                        Pageable pageable);

}