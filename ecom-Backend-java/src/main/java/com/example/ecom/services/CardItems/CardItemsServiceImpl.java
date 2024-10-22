package com.example.ecom.services.CardItems;

import com.example.ecom.Exception.OrderNotFoundException;
import com.example.ecom.Exception.ValidationExcentionForCoupon;
import com.example.ecom.dto.AddProductInCardDto;
import com.example.ecom.dto.CardItemsDto;
import com.example.ecom.dto.OrderDto;
import com.example.ecom.dto.OrderPlacedDto;
import com.example.ecom.entity.*;
import com.example.ecom.enums.OrderStatus;
import com.example.ecom.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CardItemsServiceImpl implements CardItemsService{
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CardItemsRepo cardItemsRepo;
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CouponRepo couponRepo;
    public ResponseEntity<?> addProductInCard(AddProductInCardDto addProductInCardDto){
        Order order = orderRepo.findByUserIdAndOrderStatus(addProductInCardDto.getUserId(), OrderStatus.Pending);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No pending order found for this user.");
        }
        Optional<CardItems> cardItems = cardItemsRepo.findByProductIdAndUserIdAndOrderId
                (addProductInCardDto.getProductId(),addProductInCardDto.getUserId(),order.getId());
        if (cardItems.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("cardItems already Present");
        }
        else {
            Optional<Product> product = productRepo.findById(addProductInCardDto.getProductId());
            Optional<User> user = userRepo.findById(addProductInCardDto.getUserId());
            if (product.isPresent() && user.isPresent()) {
                CardItems cardItems1 = new CardItems();
                cardItems1.setProduct(product.get());
                cardItems1.setUser(user.get());
                cardItems1.setQuantity(1L);
                cardItems1.setPrice(product.get().getPrice());
                cardItems1.setOrder(order);
                cardItemsRepo.save(cardItems1);

                order.setAmount(order.getAmount() + cardItems1.getPrice());
                order.setTotalAmount(order.getTotalAmount() + cardItems1.getPrice());
                order.getCardItems().add(cardItems1);
                orderRepo.save(order);
                return ResponseEntity.status(HttpStatus.CREATED).body(cardItems1);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sorry Product id or user id not found");
            }
        }
    }




    public OrderDto getCardItemsByUserId(Long userId){
        Order order = orderRepo.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CardItemsDto> cardItemsDtos = order.getCardItems().stream()
                .map(CardItems::getCardItems).collect(Collectors.toList());
        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(order.getAmount());
        orderDto.setDiscount(orderDto.getDiscount());
        orderDto.setId(order.getId());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setTotalAmount(order.getTotalAmount());
        orderDto.setCardItems(cardItemsDtos);
        if (order.getCoupon() != null){
            orderDto.setCouponName(order.getCoupon().getName());

        }
        return orderDto;

    }


    public OrderDto applyCoupon(Long userId, String code){
        Order order = orderRepo.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        Coupon coupon = couponRepo.findByCode(code).orElseThrow(() -> new ValidationExcentionForCoupon("coupon code Not Found"));
        if (couponIsExpired(coupon)){
            throw new ValidationExcentionForCoupon("Coupon Code Was Expired"+coupon.getCode());
        }
        double discountAmount = (coupon.getDiscount()/100.0)*order.getTotalAmount();
        double netAmount = order.getTotalAmount() - discountAmount;

        order.setCoupon(coupon);
        order.setDiscount((long) discountAmount);
        order.setTotalAmount((long) netAmount);

        orderRepo.save(order);
        return order.getOrderDto();
    }

    private boolean couponIsExpired(Coupon coupon) {
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpirationDate();
        System.out.println("Current date: " + currentDate);
        System.out.println("Expiration date: " + expirationDate);
        return expirationDate != null && currentDate.after(expirationDate);
    }

    public OrderDto increaseQuantity(AddProductInCardDto addProductInCardDto) {
        Order order = orderRepo.findByUserIdAndOrderStatus(addProductInCardDto.getUserId(), OrderStatus.Pending);
        Optional<CardItems> cardItemsOptional = cardItemsRepo.findByProductIdAndUserIdAndOrderId(
                addProductInCardDto.getProductId(), addProductInCardDto.getUserId(), order.getId());
        Optional<Product> productOptional = productRepo.findById(addProductInCardDto.getProductId());

        if (productOptional.isPresent() && cardItemsOptional.isPresent()) {
            CardItems cardItems = cardItemsOptional.get();
            Product product = productOptional.get();
            cardItems.setQuantity(cardItems.getQuantity() + 1);
            double productPrice = product.getPrice();
            double updatedTotalAmount = order.getTotalAmount() + productPrice;
            double updatedAmount = order.getAmount() + productPrice;
            order.setTotalAmount((long)updatedTotalAmount);
            order.setAmount((long)updatedAmount);
            if (order.getCoupon() != null) {
                double discountAmount = (order.getCoupon().getDiscount() / 100.0) * updatedAmount;
                double netAmount = updatedAmount - discountAmount;
                order.setDiscount((long)discountAmount);
                order.setTotalAmount((long)netAmount);
            }
            cardItemsRepo.save(cardItems);
            orderRepo.save(order);
            return order.getOrderDto();
        }
        return null;
    }


    public OrderDto decreaseQuantity(AddProductInCardDto addProductInCardDto) {
        Order order = orderRepo.findByUserIdAndOrderStatus(addProductInCardDto.getUserId(), OrderStatus.Pending);
        Optional<CardItems> cardItemsOptional = cardItemsRepo.findByProductIdAndUserIdAndOrderId(
                addProductInCardDto.getProductId(), addProductInCardDto.getUserId(), order.getId());
        Optional<Product> productOptional = productRepo.findById(addProductInCardDto.getProductId());

        if (productOptional.isPresent() && cardItemsOptional.isPresent()) {
            CardItems cardItems = cardItemsOptional.get();
            Product product = productOptional.get();
            if (cardItems.getQuantity() > 0) {
                cardItems.setQuantity(cardItems.getQuantity() - 1);
                double productPrice = product.getPrice();
                order.setTotalAmount(order.getTotalAmount() - (long) productPrice);
                order.setAmount(order.getAmount() - (long) productPrice);
                if (order.getCoupon() != null) {
                    double discountAmount = (order.getCoupon().getDiscount() / 100.0) * order.getAmount();
                    double netAmount = order.getAmount() - discountAmount;
                    order.setDiscount((long) discountAmount);
                    order.setTotalAmount((long) netAmount);
                }
                cardItemsRepo.save(cardItems);
                orderRepo.save(order);
            } else {
                System.out.println("Quantity cannot be decreased further.");
            }
            return order.getOrderDto();
        }
        return null;
    }

    public OrderDto placedOrder(OrderPlacedDto orderPlacedDto){
        Order order = orderRepo.findByUserIdAndOrderStatus(orderPlacedDto.getUserId(), OrderStatus.Pending);
        Optional<User> userOptional = userRepo.findById(orderPlacedDto.getUserId());
        if (userOptional.isPresent()){
            order.setOrderStatus(OrderStatus.Placed);
            order.setAddress(orderPlacedDto.getAddress());
            order.setDescription(orderPlacedDto.getOrderDescription());
            order.setTrackingId(UUID.randomUUID());
            order.setDate(new Date());
            orderRepo.save(order);
            Order order1 = new Order();
            order1.setAmount(0L);
            order1.setTotalAmount(0L);
            order1.setDiscount(0L);
            order1.setUser(userOptional.get());
            order1.setOrderStatus(OrderStatus.Pending);
            orderRepo.save(order1);
            return order.getOrderDto();
        }
        return null;
    }


    public List<OrderDto> getMyPlacedOrder(Long userId){
        return  orderRepo.findByUserIdAndOrderStatusIn
                        (userId,List.of(OrderStatus.Placed,OrderStatus.Delivered,OrderStatus.Shipped,OrderStatus.Pending))
                .stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    public OrderDto searchOrderByTrackingId(UUID trackingId){
        Optional<Order> orderOptional = orderRepo.findByTrackingId(trackingId);
        if (orderOptional.isPresent()){
            return orderOptional.get().getOrderDto();
        }
        return null;
    }




}
