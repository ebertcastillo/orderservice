package dev.ebecast.orderservice.model.entity;

public enum OrderStatus {
  PENDING,      // Orden creada, esperando validación
  CONFIRMED,    // Orden confirmada por inventory-service
  CANCELLED     // Orden cancelada (sin stock)
}
