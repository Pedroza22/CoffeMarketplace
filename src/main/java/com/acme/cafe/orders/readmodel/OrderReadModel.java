package com.acme.cafe.orders.readmodel;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "order_read_model")
public class OrderReadModel {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String status;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public OrderReadModel() {}

    public OrderReadModel(UUID id, String status, UUID customerId, BigDecimal totalAmount) {
        this.id = id;
        this.status = status;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
    }

    public UUID getId() { return id; }
    public String getStatus() { return status; }
    public UUID getCustomerId() { return customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void update(String status) {
        this.status = status;
        this.updatedAt = Instant.now();
    }
}