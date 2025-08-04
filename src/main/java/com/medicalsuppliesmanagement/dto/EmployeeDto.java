package com.medicalsuppliesmanagement.dto;

import com.medicalsuppliesmanagement.entity.UserAccount.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
    private String username;
    private String fullName;
    private String avatarUrl;
    private Gender gender;
    private String genderDisplay;
    private String phone;
    private String email;
    private String address;
    private String dateOfBirth;
    private String employeeCode;
    private String position;
    private Double latestSalary;
}
