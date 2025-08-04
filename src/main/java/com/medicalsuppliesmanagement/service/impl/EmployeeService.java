package com.medicalsuppliesmanagement.service.impl;

import com.medicalsuppliesmanagement.dto.EmployeeDto;
import com.medicalsuppliesmanagement.entity.Employee;
import com.medicalsuppliesmanagement.entity.UserAccount;
import com.medicalsuppliesmanagement.entity.UserAccount.Gender;
import com.medicalsuppliesmanagement.repository.IEmployeeRepository;
import com.medicalsuppliesmanagement.repository.ISalaryRepository;
import com.medicalsuppliesmanagement.repository.IUserRepository;
import com.medicalsuppliesmanagement.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Autowired
    private ISalaryRepository salaryRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public EmployeeDto getEmployeeProfile(String username) {
        Employee employee = employeeRepository.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        EmployeeDto dto = new EmployeeDto();
        dto.setUsername(employee.getUser().getUsername());
        dto.setFullName(employee.getUser().getFullName());

        String avatarUrl = employee.getUser().getAvatarUrl();
        if (avatarUrl == null || avatarUrl.isBlank()) {
            dto.setAvatarUrl("https://www.w3schools.com/howto/img_avatar.png");
        } else {
            dto.setAvatarUrl(avatarUrl);
        }

        Gender gender = employee.getUser().getGender();
        dto.setGender(gender);
        dto.setGenderDisplay(gender == Gender.NAM ? "Nam" : "Nữ");

        dto.setPhone(employee.getUser().getPhone());
        dto.setEmail(employee.getUser().getEmail());
        dto.setAddress(employee.getUser().getAddress());

        if (employee.getUser().getDateOfBirth() != null) {
            dto.setDateOfBirth(employee.getUser().getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }

        dto.setEmployeeCode(employee.getEmployeeCode());
        dto.setPosition(employee.getPosition());

        salaryRepository.findFirstByEmployee_User_UsernameOrderByYearDescMonthDesc(username)
                .ifPresent(salary -> dto.setLatestSalary(salary.getBaseSalary()));

        return dto;
    }

    @Override
    public void updateEmployee(EmployeeDto dto) {
        Employee employee = employeeRepository.findByUser_Username(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.getUser().setFullName(dto.getFullName());
        employee.getUser().setPhone(dto.getPhone());
        employee.getUser().setEmail(dto.getEmail());
        employee.getUser().setAddress(dto.getAddress());

        if (dto.getDateOfBirth() != null && !dto.getDateOfBirth().isBlank()) {
            employee.getUser().setDateOfBirth(
                    java.time.LocalDate.parse(dto.getDateOfBirth())
            );
        }

        if (dto.getAvatarUrl() != null && !dto.getAvatarUrl().isBlank()) {
            employee.getUser().setAvatarUrl(dto.getAvatarUrl());
        }

        employeeRepository.save(employee);
    }

    @Override
    public Employee addEmployeeWithAccount(Employee employee, UserAccount userAccount) {
        if (employeeRepository.existsByEmployeeCode(employee.getEmployeeCode())) {
            throw new IllegalArgumentException("Mã nhân viên " + employee.getEmployeeCode() + " đã tồn tại");
        }

        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        userAccount.setStatus(UserAccount.Status.ACTIVE.toString());
        UserAccount savedAccount = userRepository.save(userAccount);

        employee.setUser(savedAccount);
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Employee employee) {
        Employee existingEmployee = employeeRepository.findById(employee.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên"));

        if (!existingEmployee.getEmployeeCode().equals(employee.getEmployeeCode()) &&
                employeeRepository.existsByEmployeeCode(employee.getEmployeeCode())) {
            throw new IllegalArgumentException("Mã nhân viên " + employee.getEmployeeCode() + " đã tồn tại");
        }

        UserAccount existingUser = existingEmployee.getUser();
        UserAccount updatedUser = employee.getUser();

        updatedUser.setUserId(existingUser.getUserId());
        updatedUser.setUsername(existingUser.getUsername());
        updatedUser.setPassword(existingUser.getPassword());
        updatedUser.setCreatedAt(existingUser.getCreatedAt());

        if (updatedUser.getStatus() == null || updatedUser.getStatus().isEmpty()) {
            updatedUser.setStatus(existingUser.getStatus());
        }

        UserAccount savedUser = userRepository.save(updatedUser);
        employee.setUser(savedUser);

        return employeeRepository.save(employee);
    }

    @Override
    public Page<Employee> searchEmployees(String keyword, String position, Pageable pageable) {
        return employeeRepository.searchEmployees(keyword, position, pageable);
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
    @Transactional
    public void deleteById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên"));

        Long userId = employee.getUser().getUserId();
        employeeRepository.deleteById(id);
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public void deleteMultiple(List<Long> employeeIds) {
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        if (employees.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy nhân viên nào để xóa");
        }

        for (Employee employee : employees) {
            Long userId = employee.getUser().getUserId();
            employeeRepository.deleteById(employee.getEmployeeId());
            userRepository.deleteById(userId);
        }
    }

    @Override
    public long countEmployees() {
        return employeeRepository.count();
    }
}
