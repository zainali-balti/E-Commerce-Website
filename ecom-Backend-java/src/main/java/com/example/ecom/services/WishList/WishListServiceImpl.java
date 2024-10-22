package com.example.ecom.services.WishList;

import com.example.ecom.dto.WishListDto;
import com.example.ecom.entity.Product;
import com.example.ecom.entity.User;
import com.example.ecom.entity.WishList;
import com.example.ecom.repository.ProductRepo;
import com.example.ecom.repository.UserRepo;
import com.example.ecom.repository.WishListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishListServiceImpl implements WishListService{
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private WishListRepo wishListRepo;

    public WishListDto addProductToWishList(WishListDto wishListDto){
        Optional<Product> productOptional = productRepo.findById(wishListDto.getProductId());
        Optional<User> userOptional = userRepo.findById(wishListDto.getUserId());
        if (userOptional.isPresent() && productOptional.isPresent()){
            WishList wishList = new WishList();
            wishList.setProduct(productOptional.get());
            wishList.setUser(userOptional.get());
            return wishListRepo.save(wishList).getWishListDto();
        }
        return null;
    }
    public  List<WishListDto> getAllWishLIstByUserId(Long userId){
        List<WishList> wishLists = wishListRepo.findAllByuserId(userId);
        if (wishLists.isEmpty()){
            return Collections.emptyList();
        }
        return wishLists.stream()
                .map(WishList::getWishListDto)
                .collect(Collectors.toList());

    }

}
