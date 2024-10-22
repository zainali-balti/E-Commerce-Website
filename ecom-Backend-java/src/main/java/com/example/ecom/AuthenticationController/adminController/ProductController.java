package com.example.ecom.AuthenticationController.adminController;

import com.example.ecom.dto.ProductDetailsDto;
import com.example.ecom.dto.ProductDto;
import com.example.ecom.services.adminProduct.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> addProduct(@RequestPart("product") ProductDto productDto,@RequestPart("img") MultipartFile imgFile ){
        ProductDto productDto1 = productService.addProduct(productDto,imgFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto1);
    }
    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDtos = productService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }
    @GetMapping("/all/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable  String name){
        List<ProductDto> productDtos = productService.getAllProductsByName(name);
        return ResponseEntity.ok(productDtos);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable  Long id){
        return ResponseEntity.ok(productService.deleteById(id));
    }
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable("id") Long id,
            @RequestPart("product") ProductDto productDto,
            @RequestPart(value = "img", required = false) MultipartFile imgFile) {
        ProductDto updatedProduct = productService.updateProduct(id, productDto, imgFile);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductBYId(@PathVariable Long id){
        ProductDto productDto = productService.getProductById(id);
        return ResponseEntity.ok(productDto);
    }
}
