package com.example.ecom.AuthenticationController.AdminOrderController;

import com.example.ecom.dto.OrderDto;
import com.example.ecom.entity.Order;
import com.example.ecom.services.AdminOrder.OrderService;
import com.example.ecom.services.CardItems.CardItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CardItemsService cardItemsService;
    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrder(){
        return ResponseEntity.ok(orderService.listOfOrder());
    }
    @GetMapping("/orderStatus/{orderId}/{orderStatus}")
    public ResponseEntity<OrderDto> changeOrderStatus(@PathVariable Long orderId,@PathVariable String orderStatus){
        OrderDto orderDto = orderService.changeOrderStatus(orderId,orderStatus);
        if (orderDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(orderDto);
    }

}
