package com.CoffeMarketplace.CoffeMarketplace.fulfillment.infrastructure.communication;

import org.springframework.stereotype.Component;

@Component
public class WebSocketAdapter {
    public void sendToDashboard(String channel, Object payload) {
        System.out.println("[WS] channel=" + channel + " payload=" + payload);
    }
}
