package com.knu.lab3.exception;

public class UserIsBlocked extends RuntimeException {
    public UserIsBlocked(String message) {
        super(message);
    }
}
