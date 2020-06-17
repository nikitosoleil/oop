package com.knu.lab3.exception;

public class BillNotFound extends RuntimeException {
    public BillNotFound(String message) {
        super(message);
    }
}
