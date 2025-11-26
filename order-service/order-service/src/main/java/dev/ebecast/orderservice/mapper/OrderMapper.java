package dev.ebecast.orderservice.mapper;

import dev.ebecast.orderservice.kafka.event.OrderPlacedEvent;
import dev.ebecast.orderservice.model.dto.OrderRequest;
import dev.ebecast.orderservice.model.dto.OrderResponse;
import dev.ebecast.orderservice.model.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequest request) {
        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setCustomerName(request.getCustomerName());
        order.setCustomerEmail(request.getCustomerEmail());
        order.setTotalAmount(request.getTotalAmount());
        return order;
    }

    public OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setProductId(order.getProductId());
        response.setQuantity(order.getQuantity());
        response.setCustomerName(order.getCustomerName());
        response.setCustomerEmail(order.getCustomerEmail());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }

    public OrderPlacedEvent toEvent(Order order) {
        return new OrderPlacedEvent(
                order.getId(),
                order.getProductId(),
                order.getQuantity(),
                order.getCustomerName(),
                order.getCustomerEmail(),
                order.getTotalAmount()
        );
    }
}