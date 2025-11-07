package com.CoffeMarketplace.CoffeMarketplace.fulfillment.infrastructure.outbox;

public interface OutboxPublisher {
    void publish(Object event);
}
