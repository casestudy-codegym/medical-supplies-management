package com.medicalsuppliesmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
    
    private Map<String, String> errors;
    
    public ValidationException(String message) {
        super(message);
        this.errors = new HashMap<>();
    }
    
    public ValidationException(BindingResult bindingResult) {
        super("Lỗi validation dữ liệu nhập vào");
        this.errors = new HashMap<>();
        
        bindingResult.getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
    }
    
    public Map<String, String> getErrors() {
        return errors;
    }
    
    public void addError(String field, String message) {
        errors.put(field, message);
    }
} 