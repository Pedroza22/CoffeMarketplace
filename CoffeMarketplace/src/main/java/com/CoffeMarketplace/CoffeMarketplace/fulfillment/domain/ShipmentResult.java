package com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain;

public class ShipmentResult {
    private boolean success;
    private String trackingId;
    private String message;

    public ShipmentResult() {}

    public ShipmentResult(boolean success, String trackingId, String message) {
        this.success = success;
        this.trackingId = trackingId;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getTrackingId() { return trackingId; }
    public void setTrackingId(String trackingId) { this.trackingId = trackingId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
