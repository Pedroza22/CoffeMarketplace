package com.CoffeMarketplace.CoffeMarketplace.payments.domain.exceptions;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message) {
        super(message);
    }
}