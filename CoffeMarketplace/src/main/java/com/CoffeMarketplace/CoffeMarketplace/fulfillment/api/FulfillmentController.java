package com.CoffeMarketplace.CoffeMarketplace.fulfillment.api;

import com.CoffeMarketplace.CoffeMarketplace.fulfillment.application.FulfillmentService;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.OrderItem;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.ShipmentRequest;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.ShipmentResult;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.events.PaymentAuthorizedEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fulfillment")
public class FulfillmentController {

    private final FulfillmentService service;

    public FulfillmentController(FulfillmentService service) {
        this.service = service;
    }

    // Endpoint to receive PaymentAuthorized events (simulated)
    @PostMapping("/events/payment-authorized")
    public ResponseEntity<Void> paymentAuthorized(@RequestBody PaymentAuthorizedEvent evt) {
        service.handlePaymentAuthorized(evt.getOrderId(), evt.getItems(), evt.getAddress());
        return ResponseEntity.accepted().build();
    }

    // Manual shipment creation
    @PostMapping("/shipments")
    public ResponseEntity<ShipmentResult> createShipment(@RequestBody ShipmentRequest req) throws Exception {
        // For now return a manual placeholder result; real flow should call carrier adapter
        ShipmentResult result = new ShipmentResult(true, "MANUAL-TRK", "manual-created");
        return ResponseEntity.ok(result);
    }
}
