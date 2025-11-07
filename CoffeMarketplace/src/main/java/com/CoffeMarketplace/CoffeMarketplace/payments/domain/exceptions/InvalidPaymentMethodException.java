package com.CoffeMarketplace.CoffeMarketplace.payments.domain.exceptions;

public class InvalidPaymentMethodException extends RuntimeException {
    public InvalidPaymentMethodException(String message) {
        super(message);
    }
}