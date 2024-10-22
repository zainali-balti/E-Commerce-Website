package com.example.ecom.services.AdminOrder;

import com.example.ecom.dto.OrderDto;
import com.example.ecom.entity.Order;
import com.example.ecom.enums.OrderStatus;
import com.example.ecom.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepo orderRepo;
    public List<OrderDto> listOfOrder(){
        List<Order> orderList = orderRepo.findAllByOrderStatusIn(List.of(OrderStatus.Pending,OrderStatus.Delivered,OrderStatus.Placed,OrderStatus.Shipped));
        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }
    public OrderDto changeOrderStatus(Long orderId,String orderStatus){
        Optional<Order> orderOptional = orderRepo.findById(orderId);
        if (orderOptional.isPresent()){
            Order order = orderOptional.get();
            if (Objects.equals(orderStatus,"Shipped")){
                order.setOrderStatus(OrderStatus.Shipped);
            } else if (Objects.equals(orderStatus, "Delivered")) {
                order.setOrderStatus(OrderStatus.Delivered);
            }
            return orderRepo.save(order).getOrderDto();
        }
        return null;
    }
}
