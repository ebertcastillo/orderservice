package dev.ebecast.orderservice.kafka.event;

import java.time.Instant;

/**
 * Evento recibido de inventory-service cuando una orden fue confirmada (había stock disponible y
 * fue reservado exitosamente). IMPORTANTE: Esta es una COPIA del evento de inventory-service.
 */
public class OrderConfirmedEvent {

  private Long orderId;
  private Long productId;
  private Integer quantity;
  private Integer availableStockAfterReservation;
  private Integer reservedStockAfterReservation;
  private Instant timestamp;

  // Constructor vacío (REQUERIDO para deserialización)
  public OrderConfirmedEvent() {
  }

  // Constructor completo
  public OrderConfirmedEvent(Long orderId, Long productId, Integer quantity,
      Integer availableStockAfterReservation,
      Integer reservedStockAfterReservation) {
    this.orderId = orderId;
    this.productId = productId;
    this.quantity = quantity;
    this.availableStockAfterReservation = availableStockAfterReservation;
    this.reservedStockAfterReservation = reservedStockAfterReservation;
    this.timestamp = Instant.now();
  }

  // Getters y Setters

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Integer getAvailableStockAfterReservation() {
    return availableStockAfterReservation;
  }

  public void setAvailableStockAfterReservation(Integer availableStockAfterReservation) {
    this.availableStockAfterReservation = availableStockAfterReservation;
  }

  public Integer getReservedStockAfterReservation() {
    return reservedStockAfterReservation;
  }

  public void setReservedStockAfterReservation(Integer reservedStockAfterReservation) {
    this.reservedStockAfterReservation = reservedStockAfterReservation;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "OrderConfirmedEvent{" +
        "orderId=" + orderId +
        ", productId=" + productId +
        ", quantity=" + quantity +
        ", availableStockAfterReservation=" + availableStockAfterReservation +
        ", reservedStockAfterReservation=" + reservedStockAfterReservation +
        ", timestamp=" + timestamp +
        '}';
  }
}
