package com.triet.spring_commerce.Service;

import com.triet.spring_commerce.Entity.Cart;
import com.triet.spring_commerce.Entity.CartItem;
import com.triet.spring_commerce.Entity.Product;
import com.triet.spring_commerce.Entity.ProductDetails;
import com.triet.spring_commerce.Repository.CartItemRepository;
import com.triet.spring_commerce.Request.AddCartItemRequest;
import com.triet.spring_commerce.Request.UpdateItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private CartService cartService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartItemService cartItemService;

    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setQuantity(1);
    }

    @Test
    void testGetCartItems() {
        // Arrange: Mocking the repository to return a list of CartItems
        when(cartItemRepository.findAll()).thenReturn(List.of(cartItem));

        // Act: Calling the getCartItems method
        List<CartItem> cartItems = cartItemService.getCartItems();

        // Assert: Verifying the results
        assertNotNull(cartItems);
        assertEquals(1, cartItems.size());
        assertEquals(1L, cartItems.get(0).getId());
    }

    @Test
    void testGetCartItemById_Success() {
        // Arrange: Mocking the repository to return a CartItem by ID
        when(cartItemRepository.findById(1L)).thenReturn(java.util.Optional.of(cartItem));

        // Act: Calling the getCartItemById method
        CartItem result = cartItemService.getCartItemById(1L);

        // Assert: Verifying the result
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetCartItemById_Fail() {
        // Arrange: Mocking the repository to return an empty Optional
        when(cartItemRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        // Act & Assert: Verifying that an exception is thrown when CartItem is not found
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cartItemService.getCartItemById(999L);
        });
        assertEquals("Không tìm thấy chi tiết giỏ hàng này", exception.getMessage());
    }

    @Test
    void testGetCartItemListByCartId() {
        // Arrange: Mocking the service to return a list of CartItems with a specific CartId
        CartItem cartItem1 = new CartItem();
        CartItem cartItem2 = new CartItem();

        // Mock the Cart for both items
        Cart cart1 = new Cart();
        cart1.setId(1L);
        cartItem1.setCart(cart1);

        Cart cart2 = new Cart();
        cart2.setId(2L);
        cartItem2.setCart(cart2);

        // Mock getCartItems to return the list containing cartItem1 and cartItem2
        when(cartItemService.getCartItems()).thenReturn(List.of(cartItem1, cartItem2));

        // Act: Calling the getCartItemListByCartId method
        List<CartItem> cartItems = cartItemService.getCartItemListByCartId(1L);

        // Assert: Verifying the result
        assertNotNull(cartItems);
        assertEquals(1, cartItems.size());  // Only cartItem1 should match with CartId 1
        assertEquals(1L, cartItems.get(0).getCart().getId());
    }

    @Test
    void testAddToCart_Success() {
        // Arrange: Mocking dependencies
        when(cartItemRepository.findAll()).thenReturn(new ArrayList<>());
        when(cartService.getCartById(1L)).thenReturn(new Cart());
        when(productService.getProductById(1L)).thenReturn(new Product());

        AddCartItemRequest request = new AddCartItemRequest();
        request.setProductId(1L);
        request.setCartId(1L);
        request.setQuantity(2);

        // Act: Calling the addToCart method
        CartItem addedItem = cartItemService.addToCart(request);

        // Assert: Verifying the result
        assertNotNull(addedItem);
        assertEquals(2, addedItem.getQuantity());
    }

    @Test
    void testUpdateCart_Success() {
        // Arrange: Mocking dependencies
        CartItem existingCartItem = new CartItem();
        existingCartItem.setId(1L);
        Product product = new Product();
        product.setId(1L);  // Gán id sản phẩm
        existingCartItem.setProduct(product);  // Gán sản phẩm vào CartItem
        Cart cart = new Cart();
        cart.setId(1L);  // Gán id giỏ hàng
        existingCartItem.setCart(cart);  // Gán giỏ hàng vào CartItem
        existingCartItem.setQuantity(1);  // Số lượng sản phẩm ban đầu

        // Mock các phương thức trả về
        when(cartItemRepository.findAll()).thenReturn(List.of(existingCartItem)); // Trả về CartItem đã tồn tại
        when(cartService.getCartById(1L)).thenReturn(cart);  // Mock CartService trả về Cart
        when(productService.getProductById(1L)).thenReturn(product);  // Mock ProductService trả về Product

        UpdateItemRequest request = new UpdateItemRequest();
        request.setProductId(1L);  // Gán id sản phẩm
        request.setCartId(1L);     // Gán id giỏ hàng
        request.setQuantity(3);    // Số lượng muốn cập nhật

        // Act: Gọi phương thức updateCart
        CartItem updatedCartItem = cartItemService.updateCart(request);


        // Assert: Kiểm tra kết quả
        assertNotNull(updatedCartItem); // Đảm bảo đối tượng không null
        assertEquals(3, updatedCartItem.getQuantity());  // Kiểm tra số lượng sản phẩm được cập nhật
    }

    @Test
    void testUpdateCart_Fail() {
        // Arrange: Mocking the repository to simulate the absence of the cart item
        when(cartItemRepository.findAll()).thenReturn(new ArrayList<>());

        UpdateItemRequest request = new UpdateItemRequest();
        request.setProductId(999L);
        request.setCartId(999L);
        request.setQuantity(3);

        // Act & Assert: Verifying that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cartItemService.updateCart(request);
        });
        assertEquals("Không tồn tại sản phẩm này trong giỏ hàng để cập nhật", exception.getMessage());
    }

    @Test
    void testAddToCart_UpdateQuantity() {
        // Arrange: Mocking dependencies
        CartItem existingCartItem = new CartItem();
        Product product = new Product();
        product.setId(1L);  // Gán id sản phẩm
        Cart cart = new Cart();
        cart.setId(1L);
        existingCartItem.setProduct(product);  // Gán sản phẩm vào CartItem
        existingCartItem.setCart(cart);
        existingCartItem.setQuantity(1);
        when(cartItemRepository.findAll()).thenReturn(List.of(existingCartItem));
        when(cartService.getCartById(1L)).thenReturn(new Cart());
        when(productService.getProductById(1L)).thenReturn(new Product());

        AddCartItemRequest request = new AddCartItemRequest();
        request.setProductId(1L);
        request.setCartId(1L);
        request.setQuantity(3);

        // Act: Calling the addToCart method
        CartItem updatedCartItem = cartItemService.addToCart(request);

        // Assert: Verifying the result
        assertNotNull(updatedCartItem);
        assertEquals(4, updatedCartItem.getQuantity());  // Updated quantity
    }
}
