package com.CoffeMarketplace.CoffeMarketplace.payments.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Refund {
    private UUID id;
    private UUID paymentId;
    private Money amount;
    private String reason;
    private RefundStatus status;
    private String gatewayRefundId;
    private LocalDateTime createdAt;

    public Refund(UUID paymentId, Money amount, String reason) {
        this.id = UUID.randomUUID();
        this.paymentId = paymentId;
        this.amount = amount;
        this.reason = reason;
        this.status = RefundStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public Money getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    public RefundStatus getStatus() {
        return status;
    }

    public String getGatewayRefundId() {
        return gatewayRefundId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Business methods
    public void complete(String gatewayRefundId) {
        this.status = RefundStatus.COMPLETED;
        this.gatewayRefundId = gatewayRefundId;
    }

    public void fail() {
        this.status = RefundStatus.FAILED;
    }
}