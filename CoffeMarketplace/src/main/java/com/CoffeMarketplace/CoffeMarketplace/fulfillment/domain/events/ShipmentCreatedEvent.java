package com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.events;

public class ShipmentCreatedEvent {
    private String orderId;
    private String trackingId;

    public ShipmentCreatedEvent() {}
    public ShipmentCreatedEvent(String orderId, String trackingId) {
        this.orderId = orderId;
        this.trackingId = trackingId;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getTrackingId() { return trackingId; }
    public void setTrackingId(String trackingId) { this.trackingId = trackingId; }

    @Override
    public String toString() { return "ShipmentCreated{" + "orderId='" + orderId + '\'' + ", trackingId='" + trackingId + '\'' + '}'; }
}
