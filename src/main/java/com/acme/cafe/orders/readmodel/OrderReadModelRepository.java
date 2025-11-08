package com.acme.cafe.orders.readmodel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderReadModelRepository extends JpaRepository<OrderReadModel, UUID> {}