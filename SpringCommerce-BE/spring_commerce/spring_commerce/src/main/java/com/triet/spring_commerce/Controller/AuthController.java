package com.triet.spring_commerce.Controller;

import com.triet.spring_commerce.Entity.User;
import com.triet.spring_commerce.Repository.UserRepository;
import com.triet.spring_commerce.Request.LoginRequest;
import com.triet.spring_commerce.Request.RegisterRequest;
import com.triet.spring_commerce.Security.JwtResponse;
import com.triet.spring_commerce.Security.JwtTokenProvider;
import com.triet.spring_commerce.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
@Controller
public class AuthController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Trả về lỗi nếu có lỗi xác thực
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors()); // Trả về các lỗi xác thực dưới dạng danh sách
        }

        User user = userService.registerNewAccount(registerRequest);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Trả về lỗi nếu có lỗi xác thực
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        // Generate JWT token
        User user = userService.afterLogin(loginRequest.getEmail(),loginRequest.getPassword());
        String jwtToken = jwtTokenProvider.generateToken(user.getEmail());

        // Return the JWT token along with user details (or just the token if preferred)
        return ResponseEntity.ok(new JwtResponse(jwtToken,user));
    }
}
