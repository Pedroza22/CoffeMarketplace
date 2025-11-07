package com.CoffeMarketplace.CoffeMarketplace.fulfillment.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.CarrierPort;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.InventoryPort;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.OrderItem;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.ShipmentRequest;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.ShipmentResult;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.events.ShipmentCreatedEvent;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.events.ShipmentFailedEvent;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.events.StockReleasedEvent;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.events.StockReservedEvent;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.infrastructure.outbox.OutboxPublisher;

@Service
public class FulfillmentService {

    private final InventoryPort inventory;
    private final CarrierPort carrier;
    private final OutboxPublisher outbox;

    public FulfillmentService(InventoryPort inventory, CarrierPort carrier, OutboxPublisher outbox) {
        this.inventory = inventory;
        this.carrier = carrier;
        this.outbox = outbox;
    }

    /** Handle payment authorized event: reserve stock and (optionally) create shipment. */
    public void handlePaymentAuthorized(String orderId, List<OrderItem> items, String address) {
        boolean reserved = inventory.reserveStock(orderId, items);
        if (reserved) {
            outbox.publish(new StockReservedEvent(orderId));
            // try to create shipment (sync for now)
            try {
                ShipmentResult r = carrier.createShipment(new ShipmentRequest(orderId, address));
                if (r.isSuccess()) {
                    outbox.publish(new ShipmentCreatedEvent(orderId, r.getTrackingId()));
                } else {
                    outbox.publish(new ShipmentFailedEvent(orderId, r.getMessage()));
                }
            } catch (Exception e) {
                outbox.publish(new ShipmentFailedEvent(orderId, e.getMessage()));
            }
        } else {
            outbox.publish(new StockReleasedEvent(orderId));
        }
    }

    public void releaseStock(String orderId) {
        inventory.releaseStock(orderId);
        outbox.publish(new StockReleasedEvent(orderId));
    }
}
