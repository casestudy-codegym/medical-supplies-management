package com.medicalsuppliesmanagement.repository;

import com.medicalsuppliesmanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

    // Tìm kiếm theo tên (đã có)
    Page<Employee> findByUserAccountFullNameContainingIgnoreCase(String keyword, Pageable pageable);

    // Kiểm tra mã nhân viên đã tồn tại (đã có)
    boolean existsByEmployeeCode(String employeeCode);

    // Tìm kiếm theo chức vụ
    Page<Employee> findByPositionContainingIgnoreCase(String position, Pageable pageable);

    // Tìm kiếm kết hợp tên và chức vụ
    @Query("SELECT e FROM Employee e WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR LOWER(e.userAccount.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:position IS NULL OR :position = '' OR LOWER(e.position) LIKE LOWER(CONCAT('%', :position, '%')))")
    Page<Employee> findByKeywordAndPosition(@Param("keyword") String keyword,
                                            @Param("position") String position,
                                            Pageable pageable);

    // Tìm kiếm linh hoạt với nhiều điều kiện
    @Query("SELECT e FROM Employee e WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(e.userAccount.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.employeeCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.userAccount.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:position IS NULL OR :position = '' OR LOWER(e.position) = LOWER(:position))")
    Page<Employee> searchEmployees(@Param("keyword") String keyword,
                                   @Param("position") String position,
                                   Pageable pageable);

    // Tìm theo mã nhân viên
    Optional<Employee> findByEmployeeCode(String employeeCode);

    // Lấy danh sách chức vụ duy nhất (để dropdown)
    @Query("SELECT DISTINCT e.position FROM Employee e WHERE e.position IS NOT NULL ORDER BY e.position")
    List<String> findDistinctPositions();

    // Đếm nhân viên theo chức vụ
    Long countByPosition(String position);


    // Xóa nhiều nhân viên theo danh sách ID
    void deleteByEmployeeIdIn(List<Long> employeeIds);

    // Tìm kiếm nâng cao với JOIN FETCH để tránh N+1 query
    @Query("SELECT DISTINCT e FROM Employee e LEFT JOIN FETCH e.userAccount WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(e.userAccount.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.employeeCode) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:position IS NULL OR :position = '' OR LOWER(e.position) = LOWER(:position))")
    Page<Employee> searchEmployeesWithUserAccount(@Param("keyword") String keyword,
                                                  @Param("position") String position,
                                                  Pageable pageable);
    @Query("SELECT DISTINCT e.position FROM Employee e")
    List<String> findAllDistinctPositions();
}