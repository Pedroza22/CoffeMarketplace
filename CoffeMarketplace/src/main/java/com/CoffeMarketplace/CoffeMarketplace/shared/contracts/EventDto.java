package com.CoffeMarketplace.CoffeMarketplace.shared.contracts;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class EventDto {
    private UUID eventId;
    private String eventType;
    private LocalDateTime timestamp;
    private String aggregateId;
    private String aggregateType;

    // Constructor
    public EventDto(String eventType, String aggregateId, String aggregateType) {
        this.eventId = UUID.randomUUID();
        this.eventType = eventType;
        this.timestamp = LocalDateTime.now();
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
    }

    // Getters and Setters
    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }
}