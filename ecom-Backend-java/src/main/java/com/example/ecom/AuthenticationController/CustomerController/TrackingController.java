package com.example.ecom.AuthenticationController.CustomerController;

import com.example.ecom.dto.OrderDto;
import com.example.ecom.services.CardItems.CardItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/tracking")
public class TrackingController {
    @Autowired
    private CardItemsService cardItemsService;

    @GetMapping("/{trackingId}")
    public ResponseEntity<OrderDto> getByTrackingId(@PathVariable UUID trackingId){
        OrderDto orderDto = cardItemsService.searchOrderByTrackingId(trackingId);
        if (orderDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderDto);
    }
}
