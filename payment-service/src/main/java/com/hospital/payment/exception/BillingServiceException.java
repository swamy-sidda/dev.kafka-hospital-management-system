package com.hospital.payment.exception;

public class BillingServiceException extends RuntimeException {

    public BillingServiceException(String message) {
        super(message);
    }
}