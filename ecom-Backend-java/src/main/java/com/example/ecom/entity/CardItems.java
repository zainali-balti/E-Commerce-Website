package com.example.ecom.entity;

import com.example.ecom.dto.CardItemsDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "card_item")
public class CardItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long quantity;
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "product_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

    public CardItemsDto getCardItems(){
        CardItemsDto cardItemsDto = new CardItemsDto();
        cardItemsDto.setId(id);
        cardItemsDto.setPrice(price);
        cardItemsDto.setQuantity(quantity);
        cardItemsDto.setProductId(product.getId());
        cardItemsDto.setProductName(product.getName());
        cardItemsDto.setReturnedImg(product.getImg());
        cardItemsDto.setUserId(user.getId());
        return cardItemsDto;
    }
}
