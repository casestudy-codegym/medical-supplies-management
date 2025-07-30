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

import java.time.format.DateTimeFormatter;

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
        if (userRepository.existsByUsername(userAccount.getUsername())) {
            throw new IllegalArgumentException("Tên tài khoản đã tồn tại");
        }

        if (userAccount.getPassword() == null || userAccount.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống");
        }

        if (employeeRepository.existsByEmployeeCode(employee.getEmployeeCode())) {
            throw new IllegalArgumentException("Mã nhân viên " + employee.getEmployeeCode() + " đã tồn tại");
        }

        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        userAccount.setStatus(UserAccount.Status.ACTIVE.toString());
        UserAccount savedAccount = userRepository.save(userAccount);
        employee.setUser(savedAccount);

        return employeeRepository.save(employee);
    }
}
