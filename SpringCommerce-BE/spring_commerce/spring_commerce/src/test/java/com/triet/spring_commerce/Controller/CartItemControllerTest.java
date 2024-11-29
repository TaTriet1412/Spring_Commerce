package com.triet.spring_commerce.Controller;

import com.triet.spring_commerce.Entity.CartItem;
import com.triet.spring_commerce.Repository.CartItemRepository;
import com.triet.spring_commerce.Service.CartItemService;
import com.triet.spring_commerce.Service.CartService;
import com.triet.spring_commerce.Service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.hamcrest.Matchers.is;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

public class CartItemControllerTest {

    @Mock
    private CartItemService cartItemService;

    @InjectMocks
    private CartItemController cartItemController;

    @Mock
    private ProductService productService;

    @Mock
    private CartService cartService;

    @Mock
    private CartItemRepository cartItemRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartItemController).build();
    }

    @Test
    void testGetCartItemList() throws Exception {
        // Arrange: Mock data
        CartItem cartItem1 = new CartItem();
        cartItem1.setId(1L);
        CartItem cartItem2 = new CartItem();
        cartItem2.setId(2L);
        List<CartItem> cartItemList = Arrays.asList(cartItem1, cartItem2);

        when(cartItemService.getCartItems()).thenReturn(cartItemList);

        // Act & Assert: Perform GET request and check response
        mockMvc.perform(get("/cart_items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(cartItemService, times(1)).getCartItems();
    }

    @Test
    void testGetCartItemsById() throws Exception {
        // Arrange: Mock data
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);

        when(cartItemService.getCartItemById(1L)).thenReturn(cartItem);

        // Act & Assert: Perform GET request and check response
        mockMvc.perform(get("/cart_items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(cartItemService, times(1)).getCartItemById(1L);
    }

    @Test
    void testGetCartItemListByCartId() throws Exception {
        // Arrange: Mock data
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        List<CartItem> cartItemList = Arrays.asList(cartItem);

        when(cartItemService.getCartItemListByCartId(1L)).thenReturn(cartItemList);

        // Act & Assert: Perform GET request and check response
        mockMvc.perform(get("/cart_items/cart/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(cartItemService, times(1)).getCartItemListByCartId(1L);
    }
}
