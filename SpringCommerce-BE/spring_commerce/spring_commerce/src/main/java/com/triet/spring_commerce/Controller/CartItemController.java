package com.triet.spring_commerce.Controller;

import com.triet.spring_commerce.Entity.CartItem;
import com.triet.spring_commerce.Request.AddCartItemRequest;
import com.triet.spring_commerce.Request.UpdateItemRequest;
import com.triet.spring_commerce.Service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/cart_items")
@Controller
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItemList(){
        List<CartItem> cartItemList = cartItemService.getCartItems();
        return new ResponseEntity<>(cartItemList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItems(@PathVariable Long id){
        CartItem cartItem = cartItemService.getCartItemById(id);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<List<CartItem>> getCartItemListByCartId(@PathVariable Long id){
        List<CartItem> cartItemList = cartItemService.getCartItemListByCartId(id);
        return new ResponseEntity<>(cartItemList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartItem> addToCart(@RequestBody AddCartItemRequest request){
        CartItem cartItem = cartItemService.addToCart(request);
        return new ResponseEntity<>(cartItem,HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CartItem> updateCart(@RequestBody UpdateItemRequest request){
        CartItem cartItem = cartItemService.updateCart(request);
        return new ResponseEntity<>(cartItem,HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id){
        cartItemService.deleteCartItem(id);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }
}
