package com.example.ecom.services.WishList;

import com.example.ecom.dto.WishListDto;

import java.util.List;

public interface WishListService {
    WishListDto addProductToWishList(WishListDto wishListDto);
    List<WishListDto> getAllWishLIstByUserId(Long userId);
}
