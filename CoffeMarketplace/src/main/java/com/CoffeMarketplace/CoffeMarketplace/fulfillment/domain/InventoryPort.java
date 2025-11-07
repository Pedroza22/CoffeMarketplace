package com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain;

import java.util.List;

public interface InventoryPort {
    /** Reserve stock for an order. Returns true if reservation succeeded. */
    boolean reserveStock(String orderId, List<OrderItem> items);

    /** Release previously reserved stock for an order. */
    void releaseStock(String orderId);
}
