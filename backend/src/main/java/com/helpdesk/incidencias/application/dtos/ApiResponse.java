package com.helpdesk.incidencias.application.dtos;

import java.time.LocalDateTime;

/**
 * DTO genérico para respuestas de la API
 */
public class ApiResponse<T> {
    
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private String error;
    
    // Constructor por defecto
    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }
    
    // Constructor de éxito
    public ApiResponse(boolean success, String message, T data) {
        this();
        this.success = success;
        this.message = message;
        this.data = data;
    }
    
    // Constructor de error
    public ApiResponse(boolean success, String message, String error) {
        this();
        this.success = success;
        this.message = message;
        this.error = error;
    }
    
    // Métodos estáticos para crear respuestas
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }
    
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message, null);
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, message);
    }
    
    public static <T> ApiResponse<T> error(String message, String error) {
        return new ApiResponse<>(false, message, error);
    }
    
    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
} 