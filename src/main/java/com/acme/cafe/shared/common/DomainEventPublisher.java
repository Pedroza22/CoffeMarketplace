package com.acme.cafe.shared.common;

public interface DomainEventPublisher {
    void publish(Object event);
}