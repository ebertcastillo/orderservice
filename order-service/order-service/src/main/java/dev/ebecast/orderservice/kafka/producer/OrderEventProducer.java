package dev.ebecast.orderservice.kafka.producer;

import dev.ebecast.orderservice.kafka.event.OrderPlacedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventProducer {

  private static final Logger log = LoggerFactory.getLogger(OrderEventProducer.class);
  private static final String TOPIC = "ecommerce.orders.placed";

  private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

  public OrderEventProducer(KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void publishOrderPlaced(OrderPlacedEvent event) {
    String key = event.getOrderId().toString();

    log.info("Publishing OrderPlacedEvent: orderId={}, productId={}, quantity={}",
        event.getOrderId(), event.getProductId(), event.getQuantity());

    kafkaTemplate.send(TOPIC, key, event)
        .whenComplete((result, ex) -> {
          if (ex != null) {
            log.error("Failed to publish OrderPlacedEvent: orderId={}",
                event.getOrderId(), ex);
          } else {
            log.info("OrderPlacedEvent published successfully: orderId={}, partition={}",
                event.getOrderId(),
                result.getRecordMetadata().partition());
          }
        });
  }
}
