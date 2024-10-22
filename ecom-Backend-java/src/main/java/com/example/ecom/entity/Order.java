package com.example.ecom.entity;

import com.example.ecom.dto.OrderDto;
import com.example.ecom.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Long amount;
    private Long discount;
    private Long totalAmount;
    private UUID trackingId;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Date date;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon coupon;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CardItems> cardItems;
    public OrderDto getOrderDto(){
         OrderDto orderDto = new OrderDto();
         orderDto.setId(id);
         orderDto.setOrderStatus(orderStatus);
         orderDto.setAmount(amount);
         orderDto.setDate(date);
         orderDto.setTotalAmount(totalAmount);
         orderDto.setAddress(address);
         orderDto.setName(name);
         orderDto.setDescription(description);
         orderDto.setTrackingId(trackingId);
         orderDto.setUserName(user.getName());
         if (coupon != null){
             orderDto.setCouponName(coupon.getName());
         }
         return orderDto;
    }
}
