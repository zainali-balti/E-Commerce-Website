package com.example.ecom.dto;

import com.example.ecom.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String email;
    private Long id;
    private UserRole userRole;
}
