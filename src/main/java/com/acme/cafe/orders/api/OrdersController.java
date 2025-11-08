package com.acme.cafe.orders.api;

import com.acme.cafe.orders.application.OrderApplicationService;
import com.acme.cafe.orders.infrastructure.OrderEvent;
import com.acme.cafe.orders.readmodel.OrderReadModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

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

    @PostMapping("/{id}/paid")
    @Operation(summary = "Marcar orden como PAID (simula PaymentAuthorized)")
    public ResponseEntity<Void> markPaid(@PathVariable UUID id) {
        service.markPaid(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancelar orden y emitir OrderCancelled")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        service.cancel(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/fulfill")
    @Operation(summary = "Finalizar orden (FULFILLED) y emitir OrderFulfilled")
    public ResponseEntity<Void> fulfill(@PathVariable UUID id) {
        service.fulfill(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar estado actual desde Read Model")
    public ResponseEntity<OrderReadModel> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getReadModel(id));
    }

    @GetMapping("/{id}/timeline")
    @Operation(summary = "Ver timeline de eventos del Order (Event Store)")
    public ResponseEntity<List<OrderEvent>> timeline(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getTimeline(id));
    }

    public record OrderResponse(UUID id) {}
}