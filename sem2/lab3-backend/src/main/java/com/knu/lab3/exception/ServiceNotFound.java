package com.knu.lab3.exception;

public class ServiceNotFound extends RuntimeException {
    public ServiceNotFound(String message) {
        super(message);
    }
}
