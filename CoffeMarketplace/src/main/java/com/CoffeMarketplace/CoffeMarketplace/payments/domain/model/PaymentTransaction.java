package com.CoffeMarketplace.CoffeMarketplace.payments.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentTransaction {
    private UUID id;
    private UUID paymentId;
    private TransactionType type;
    private Money amount;
    private TransactionStatus status;
    private String gatewayResponse;
    private LocalDateTime createdAt;

    public PaymentTransaction(UUID paymentId, TransactionType type, Money amount, TransactionStatus status, String gatewayResponse) {
        this.id = UUID.randomUUID();
        this.paymentId = paymentId;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.gatewayResponse = gatewayResponse;
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public TransactionType getType() {
        return type;
    }

    public Money getAmount() {
        return amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public String getGatewayResponse() {
        return gatewayResponse;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}