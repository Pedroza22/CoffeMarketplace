package com.CoffeMarketplace.CoffeMarketplace.fulfillment.infrastructure.carrier;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.CarrierPort;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.ShipmentRequest;
import com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain.ShipmentResult;

@Component
public class HttpCarrierAdapter implements CarrierPort {
    @Override
    public ShipmentResult createShipment(ShipmentRequest request) throws Exception {
        // placeholder: simulate calling external carrier
        // random success for demo
        String tracking = "TRK-" + UUID.randomUUID().toString().substring(0,8);
        return new ShipmentResult(true, tracking, "Created");
    }
}
