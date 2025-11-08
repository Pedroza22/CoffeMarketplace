package com.acme.cafe.orders.application;

import com.acme.cafe.orders.domain.Order;
import com.acme.cafe.orders.domain.OrderStatus;
import com.acme.cafe.orders.infrastructure.OrderRepository;
import com.acme.cafe.orders.infrastructure.OrderEvent;
import com.acme.cafe.orders.infrastructure.OrderEventRepository;
import com.acme.cafe.orders.readmodel.OrderReadModel;
import com.acme.cafe.orders.readmodel.OrderReadModelRepository;
import com.acme.cafe.shared.common.DomainEventPublisher;
import com.acme.cafe.shared.contracts.events.OrderCreated;
import com.acme.cafe.shared.outbox.OutboxEvent;
import com.acme.cafe.shared.outbox.OutboxEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class OrderApplicationService {

    private final OrderRepository repository;
    private final DomainEventPublisher eventPublisher;
    private final OrderEventRepository eventRepository;
    private final OrderReadModelRepository readModelRepository;
    private final OutboxEventRepository outboxRepository;

    public OrderApplicationService(OrderRepository repository,
                                   DomainEventPublisher eventPublisher,
                                   OrderEventRepository eventRepository,
                                   OrderReadModelRepository readModelRepository,
                                   OutboxEventRepository outboxRepository) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.eventRepository = eventRepository;
        this.readModelRepository = readModelRepository;
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    public UUID createOrder(UUID customerId, BigDecimal totalAmount) {
        UUID orderId = UUID.randomUUID();
        Order entity = Order.newOrder(orderId, customerId, totalAmount);
        repository.save(entity);

        eventPublisher.publish(new OrderCreated(orderId, customerId, totalAmount, Instant.now()));

        // Persist immutable event
        eventRepository.save(new OrderEvent(orderId, "OrderCreated",
                "{\"orderId\":\"" + orderId + "\",\"customerId\":\"" + customerId + "\"}"));

        // Update read model
        readModelRepository.save(new OrderReadModel(orderId, OrderStatus.NEW.name(), customerId, totalAmount));

        // Outbox for integration
        outboxRepository.save(new OutboxEvent("Order", orderId, "OrderCreated",
                "{\"orderId\":\"" + orderId + "\"}"));
        return orderId;
    }

    @Transactional
    public void markPaid(UUID orderId) {
        Order order = repository.findById(orderId).orElseThrow();
        order.markPaid();
        repository.save(order);
        eventRepository.save(new OrderEvent(orderId, "OrderPaid", "{}"));
        readModelRepository.findById(orderId).ifPresent(rm -> { rm.update(OrderStatus.PAID.name()); readModelRepository.save(rm); });
        outboxRepository.save(new OutboxEvent("Order", orderId, "OrderPaid", "{}"));
    }

    @Transactional
    public void cancel(UUID orderId) {
        Order order = repository.findById(orderId).orElseThrow();
        order.cancel();
        repository.save(order);
        eventRepository.save(new OrderEvent(orderId, "OrderCancelled", "{}"));
        readModelRepository.findById(orderId).ifPresent(rm -> { rm.update(OrderStatus.CANCELLED.name()); readModelRepository.save(rm); });
        outboxRepository.save(new OutboxEvent("Order", orderId, "OrderCancelled", "{}"));
    }

    @Transactional
    public void fulfill(UUID orderId) {
        Order order = repository.findById(orderId).orElseThrow();
        order.fulfill();
        repository.save(order);
        eventRepository.save(new OrderEvent(orderId, "OrderFulfilled", "{}"));
        readModelRepository.findById(orderId).ifPresent(rm -> { rm.update(OrderStatus.FULFILLED.name()); readModelRepository.save(rm); });
        outboxRepository.save(new OutboxEvent("Order", orderId, "OrderFulfilled", "{}"));
    }

    @Transactional(readOnly = true)
    public List<OrderEvent> getTimeline(UUID orderId) {
        return eventRepository.findByOrderIdOrderByCreatedAtAsc(orderId);
    }

    @Transactional(readOnly = true)
    public OrderReadModel getReadModel(UUID orderId) {
        return readModelRepository.findById(orderId).orElseThrow();
    }
}