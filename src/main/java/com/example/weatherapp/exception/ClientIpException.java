package com.example.weatherapp.exception;

public class ClientIpException extends RuntimeException {
    public ClientIpException(String message) {
        super(message);
    }
}
