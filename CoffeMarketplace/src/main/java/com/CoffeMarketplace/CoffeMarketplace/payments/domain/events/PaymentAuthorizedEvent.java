package com.CoffeMarketplace.CoffeMarketplace.payments.domain.events;

import com.CoffeMarketplace.CoffeMarketplace.shared.contracts.EventDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentAuthorizedEvent extends EventDto {
    private UUID paymentId;
    private UUID orderId;
    private BigDecimal amount;
    private LocalDateTime authorizedAt;

    public PaymentAuthorizedEvent(UUID paymentId, UUID orderId, BigDecimal amount) {
        super("PaymentAuthorized", paymentId.toString(), "Payment");
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.authorizedAt = LocalDateTime.now();
    }

    // Getters
    public UUID getPaymentId() {
        return paymentId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getAuthorizedAt() {
        return authorizedAt;
    }
}