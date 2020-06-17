package com.knu.lab3.exception;

public class BillAlreadyPaid extends RuntimeException {
    public BillAlreadyPaid(String message) {
        super(message);
    }
}
