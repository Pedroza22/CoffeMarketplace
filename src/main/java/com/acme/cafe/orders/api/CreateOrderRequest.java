package com.acme.cafe.orders.api;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderRequest(
        @NotNull UUID customerId,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal totalAmount
) {}