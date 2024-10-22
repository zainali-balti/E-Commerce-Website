package com.example.ecom.AuthenticationController.CustomerController;

import com.example.ecom.dto.ProductDetailsDto;
import com.example.ecom.dto.ProductDto;
import com.example.ecom.services.CustomerProduct.CustomerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer/product")
public class CustomerProductController{
    @Autowired
    private CustomerProductService customerProductService;
    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDtos = customerProductService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }
    @GetMapping("/all/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name){
        List<ProductDto> productDtos = customerProductService.getAllProductsByName(name);
        return ResponseEntity.ok(productDtos);
    }
    @GetMapping("/details/{productId}")
    public ResponseEntity<ProductDetailsDto> getAllProductDetails(@PathVariable Long productId){
        ProductDetailsDto productDetailsDto = customerProductService.getAllProductDetailsById(productId);
        if (productDetailsDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDetailsDto);
    }
}
