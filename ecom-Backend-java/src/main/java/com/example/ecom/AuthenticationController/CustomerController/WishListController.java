package com.example.ecom.AuthenticationController.CustomerController;

import com.example.ecom.dto.WishListDto;
import com.example.ecom.services.WishList.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/wishlist")
public class WishListController {
    @Autowired
    private WishListService wishListService;

    @PostMapping("/add")
    public ResponseEntity<?> addWishList(@RequestBody WishListDto wishListDto){
        WishListDto wishListDto1 = wishListService.addProductToWishList(wishListDto);
        if (wishListDto1 == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(wishListDto1);
    }
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<WishListDto>> getAll(@PathVariable Long userId){
        return ResponseEntity.ok(wishListService.getAllWishLIstByUserId(userId));
    }

}
