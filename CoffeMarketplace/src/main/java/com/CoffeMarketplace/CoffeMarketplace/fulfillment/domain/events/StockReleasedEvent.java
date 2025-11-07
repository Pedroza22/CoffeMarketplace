package com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.events;

public class StockReleasedEvent {
    private String orderId;

    public StockReleasedEvent() {}
    public StockReleasedEvent(String orderId) { this.orderId = orderId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    @Override
    public String toString() { return "StockReleased{" + "orderId='" + orderId + '\'' + '}'; }
}
