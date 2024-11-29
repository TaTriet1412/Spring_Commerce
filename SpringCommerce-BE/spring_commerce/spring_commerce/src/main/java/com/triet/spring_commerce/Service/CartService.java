package com.triet.spring_commerce.Service;

import com.triet.spring_commerce.Entity.Cart;
import com.triet.spring_commerce.Entity.User;
import com.triet.spring_commerce.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getCarts(){
        return cartRepository.findAll();
    }

    public Cart getCartById(Long id){
        return cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng này"));
    }

    public Cart getCartByUserId(Long userId) {
        for(Cart cart:getCarts()){
            if(cart.getUser().getId() == userId){
                return cart;
            }
        }
        throw new RuntimeException("Không tìm thấy giỏ hàng của user này");
    }

    public Cart createNewCart(User user){
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
}
