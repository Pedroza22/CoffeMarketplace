package com.acme.cafe.orders.api;

import com.acme.cafe.orders.application.OrderApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Operaciones del bounded context de órdenes")
public class OrdersController {

    private final OrderApplicationService service;

    public OrdersController(OrderApplicationService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Crear una nueva orden (estado NEW) y emitir OrderCreated")
    public ResponseEntity<OrderResponse> create(@RequestBody @Validated CreateOrderRequest request) {
        UUID id = service.createOrder(request.customerId(), request.totalAmount());
        return ResponseEntity.ok(new OrderResponse(id));
    }
}