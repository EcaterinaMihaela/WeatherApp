package com.example.weatherapp.exception;

public class WeatherDataException extends RuntimeException {
    public WeatherDataException(String message) {
        super(message);
    }
}
