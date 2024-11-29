package com.triet.spring_commerce.Service;

import com.triet.spring_commerce.Entity.User;
import com.triet.spring_commerce.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartService cartService;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setEmail("nguyenvana@example.com");
        user.setPassword("$2a$12$iYx31c7O5HCoegLFdq5RROuNwppLHRp1y4z2cVwpYuRF1U54DZXPG"); // Mật khẩu đã mã hóa
    }

    @Test
    void testAfterLogin_Success() {
        // Arrange: Mocking the behavior of the userRepository
        when(userRepository.findAll()).thenReturn(List.of(user));

        // Act: Calling the afterLogin method
        User loggedInUser = userService.afterLogin("nguyenvana@example.com", "password123");

        // Assert: Verifying the user is returned successfully
        assertNotNull(loggedInUser);
        assertEquals("nguyenvana@example.com", loggedInUser.getEmail());
    }

    @Test
    void testAfterLogin_Fail_InvalidCredentials() {
        // Arrange: Mocking the behavior of the userRepository to return a user list
        when(userRepository.findAll()).thenReturn(List.of(user));

        // Act & Assert: Verifying that the exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.afterLogin("nguyenvana@example.com", "wrongPassword");
        });
        assertEquals("Thông tin đăng nhập không chính xác", exception.getMessage());
    }
}
