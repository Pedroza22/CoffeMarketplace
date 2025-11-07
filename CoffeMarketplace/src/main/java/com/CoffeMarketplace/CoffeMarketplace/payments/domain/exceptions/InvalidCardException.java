package com.CoffeMarketplace.CoffeMarketplace.payments.domain.exceptions;

public class InvalidCardException extends RuntimeException {
    public InvalidCardException(String message) {
        super(message);
    }
}