package com.hospital.billing.exception;

public class BillingValidationException extends RuntimeException {

    public BillingValidationException(String message) {
        super(message);
    }
}