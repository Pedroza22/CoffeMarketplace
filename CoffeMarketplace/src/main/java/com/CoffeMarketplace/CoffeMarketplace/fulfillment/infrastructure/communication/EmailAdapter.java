package com.CoffeMarketplace.CoffeMarketplace.fulfillment.infrastructure.communication;

import org.springframework.stereotype.Component;

@Component
public class EmailAdapter {
    public void sendTransactional(String to, String subject, String body) {
        System.out.println("[EMAIL] to=" + to + " subject=" + subject + " body=" + body);
    }
}
