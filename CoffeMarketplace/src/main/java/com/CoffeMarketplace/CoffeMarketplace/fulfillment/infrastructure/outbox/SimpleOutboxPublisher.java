package com.CoffeMarketplace.CoffeMarketplace.fulfillment.infrastructure.outbox;

import org.springframework.stereotype.Component;

@Component
public class SimpleOutboxPublisher implements OutboxPublisher {
    @Override
    public void publish(Object event) {
        // Simple placeholder: print to stdout. Replace with real outbox/Kafka later.
        System.out.println("[OUTBOX PUBLISH] " + event.getClass().getSimpleName() + " -> " + event.toString());
    }
}
