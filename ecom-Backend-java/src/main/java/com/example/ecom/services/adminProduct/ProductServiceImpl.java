package com.example.ecom.services.adminProduct;

import com.example.ecom.dto.ProductDto;
import com.example.ecom.entity.Category;
import com.example.ecom.entity.Product;
import com.example.ecom.repository.CategoryRepo;
import com.example.ecom.repository.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    public ProductDto addProduct(ProductDto productDto, MultipartFile imgFile) {
        if (productDto.getCategoryId() == null) {
            throw new IllegalArgumentException("Category ID must not be null");
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        try {
            if (imgFile != null && !imgFile.isEmpty()) {
                product.setImg(imgFile.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading image file", e);
        }
        Category category = categoryRepo.findById(productDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException
                        ("Category not found with id: " + productDto.getCategoryId()));

        product.setCategory(category);

        return productRepo.save(product).getDto();
    }

    public List<ProductDto> getAllProducts(){
        List<Product> product = productRepo.findAll();
        return product.stream().map(Product::getDto).collect(Collectors.toList());
    }
    public List<ProductDto> getAllProductsByName(String name){
        List<Product> product = productRepo.findByName(name);
        return product.stream().map(Product::getDto).collect(Collectors.toList());
    }
    public boolean deleteById(Long id){
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent()){
            productRepo.deleteById(id);
            return true;
        }
        return false;
    }
    public ProductDto updateProduct(Long id, ProductDto productDto, MultipartFile imgFile) {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        if (productDto.getCategoryId() != null) {
            Category category = categoryRepo.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + productDto.getCategoryId()));
            existingProduct.setCategory(category);
        }
        try {
            if (imgFile != null && !imgFile.isEmpty()) {
                existingProduct.setImg(imgFile.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading image file", e);
        }
        return productRepo.save(existingProduct).getDto();
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return product.getDto();
    }


}
