package com.example.gp.gp_crud_backend.apiDTO;

public class ApiResponse {

    public String message;
    public Object data;

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
    
}
