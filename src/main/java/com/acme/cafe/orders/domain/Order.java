package com.acme.cafe.orders.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public static Order newOrder(UUID id, UUID customerId, BigDecimal totalAmount) {
        Order o = new Order();
        o.id = id;
        o.customerId = customerId;
        o.totalAmount = totalAmount;
        o.status = OrderStatus.NEW;
        o.createdAt = Instant.now();
        return o;
    }

    public void markPaid() {
        if (this.status == OrderStatus.NEW) {
            this.status = OrderStatus.PAID;
        }
    }

    public void cancel() {
        if (this.status == OrderStatus.NEW || this.status == OrderStatus.PAID) {
            this.status = OrderStatus.CANCELLED;
        }
    }

    public void fulfill() {
        if (this.status == OrderStatus.PAID) {
            this.status = OrderStatus.FULFILLED;
        }
    }
}