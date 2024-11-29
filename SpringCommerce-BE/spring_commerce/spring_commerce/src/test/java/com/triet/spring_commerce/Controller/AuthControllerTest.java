package com.triet.spring_commerce.Controller;

import com.triet.spring_commerce.Entity.User;
import com.triet.spring_commerce.ErrorHandler.ApiError;
import com.triet.spring_commerce.Request.LoginRequest;
import com.triet.spring_commerce.Request.RegisterRequest;
import com.triet.spring_commerce.Security.JwtResponse;
import com.triet.spring_commerce.Security.JwtTokenProvider;
import com.triet.spring_commerce.Service.UserService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;  // Mock BindingResult

    @InjectMocks
    private AuthController authController;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User mockUser;

    @BeforeEach
    void setUp() {
        // Setup mock user data for the test
        mockUser = new User(1L, "Nguyễn Văn A", "nguyenvana@example.com", "password123", "0901234567", null, null, true, null);

        // Initialize the test requests
        registerRequest = new RegisterRequest();
        registerRequest.setEmail("nguyenvana@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setPhone("0901234567");
        registerRequest.setName("Nguyễn Văn A");
        registerRequest.setGender(true);

        loginRequest = new LoginRequest();
        loginRequest.setEmail("nguyenvana@example.com");
        loginRequest.setPassword("password123");
    }

    @Test
    void testRegisterUserSuccess() {
        when(userService.registerNewAccount(any(RegisterRequest.class))).thenReturn(mockUser);
        when(bindingResult.hasErrors()).thenReturn(false); // Simulate no validation errors

        ResponseEntity<?> response = authController.registerUser(registerRequest, bindingResult);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof User);
        verify(userService).registerNewAccount(any(RegisterRequest.class));
    }

    @Test
    void testRegisterUserFail_ValidationError() {
        when(bindingResult.hasErrors()).thenReturn(true); // Simulate validation errors
        when(bindingResult.getAllErrors()).thenReturn(
                List.of(new ObjectError("email", "Email không được để trống"))
        );

        ResponseEntity<?> response = authController.registerUser(registerRequest, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testLoginUserSuccess() {
        String jwtToken = "mock.jwt.token";
        when(userService.afterLogin(any(String.class), any(String.class))).thenReturn(mockUser);
        when(jwtTokenProvider.generateToken(any(String.class))).thenReturn(jwtToken);

        ResponseEntity<?> response = authController.loginUser(loginRequest, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof JwtResponse);
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertEquals(jwtToken, jwtResponse.getToken());
        assertEquals(mockUser, jwtResponse.getUser());
        verify(userService).afterLogin(any(String.class), any(String.class));
    }
}
