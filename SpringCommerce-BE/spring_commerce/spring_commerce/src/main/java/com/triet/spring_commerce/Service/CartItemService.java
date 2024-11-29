package com.triet.spring_commerce.Service;

import com.triet.spring_commerce.Entity.CartItem;
import com.triet.spring_commerce.Repository.CartItemRepository;
import com.triet.spring_commerce.Request.AddCartItemRequest;
import com.triet.spring_commerce.Request.UpdateItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    public List<CartItem> getCartItems(){
        return cartItemRepository.findAll();
    }

    public CartItem getCartItemById(Long id){
        return cartItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết giỏ hàng này"));
    }

    public List<CartItem> getCartItemListByCartId(Long cartId){
        List<CartItem> result = new ArrayList<>();
        for(CartItem cartItem:getCartItems()){
            if(cartItem.getCart()!=null){
                if(Objects.equals(cartItem.getCart().getId(), cartId)){
                    result.add(cartItem);
                }
            }
        }
        return result;
    }

    public CartItem addToCart(AddCartItemRequest request){
        for(CartItem cartItem:getCartItems()){
            if(Objects.equals(cartItem.getProductId(), request.getProductId())
                && Objects.equals(cartItem.getCartId(), request.getCartId())
            ){
                cartItem.setQuantity(
                        cartItem.getQuantity() + request.getQuantity()
                );
                cartItemRepository.save(cartItem);
                return  cartItem;
            }
        }
        CartItem cartItem = new CartItem();
        cartItem.setProduct(productService.getProductById(request.getProductId()));
        cartItem.setCart(cartService.getCartById(request.getCartId()));
        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    public CartItem updateCart(UpdateItemRequest request){
        for(CartItem cartItem:getCartItems()){
            if(Objects.equals(cartItem.getProductId(), request.getProductId())
                    && Objects.equals(cartItem.getCartId(), request.getCartId())
            ){
                cartItem.setQuantity(request.getQuantity());
                cartItemRepository.save(cartItem);
                return cartItem;
            }
        }
        throw new RuntimeException("Không tồn tại sản phẩm này trong giỏ hàng để cập nhật");
    }

    public void deleteCartItem(Long id){
        cartItemRepository.deleteById(id);
    }
}
