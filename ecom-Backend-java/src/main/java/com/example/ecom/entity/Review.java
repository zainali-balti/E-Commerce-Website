package com.example.ecom.entity;

import com.example.ecom.dto.ReviewDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long rating;
    @Lob
    private String description;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    public ReviewDto getReviewDto() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(id);
        reviewDto.setReturnedImg(img);
        reviewDto.setDescription(description);
        reviewDto.setRating(rating);
        reviewDto.setUserId(user.getId());
        reviewDto.setUserName(user.getName());
        reviewDto.setProductId(product.getId());
        return reviewDto;
    }
}
