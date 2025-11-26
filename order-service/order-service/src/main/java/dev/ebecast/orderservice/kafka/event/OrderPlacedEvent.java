package dev.ebecast.orderservice.kafka.event;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderPlacedEvent {

  private Long orderId;
  private Long productId;
  private Integer quantity;
  private String customerName;
  private String customerEmail;
  private BigDecimal totalAmount;
  private Instant timestamp;

  public OrderPlacedEvent() {
    // Constructor vacío para deserialización JSON
  }

  public OrderPlacedEvent(Long orderId, Long productId, Integer quantity,
      String customerName, String customerEmail,
      BigDecimal totalAmount) {
    this.orderId = orderId;
    this.productId = productId;
    this.quantity = quantity;
    this.customerName = customerName;
    this.customerEmail = customerEmail;
    this.totalAmount = totalAmount;
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

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "OrderPlacedEvent{" +
        "orderId=" + orderId +
        ", productId=" + productId +
        ", quantity=" + quantity +
        ", customerName='" + customerName + '\'' +
        ", totalAmount=" + totalAmount +
        ", timestamp=" + timestamp +
        '}';
  }
}
