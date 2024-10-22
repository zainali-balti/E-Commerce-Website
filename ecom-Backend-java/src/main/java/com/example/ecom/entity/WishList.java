package com.example.ecom.entity;

import com.example.ecom.dto.WishListDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "wishlist")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "product_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public WishListDto getWishListDto() {

        WishListDto wishListDto = new WishListDto();
        wishListDto.setId(id);
        wishListDto.setPrice(product.getPrice());
        wishListDto.setUserId(user.getId());
        wishListDto.setReturnedImg(product.getImg());
        wishListDto.setProductId(product.getId());
        wishListDto.setProductDescription(product.getDescription());
        wishListDto.setProductName(product.getName());
        return wishListDto;
    }
}
