package com.example.ecom.services.AdminOrder;

import com.example.ecom.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> listOfOrder();
    OrderDto changeOrderStatus(Long orderId,String orderStatus);
}
