package com.acme.cafe.orders.application;

import com.acme.cafe.orders.domain.Order;
import com.acme.cafe.orders.infrastructure.OrderRepository;
import com.acme.cafe.shared.common.DomainEventPublisher;
import com.acme.cafe.shared.contracts.events.OrderCreated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class OrderApplicationService {

    private final OrderRepository repository;
    private final DomainEventPublisher eventPublisher;

    public OrderApplicationService(OrderRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public UUID createOrder(UUID customerId, BigDecimal totalAmount) {
        UUID orderId = UUID.randomUUID();
        Order entity = Order.newOrder(orderId, customerId, totalAmount);
        repository.save(entity);

        eventPublisher.publish(new OrderCreated(orderId, customerId, totalAmount, Instant.now()));
        return orderId;
    }
}