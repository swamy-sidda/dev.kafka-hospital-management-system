package com.hospital.billing.exception;

public class BillingNotFoundException extends RuntimeException {

    public BillingNotFoundException(String message) {
        super(message);
    }
}