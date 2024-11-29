package com.triet.spring_commerce.Controller;

import com.triet.spring_commerce.Entity.Brand;
import com.triet.spring_commerce.Entity.Cart;
import com.triet.spring_commerce.Service.BrandService;
import com.triet.spring_commerce.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/carts")
@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCarts(@PathVariable Long id){
        Cart cart = cartService.getCartById(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long id){
        Cart cart = cartService.getCartByUserId(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
