package dev.ebecast.orderservice.service;

import dev.ebecast.orderservice.kafka.consumer.OrderEventConsumer;
import dev.ebecast.orderservice.kafka.event.OrderPlacedEvent;
import dev.ebecast.orderservice.kafka.producer.OrderEventProducer;
import dev.ebecast.orderservice.model.dto.OrderRequest;
import dev.ebecast.orderservice.model.dto.OrderResponse;
import dev.ebecast.orderservice.model.entity.Order;
import dev.ebecast.orderservice.model.entity.OrderStatus;
import dev.ebecast.orderservice.repository.OrderRepository;
import dev.ebecast.orderservice.mapper.OrderMapper;
import dev.ebecast.orderservice.exception.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);

    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;
    private final OrderMapper orderMapper;

    public OrderService(
            OrderRepository orderRepository,
            OrderEventProducer orderEventProducer,
            OrderMapper orderMapper
    ) {
        this.orderRepository = orderRepository;
        this.orderEventProducer = orderEventProducer;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderResponse create(OrderRequest request) {

        // 1. Convertir el Request → Entidad
        Order order = orderMapper.toEntity(request);

        // 2. Guardar en BD
        Order saved = orderRepository.save(order);

        // 3. Publicar evento
        OrderPlacedEvent event = orderMapper.toEvent(saved);
        orderEventProducer.publishOrderPlaced(event);

        // 4. Respuesta
        return orderMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        return orderMapper.toResponse(order);
    }

    @Transactional
    public void confirmOrder(Long orderId) {
        log.info("Confirming order: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        if (order.getStatus() != OrderStatus.PENDING) {
            log.warn("Order is not PENDING: id={}, status={}", orderId, order.getStatus());
            return;
        }

        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        log.info("Order confirmed: {}", orderId);
    }

    @Transactional
    public void cancelOrder(Long orderId, String reason) {
        log.info("Cancelling order: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        if (order.getStatus() != OrderStatus.PENDING) {
            log.warn("Order is not PENDING: id={}, status={}", orderId, order.getStatus());
            return;
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setCancellationReason(reason);
        orderRepository.save(order);

        log.info("Order cancelled: {}", orderId);
    }
}