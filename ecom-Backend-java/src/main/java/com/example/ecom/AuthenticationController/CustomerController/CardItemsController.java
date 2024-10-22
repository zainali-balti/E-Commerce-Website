package com.example.ecom.AuthenticationController.CustomerController;

import com.example.ecom.Exception.ValidationExcentionForCoupon;
import com.example.ecom.dto.AddProductInCardDto;
import com.example.ecom.dto.OrderDto;
import com.example.ecom.dto.OrderPlacedDto;
import com.example.ecom.services.CardItems.CardItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/card")
public class CardItemsController {

    @Autowired
    private CardItemsService cardItemsService;

    @PostMapping("/add")
    public ResponseEntity<?> addProductInCard(@RequestBody AddProductInCardDto addProductInCardDto){
        return ResponseEntity.ok(cardItemsService.addProductInCard(addProductInCardDto));
    }
    @GetMapping("/all/{userId}")
    public  ResponseEntity<?> getCardByUserId(@PathVariable Long userId){
        OrderDto orderDto = cardItemsService.getCardItemsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }
    @GetMapping("/all/{userId}/{code}")
    public ResponseEntity<?> getCouponByUserIdAndCode(@PathVariable Long userId, @PathVariable String code){
        try{
            OrderDto orderDto = cardItemsService.applyCoupon(userId,code);
            return ResponseEntity.ok(orderDto);
        }catch (ValidationExcentionForCoupon validationExcentionForCoupon){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationExcentionForCoupon.getMessage());
        }
    }
    @PostMapping("/addition")
    public ResponseEntity<OrderDto> increaseQuantity(@RequestBody AddProductInCardDto addProductInCardDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cardItemsService.increaseQuantity(addProductInCardDto));
    }
    @PostMapping("/subtraction")
    public ResponseEntity<OrderDto> decreaseQuantity(@RequestBody AddProductInCardDto addProductInCardDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cardItemsService.decreaseQuantity(addProductInCardDto));
    }
    @PostMapping("/placedOrder")
    public ResponseEntity<OrderDto> placedOrder(@RequestBody OrderPlacedDto orderPlacedDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cardItemsService.placedOrder(orderPlacedDto));
    }
    @GetMapping("/myOrder/{userId}")
    public ResponseEntity<List<OrderDto>> getMyOrderPlaced(@PathVariable Long userId){
        return ResponseEntity.ok(cardItemsService.getMyPlacedOrder(userId));
    }
}
