package dev.ebecast.orderservice.controller;

import dev.ebecast.orderservice.model.dto.OrderRequest;
import dev.ebecast.orderservice.model.dto.OrderResponse;
import dev.ebecast.orderservice.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
    OrderResponse response = orderService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  public ResponseEntity<List<OrderResponse>> findAll() {
    List<OrderResponse> orders = orderService.findAll();
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
    OrderResponse order = orderService.findById(id);
    return ResponseEntity.ok(order);
  }
}
