package com.CoffeMarketplace.CoffeMarketplace.fulfillment.domain;

public interface CarrierPort {
    ShipmentResult createShipment(ShipmentRequest request) throws Exception;
}
