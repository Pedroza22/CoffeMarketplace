package com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.events;

import java.util.List;

import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.OrderItem;

public class PaymentAuthorizedEvent {
    private String orderId;
    private List<OrderItem> items;
    private String address;

    public PaymentAuthorizedEvent() {}

    public PaymentAuthorizedEvent(String orderId, List<OrderItem> items, String address) {
        this.orderId = orderId;
        this.items = items;
        this.address = address;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
