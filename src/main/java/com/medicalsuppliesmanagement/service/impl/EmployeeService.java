package com.medicalsuppliesmanagement.service.impl;

import com.medicalsuppliesmanagement.entity.Employee;
import com.medicalsuppliesmanagement.entity.UserAccount;
import com.medicalsuppliesmanagement.repository.IEmployeeRepository;
import com.medicalsuppliesmanagement.repository.IUserRepository;
import com.medicalsuppliesmanagement.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private IEmployeeRepository employeeRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        employee.setUserAccount(savedAccount);

        return employeeRepository.save(employee);
    }
}
