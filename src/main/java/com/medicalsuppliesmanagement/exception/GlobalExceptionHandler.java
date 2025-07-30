package com.medicalsuppliesmanagement.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Xử lý ngoại lệ chung
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e, Model model, HttpServletRequest request) {
        model.addAttribute("error", "Lỗi hệ thống: " + e.getMessage());
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("timestamp", formatTimestamp(LocalDateTime.now()));
        return "error/500";
    }

    // Xử lý ngoại lệ 404 - Không tìm thấy trang
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NoHandlerFoundException e, Model model, HttpServletRequest request) {
        model.addAttribute("error", "Không tìm thấy trang yêu cầu");
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("timestamp", formatTimestamp(LocalDateTime.now()));
        return "error/404";
    }

    // Xử lý ngoại lệ 403 - Không có quyền truy cập
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(AccessDeniedException e, Model model, HttpServletRequest request) {
        model.addAttribute("error", "Bạn không có quyền truy cập chức năng này");
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("timestamp", formatTimestamp(LocalDateTime.now()));
        return "error/403";
    }

    // Xử lý ngoại lệ đăng nhập
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("loginError", true);
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("path", request.getRequestURI());
        modelAndView.addObject("timestamp", formatTimestamp(LocalDateTime.now()));
        modelAndView.setViewName("auth/login");
        return modelAndView;
    }

    // Xử lý ngoại lệ dữ liệu không tồn tại
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException(ResourceNotFoundException e, Model model, HttpServletRequest request) {
        model.addAttribute("error", e.getMessage());
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("timestamp", formatTimestamp(LocalDateTime.now()));
        return "error/404";
    }

    // Xử lý ngoại lệ yêu cầu không hợp lệ
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestException(BadRequestException e, Model model, HttpServletRequest request) {
        model.addAttribute("error", e.getMessage());
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("timestamp", formatTimestamp(LocalDateTime.now()));
        return "error/400";
    }

    // Xử lý lỗi validation từ @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationExceptions(MethodArgumentNotValidException e, Model model, HttpServletRequest request) {
        ValidationException validationException = new ValidationException(e.getBindingResult());
        model.addAttribute("error", "Dữ liệu không hợp lệ");
        model.addAttribute("errors", validationException.getErrors());
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("timestamp", formatTimestamp(LocalDateTime.now()));
        return "error/400";
    }

    // Xử lý lỗi validation tùy chỉnh
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(ValidationException e, Model model, HttpServletRequest request) {
        model.addAttribute("error", e.getMessage());
        model.addAttribute("errors", e.getErrors());
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("timestamp", formatTimestamp(LocalDateTime.now()));
        return "error/400";
    }
    
    private String formatTimestamp(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }
} 