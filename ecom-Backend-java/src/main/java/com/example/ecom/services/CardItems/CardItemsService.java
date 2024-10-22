package com.example.ecom.services.CardItems;

import com.example.ecom.dto.AddProductInCardDto;
import com.example.ecom.dto.OrderDto;
import com.example.ecom.dto.OrderPlacedDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CardItemsService {
    ResponseEntity<?> addProductInCard(AddProductInCardDto addProductInCardDto);
    OrderDto getCardItemsByUserId(Long userId);
    OrderDto applyCoupon(Long userId, String code);
    OrderDto decreaseQuantity(AddProductInCardDto addProductInCardDto);
    OrderDto increaseQuantity(AddProductInCardDto addProductInCardDto);
    OrderDto placedOrder(OrderPlacedDto orderPlacedDto);
    List<OrderDto> getMyPlacedOrder(Long userId);
    OrderDto searchOrderByTrackingId(UUID trackingId);
}
