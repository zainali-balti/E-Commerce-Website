package com.example.ecom.repository;

import com.example.ecom.dto.OrderDto;
import com.example.ecom.entity.Order;
import com.example.ecom.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatusList);
    List<Order> findByUserIdAndOrderStatusIn(Long userId, List<OrderStatus> orderStatuses);
    Optional<Order> findByTrackingId(UUID trackingId);

}
