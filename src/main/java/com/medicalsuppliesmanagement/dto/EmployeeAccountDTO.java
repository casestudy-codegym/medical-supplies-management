package com.medicalsuppliesmanagement.dto;

import com.medicalsuppliesmanagement.entity.UserAccount;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAccountDTO {

    // UserAccount fields
    @NotBlank(message = "Tên tài khoản không được để trống")
    @Size(min = 4, max = 50, message = "Tên tài khoản phải từ 4-50 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Tên tài khoản chỉ được chứa chữ cái, số và dấu gạch dưới")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @NotBlank(message = "Tên nhân viên không được để trống")
    @Size(max = 100, message = "Tên nhân viên không được quá 100 ký tự")
    private String fullName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    private LocalDate dateOfBirth;

    private UserAccount.Gender gender;

    @Size(max = 255, message = "Địa chỉ không được quá 255 ký tự")
    private String address;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại phải từ 10-11 số")
    private String phone;

    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotNull(message = "Quyền hạn không được để trống")
    private UserAccount.Role role;

    // Employee fields
    @NotBlank(message = "Mã nhân viên không được để trống")
    @Pattern(regexp = "^[A-Z]{2}\\d{4}$", message = "Mã nhân viên phải theo định dạng XX0000")
    private String employeeCode;

    @Size(max = 50, message = "Chức vụ không được quá 50 ký tự")
    private String position;
}