package com.example.ecom.AuthenticationController.adminController;

import com.example.ecom.dto.FAQDto;
import com.example.ecom.services.AdminFAQ.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/faq")
public class FAQController {
    @Autowired
    private FAQService faqService;

    @PostMapping("/add/{productId}")
    public ResponseEntity<FAQDto> postFAQ(@PathVariable Long productId, @RequestBody FAQDto faqDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(faqService.postFAQ(productId,faqDto));
    }
}
