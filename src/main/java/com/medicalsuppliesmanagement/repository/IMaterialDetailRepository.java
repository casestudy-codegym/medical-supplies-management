package com.medicalsuppliesmanagement.repository;

import com.medicalsuppliesmanagement.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMaterialDetailRepository extends JpaRepository<Material,Long> {
}
