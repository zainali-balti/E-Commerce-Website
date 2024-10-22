package com.example.ecom.services.AdminFAQ;

import com.example.ecom.dto.FAQDto;
import com.example.ecom.entity.FAQ;
import com.example.ecom.entity.Product;
import com.example.ecom.repository.FAQRepo;
import com.example.ecom.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FAQServiceImpl implements FAQService{
    @Autowired
    private FAQRepo faqRepo;
    @Autowired
    private ProductRepo productRepo;

    public FAQDto postFAQ(Long productId, FAQDto faqDto){
       Optional<Product> product = productRepo.findById(productId);
       if (product.isPresent()) {
           FAQ faq = new FAQ();
           faq.setQuestion(faqDto.getQuestion());
           faq.setAnswer(faqDto.getAnswer());
           faq.setProduct(product.get());
           return faqRepo.save(faq).getDto();
       }
       return null;
    }
}
