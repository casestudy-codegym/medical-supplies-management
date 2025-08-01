package com.medicalsuppliesmanagement.util;

import com.medicalsuppliesmanagement.dto.EmployeeAccountDTO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class EmailService {
    private static final String SENDER_EMAIL = "projectcodegymteam2@gmail.com";
    private static final String SENDER_PASSWORD = "yhxk mpjy jnle wjwi";

    public static void sendEmail(String recipientEmail, String otp) {
        String subject = "Here is your OTP Code";
        String body = "Your OTP code is " + otp;

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    public static void sendEmployeeAccountInfo(EmployeeAccountDTO employeeDTO) {
        if (employeeDTO.getEmail() == null || employeeDTO.getEmail().isEmpty()) {
            System.out.println("Không thể gửi email: địa chỉ email nhân viên không có sẵn");
            return;
        }
        
        String subject = "Thông tin tài khoản nhân viên mới";
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("Kính gửi ").append(employeeDTO.getFullName()).append(",\n\n");
        bodyBuilder.append("Chúc mừng bạn! Tài khoản của bạn đã được tạo thành công trong hệ thống quản lý vật tư y tế của chúng tôi. Dưới đây là thông tin chi tiết về tài khoản của bạn:\n\n");
        bodyBuilder.append("THÔNG TIN TÀI KHOẢN:\n");
        bodyBuilder.append("- Tên đăng nhập: ").append(employeeDTO.getUsername()).append("\n");
        bodyBuilder.append("- Mật khẩu: ").append(employeeDTO.getPassword()).append("\n");
        bodyBuilder.append("- Quyền hạn: ").append(getVietnameseRole(employeeDTO.getRole())).append("\n");
        bodyBuilder.append("\nTHÔNG TIN NHÂN VIÊN:\n");
        bodyBuilder.append("- Mã nhân viên: ").append(employeeDTO.getEmployeeCode()).append("\n");
        bodyBuilder.append("- Họ và tên: ").append(employeeDTO.getFullName()).append("\n");
        
        if (employeeDTO.getDateOfBirth() != null) {
            bodyBuilder.append("- Ngày sinh: ").append(employeeDTO.getDateOfBirth().format(dateFormatter)).append("\n");
        }
        
        if (employeeDTO.getGender() != null) {
            bodyBuilder.append("- Giới tính: ").append(employeeDTO.getGender().toString().equals("NAM") ? "Nam" : "Nữ").append("\n");
        }
        
        if (employeeDTO.getPosition() != null && !employeeDTO.getPosition().isEmpty()) {
            bodyBuilder.append("- Chức vụ: ").append(employeeDTO.getPosition()).append("\n");
        }
        
        if (employeeDTO.getAddress() != null && !employeeDTO.getAddress().isEmpty()) {
            bodyBuilder.append("- Địa chỉ: ").append(employeeDTO.getAddress()).append("\n");
        }
        
        if (employeeDTO.getPhone() != null && !employeeDTO.getPhone().isEmpty()) {
            bodyBuilder.append("- Số điện thoại: ").append(employeeDTO.getPhone()).append("\n");
        }
        
        bodyBuilder.append("- Email: ").append(employeeDTO.getEmail()).append("\n\n");
        
        bodyBuilder.append("Vui lòng đổi mật khẩu của bạn sau khi đăng nhập lần đầu để đảm bảo tính bảo mật.\n\n");
        bodyBuilder.append("Nếu bạn có bất kỳ thắc mắc nào, vui lòng liên hệ với quản trị viên hệ thống.\n\n");
        bodyBuilder.append("Trân trọng,\n");
        bodyBuilder.append("Ban quản trị hệ thống quản lý vật tư y tế");
        
        sendEmailWithContent(employeeDTO.getEmail(), subject, bodyBuilder.toString());
    }
    
    private static String getVietnameseRole(Object role) {
        if (role == null) return "";
        
        String roleStr = role.toString();
        switch (roleStr) {
            case "ADMIN":
                return "Quản trị viên";
            case "ACCOUNTANT":
                return "Kế toán";
            case "SALES":
                return "Bán hàng";
            default:
                return roleStr;
        }
    }
    
    private static void sendEmailWithContent(String recipientEmail, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email thông tin nhân viên đã được gửi thành công đến: " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
