package com.example.ecom.services.CustomerProduct;

import com.example.ecom.dto.ProductDetailsDto;
import com.example.ecom.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {
    List<ProductDto> getAllProducts();
    List<ProductDto> getAllProductsByName(String name);
    ProductDetailsDto getAllProductDetailsById(Long productId);
}
