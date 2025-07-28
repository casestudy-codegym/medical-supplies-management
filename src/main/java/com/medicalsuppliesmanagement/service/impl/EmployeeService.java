package com.medicalsuppliesmanagement.service.impl;

import com.medicalsuppliesmanagement.entity.UserAccount.Gender;
import com.medicalsuppliesmanagement.dto.EmployeeDto;
import com.medicalsuppliesmanagement.entity.Employee;
import com.medicalsuppliesmanagement.repository.IEmployeeRepository;
import com.medicalsuppliesmanagement.repository.ISalaryRepository;
import com.medicalsuppliesmanagement.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;
    private final ISalaryRepository salaryRepository;

    @Override
    public EmployeeDto getEmployeeProfile(String username) {
        Employee employee = employeeRepository.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        EmployeeDto dto = new EmployeeDto();
        dto.setUsername(employee.getUser().getUsername());
        dto.setFullName(employee.getUser().getFullName());
        dto.setAvatarUrl(employee.getUser().getAvatarUrl());

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
}
