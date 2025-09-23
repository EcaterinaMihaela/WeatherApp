package com.example.weatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private String title;
    private String message;

    // getters și setters dacă vrei
    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}