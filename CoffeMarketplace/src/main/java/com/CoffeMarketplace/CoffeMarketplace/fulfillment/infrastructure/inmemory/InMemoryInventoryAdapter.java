package com.CoffeMarketplace.CoffeMarketplace.fulfillment.infrastructure.inmemory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.InventoryPort;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.OrderItem;

@Component
public class InMemoryInventoryAdapter implements InventoryPort {
    // Map<sku, available>
    private final Map<String, Integer> stock = new ConcurrentHashMap<>();
    // Map<orderId, Map<sku,qty>> for reservations
    private final Map<String, Map<String, Integer>> reservations = new ConcurrentHashMap<>();

    public InMemoryInventoryAdapter() {
        // seed some stock for testing
        stock.put("COF-001", 100);
        stock.put("COF-002", 50);
    }

    @Override
    public boolean reserveStock(String orderId, List<OrderItem> items) {
        synchronized (this) {
            // check availability
            for (OrderItem it : items) {
                int available = stock.getOrDefault(it.getSku(), 0);
                if (available < it.getQuantity()) return false;
            }
            // apply reservation
            for (OrderItem it : items) {
                stock.put(it.getSku(), stock.getOrDefault(it.getSku(), 0) - it.getQuantity());
            }
            // store reservation detail
            Map<String,Integer> map = new ConcurrentHashMap<>();
            for (OrderItem it : items) map.put(it.getSku(), it.getQuantity());
            reservations.put(orderId, map);
            return true;
        }
    }

    @Override
    public void releaseStock(String orderId) {
        synchronized (this) {
            Map<String,Integer> reserved = reservations.remove(orderId);
            if (reserved != null) {
                for (Map.Entry<String,Integer> e : reserved.entrySet()) {
                    stock.put(e.getKey(), stock.getOrDefault(e.getKey(), 0) + e.getValue());
                }
            }
        }
    }
}
