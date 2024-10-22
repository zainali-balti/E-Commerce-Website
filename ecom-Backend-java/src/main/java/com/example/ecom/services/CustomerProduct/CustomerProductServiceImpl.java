package com.example.ecom.services.CustomerProduct;

import com.example.ecom.dto.FAQDto;
import com.example.ecom.dto.ProductDetailsDto;
import com.example.ecom.dto.ProductDto;
import com.example.ecom.dto.ReviewDto;
import com.example.ecom.entity.FAQ;
import com.example.ecom.entity.Product;
import com.example.ecom.entity.Review;
import com.example.ecom.repository.FAQRepo;
import com.example.ecom.repository.ProductRepo;
import com.example.ecom.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerProductServiceImpl implements CustomerProductService{
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private FAQRepo faqRepo;
    @Autowired
    private ReviewRepo reviewRepo;

    public List<ProductDto> getAllProducts(){
        List<Product> product = productRepo.findAll();
        return product.stream().map(Product::getDto).collect(Collectors.toList());
    }
    public List<ProductDto> getAllProductsByName(String name){
        List<Product> product = productRepo.findByName(name);
        return product.stream().map(Product::getDto).collect(Collectors.toList());
    }
    public ProductDetailsDto getAllProductDetailsById(Long productId){
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isPresent()){
            List<FAQ> faqList = faqRepo.findAllByProductId(productId);
            List<Review> reviewList = reviewRepo.findAllByProductId(productId);
            ProductDetailsDto productDetailsDto = new ProductDetailsDto();
            productDetailsDto.setProductDto(optionalProduct.get().getDto());
            productDetailsDto.setFaqDtos(faqList.isEmpty() ? new ArrayList<>() :faqList.stream().map(FAQ::getDto).collect(Collectors.toList()));
            productDetailsDto.setReviewDtos(reviewList.isEmpty() ? new ArrayList<>() :reviewList.stream().map(Review::getReviewDto).collect(Collectors.toList()));
            return productDetailsDto;
        }
        return null;
    }
}
