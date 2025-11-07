package com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.events;

public class StockReservedEvent {
    private String orderId;

    public StockReservedEvent() {}
    public StockReservedEvent(String orderId) { this.orderId = orderId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    @Override
    public String toString() { return "StockReserved{" + "orderId='" + orderId + '\'' + '}'; }
}
