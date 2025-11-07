package com.acme.cafe.shared.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingEventPublisher implements DomainEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(LoggingEventPublisher.class);

    @Override
    public void publish(Object event) {
        // Placeholder for future Outbox/Kafka. For now, we log the event.
        log.info("DomainEvent published: {}", event);
    }
}