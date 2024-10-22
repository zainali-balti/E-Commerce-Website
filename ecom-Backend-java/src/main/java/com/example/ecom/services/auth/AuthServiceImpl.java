package com.example.ecom.services.auth;

import com.example.ecom.dto.SignUpRequest;
import com.example.ecom.dto.UserDto;
import com.example.ecom.entity.Order;
import com.example.ecom.entity.User;
import com.example.ecom.enums.OrderStatus;
import com.example.ecom.enums.UserRole;
import com.example.ecom.repository.OrderRepo;
import com.example.ecom.repository.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(SignUpRequest signUpRequest){
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        user.setRole(UserRole.CUSTOMER);
        User userCreated = userRepo.save(user);
        Order order = new Order();
        order.setAmount(0L);
        order.setTotalAmount(0L);
        order.setDiscount(0L);
        order.setUser(userCreated);
        order.setOrderStatus(OrderStatus.Pending);
        orderRepo.save(order);
        UserDto userDto = new UserDto();
        userDto.setId(userCreated.getId());
        return userDto;
    }

    public Boolean hasUserWithEmail(String email){
        return userRepo.findFirstByEmail(email).isPresent();
    }
    @PostConstruct
    public void createAdminAccount(){
        User user = userRepo.findByRole(UserRole.ADMIN);
        if (user == null){
            user = new User();
            user.setName("admin");
            user.setEmail("test123@gmail.com");
            user.setRole(UserRole.ADMIN);
            user.setPassword(bCryptPasswordEncoder.encode("admin"));
            this.userRepo.save(user);
        }
    }
}
