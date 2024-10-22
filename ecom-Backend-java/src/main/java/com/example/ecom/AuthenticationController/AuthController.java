package com.example.ecom.AuthenticationController;

import com.example.ecom.dto.AuthenticationRequest;
import com.example.ecom.dto.SignUpRequest;
import com.example.ecom.dto.UserDto;
import com.example.ecom.entity.User;
import com.example.ecom.repository.UserRepo;
import com.example.ecom.services.auth.AuthService;
import com.example.ecom.services.auth.UserDetailsServiceImpl;
import com.example.ecom.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    public static final String HEADER_PREFIX = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepo userRepo;
    private final JwtUtils jwtUtils;
    private final AuthService authService;

    @PostMapping("/authenticate")
    public void loginUser(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password!");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepo.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails.getUsername(), optionalUser.get().getRole().toString().toUpperCase());
        if (optionalUser.isPresent()){
            response.getWriter().write(new JSONObject()
                    .put("userId", optionalUser.get().getId())
                    .put("role",optionalUser.get().getRole())
                    .toString());
        }
        response.addHeader("Access-Control-Expose-Headers","Authorization");
        response.addHeader("Access-Control-Allow-Headers","Authorization, X-PINGOTHER, Origin," +
                " X-Requested-With, Content-Type,Accept,X-Custom-header");
        response.addHeader(HEADER_PREFIX,TOKEN_PREFIX+jwt);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUpUser(@RequestBody SignUpRequest signUpRequest){
        if (authService.hasUserWithEmail(signUpRequest.getEmail())){
            return new ResponseEntity<>("User is already created!", HttpStatus.BAD_REQUEST);
        }
        UserDto userDto = authService.createUser(signUpRequest);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }


}
