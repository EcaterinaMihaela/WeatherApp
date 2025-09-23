package com.example.weatherapp.exception;

import com.example.weatherapp.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientIpException.class)
    public ResponseEntity<ErrorResponseDto> handleClientIpException(ClientIpException ex) {
        return new ResponseEntity<>(new ErrorResponseDto("IP Error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WeatherDataException.class)
    public ResponseEntity<ErrorResponseDto> handleWeatherDataException(WeatherDataException ex) {
        return new ResponseEntity<>(new ErrorResponseDto("Weather Error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
