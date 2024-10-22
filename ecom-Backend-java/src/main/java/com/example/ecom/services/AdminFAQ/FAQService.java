package com.example.ecom.services.AdminFAQ;


import com.example.ecom.dto.FAQDto;

public interface FAQService {
    FAQDto postFAQ(Long productId, FAQDto faqDto);
}
