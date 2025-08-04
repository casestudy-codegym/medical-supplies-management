package com.medicalsuppliesmanagement.repository;

import com.medicalsuppliesmanagement.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
}