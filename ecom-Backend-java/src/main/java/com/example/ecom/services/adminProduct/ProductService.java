package com.example.ecom.services.adminProduct;

import com.example.ecom.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
   ProductDto addProduct(ProductDto productDto, MultipartFile imgFile);
   List<ProductDto> getAllProducts();
   List<ProductDto> getAllProductsByName(String name);
   boolean deleteById(Long id);
   ProductDto updateProduct(Long id, ProductDto productDto, MultipartFile imgFile);
   ProductDto getProductById(Long id);
}
