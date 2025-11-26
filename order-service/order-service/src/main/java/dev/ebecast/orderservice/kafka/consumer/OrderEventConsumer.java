package dev.ebecast.orderservice.kafka.consumer;

import dev.ebecast.orderservice.kafka.event.OrderCancelledEvent;
import dev.ebecast.orderservice.kafka.event.OrderConfirmedEvent;
import dev.ebecast.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumer de eventos OrderConfirmedEvent y OrderCancelledEvent. Escucha eventos de
 * inventory-service y actualiza el estado de las órdenes.
 */
@Component
public class OrderEventConsumer {

  private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);

  private final OrderService orderService;

  // Constructor injection
  public OrderEventConsumer(OrderService orderService) {
    this.orderService = orderService;
  }

  /**
   * Consume OrderConfirmedEvent del topic ecommerce.orders.confirmed y actualiza la orden a estado
   * CONFIRMED
   */
  @KafkaListener(
      topics = "ecommerce.orders.confirmed",
      groupId = "order-service"
  )
  public void consumeOrderConfirmed(OrderConfirmedEvent event) {
    log.info("Received OrderConfirmedEvent: {}", event);

    try {
      // Delegar lógica de negocio al Service
      orderService.confirmOrder(event.getOrderId());

      log.info("Order confirmed successfully: orderId={}", event.getOrderId());
    } catch (Exception e) {
      log.error("Error confirming order: orderId={}, error={}",
          event.getOrderId(), e.getMessage(), e);
    }
  }

  /**
   * Consume OrderCancelledEvent del topic ecommerce.orders.cancelled y actualiza la orden a estado
   * CANCELLED
   */
  @KafkaListener(
      topics = "ecommerce.orders.cancelled",
      groupId = "order-service"
  )
  public void consumeOrderCancelled(OrderCancelledEvent event) {
    log.info("Received OrderCancelledEvent: {}", event);

    try {
      // Delegar lógica de negocio al Service
      orderService.cancelOrder(event.getOrderId(), event.getReason());

      log.info("Order cancelled successfully: orderId={}, reason={}",
          event.getOrderId(), event.getReason());
    } catch (Exception e) {
      log.error("Error cancelling order: orderId={}, error={}",
          event.getOrderId(), e.getMessage(), e);
    }
  }
}
