package com.medicalsuppliesmanagement.service.impl;

import com.medicalsuppliesmanagement.entity.Employee;
import com.medicalsuppliesmanagement.entity.UserAccount;
import com.medicalsuppliesmanagement.repository.IEmployeeRepository;
import com.medicalsuppliesmanagement.repository.IUserRepository;
import com.medicalsuppliesmanagement.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService implements IEmployeeService {

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Employee addEmployeeWithAccount(Employee employee, UserAccount userAccount) {
        // Kiểm tra username đã tồn tại
        if (userRepository.existsByUsername(userAccount.getUsername())) {
            throw new IllegalArgumentException("Tên tài khoản đã tồn tại");
        }

        // Kiểm tra password
        if (userAccount.getPassword() == null || userAccount.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống");
        }

        // Kiểm tra mã nhân viên đã tồn tại
        if (employeeRepository.existsByEmployeeCode(employee.getEmployeeCode())) {
            throw new IllegalArgumentException("Mã nhân viên " + employee.getEmployeeCode() + " đã tồn tại");
        }

        // Mã hóa password
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));

        // Set default status nếu chưa có
        if (userAccount.getStatus() == null || userAccount.getStatus().isEmpty()) {
            userAccount.setStatus("ACTIVE");
        }

        // Lưu user account trước
        UserAccount savedAccount = userRepository.save(userAccount);

        // Set user account cho employee và lưu
        employee.setUserAccount(savedAccount);
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Employee employee) {
        // Kiểm tra employee có tồn tại không
        Employee existingEmployee = employeeRepository.findById(employee.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên"));

        // Kiểm tra mã nhân viên có bị trùng với nhân viên khác không
        if (!existingEmployee.getEmployeeCode().equals(employee.getEmployeeCode()) &&
                employeeRepository.existsByEmployeeCode(employee.getEmployeeCode())) {
            throw new IllegalArgumentException("Mã nhân viên " + employee.getEmployeeCode() + " đã tồn tại");
        }

        // Cập nhật thông tin user account
        UserAccount existingUser = existingEmployee.getUserAccount();
        UserAccount updatedUser = employee.getUserAccount();

        // Giữ lại các thông tin quan trọng
        updatedUser.setUserId(existingUser.getUserId());
        updatedUser.setUsername(existingUser.getUsername());
        updatedUser.setPassword(existingUser.getPassword());
        updatedUser.setCreatedAt(existingUser.getCreatedAt());

        if (updatedUser.getStatus() == null || updatedUser.getStatus().isEmpty()) {
            updatedUser.setStatus(existingUser.getStatus());
        }

        // Lưu user account
        UserAccount savedUser = userRepository.save(updatedUser);
        employee.setUserAccount(savedUser);

        // Lưu employee
        return employeeRepository.save(employee);
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Page<Employee> searchByFullName(String keyword, Pageable pageable) {
        return employeeRepository.findByUserAccountFullNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public Page<Employee> searchEmployees(String keyword, String position, Pageable pageable) {
        return employeeRepository.searchEmployees(keyword, position, pageable);
    }

    @Override
    public Optional<Employee> findByEmployeeCode(String employeeCode) {
        return  employeeRepository.findByEmployeeCode(employeeCode);
    }

    @Override
    public List<String> getAllDistinctPositions() {
        return employeeRepository.findAllDistinctPositions();
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên"));

        // Xóa employee trước, sau đó xóa user account
        Long userId = employee.getUserAccount().getUserId();
        employeeRepository.deleteById(id);
        userRepository.deleteById(userId);
    }

    @Override
    public void deleteMultiple(List<Long> employeeIds) {
        // Lấy danh sách nhân viên để kiểm tra trước khi xóa
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        if (employees.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy nhân viên nào để xóa");
        }

        // Xóa từng nhân viên và tài khoản của họ
        for (Employee employee : employees) {
            Long userId = employee.getUserAccount().getUserId();
            employeeRepository.deleteById(employee.getEmployeeId());
            userRepository.deleteById(userId);
        }
    }

    @Override
    public boolean existsByEmployeeCode(String employeeCode) {
        return  employeeRepository.existsByEmployeeCode(employeeCode);
    }

    @Override
    public long countEmployees() {
        return employeeRepository.count();
    }
}