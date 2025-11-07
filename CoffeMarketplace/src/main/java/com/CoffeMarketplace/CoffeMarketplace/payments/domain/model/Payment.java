package com.CoffeMarketplace.CoffeMarketplace.payments.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Payment {
    private UUID id;
    private UUID orderId;
    private Money amount;
    private Currency currency;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private String gatewayId;
    private String gatewayTransactionId;
    private String idempotencyKey;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PaymentTransaction> transactions;
    private List<Refund> refunds;

    // Constructor for new payment
    public Payment(UUID orderId, Money amount, PaymentMethod paymentMethod, String idempotencyKey) {
        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.amount = amount;
        this.currency = amount.getCurrency();
        this.status = PaymentStatus.PENDING;
        this.paymentMethod = paymentMethod;
        this.idempotencyKey = idempotencyKey;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.transactions = new ArrayList<>();
        this.refunds = new ArrayList<>();
    }

    // Constructor for existing payment
    public Payment(UUID id, UUID orderId, Money amount, Currency currency, PaymentStatus status,
                   PaymentMethod paymentMethod, String gatewayId, String gatewayTransactionId,
                   String idempotencyKey, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.gatewayId = gatewayId;
        this.gatewayTransactionId = gatewayTransactionId;
        this.idempotencyKey = idempotencyKey;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.transactions = new ArrayList<>();
        this.refunds = new ArrayList<>();
    }

    // Business methods
    public void authorize(String gatewayId, String gatewayTransactionId) {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("Payment can only be authorized from PENDING status");
        }
        this.status = PaymentStatus.AUTHORIZED;
        this.gatewayId = gatewayId;
        this.gatewayTransactionId = gatewayTransactionId;
        this.updatedAt = LocalDateTime.now();
    }

    public void capture() {
        if (this.status != PaymentStatus.AUTHORIZED) {
            throw new IllegalStateException("Payment can only be captured from AUTHORIZED status");
        }
        this.status = PaymentStatus.CAPTURED;
        this.updatedAt = LocalDateTime.now();
    }

    public void fail() {
        if (this.status == PaymentStatus.CAPTURED) {
            throw new IllegalStateException("Cannot fail a captured payment");
        }
        this.status = PaymentStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }

    public Refund createRefund(Money refundAmount, String reason) {
        if (this.status != PaymentStatus.CAPTURED) {
            throw new IllegalStateException("Can only refund captured payments");
        }

        Money totalRefunded = this.refunds.stream()
                .map(Refund::getAmount)
                .reduce(Money.of(java.math.BigDecimal.ZERO, this.currency),
                       Money::add);

        Money newTotalRefunded = totalRefunded.add(refundAmount);
        if (newTotalRefunded.isGreaterThan(this.amount)) {
            throw new IllegalArgumentException("Total refunds cannot exceed payment amount");
        }

        Refund refund = new Refund(this.id, refundAmount, reason);
        this.refunds.add(refund);

        if (newTotalRefunded.equals(this.amount)) {
            this.status = PaymentStatus.REFUNDED;
        }

        this.updatedAt = LocalDateTime.now();
        return refund;
    }

    public void addTransaction(PaymentTransaction transaction) {
        this.transactions.add(transaction);
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Money getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public String getGatewayTransactionId() {
        return gatewayTransactionId;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<PaymentTransaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public List<Refund> getRefunds() {
        return new ArrayList<>(refunds);
    }

    // Setters for infrastructure layer
    public void setTransactions(List<PaymentTransaction> transactions) {
        this.transactions = transactions;
    }

    public void setRefunds(List<Refund> refunds) {
        this.refunds = refunds;
    }
}