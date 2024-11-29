package com.triet.spring_commerce.Controller;

import com.triet.spring_commerce.Entity.CartItem;
import com.triet.spring_commerce.Entity.Order;
import com.triet.spring_commerce.Entity.Product;
import com.triet.spring_commerce.Request.AddCartItemRequest;
import com.triet.spring_commerce.Request.ProcessOrderRequest;
import com.triet.spring_commerce.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/orders")
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Order> getOrderByUserId(@PathVariable Long userId){
        Order order = orderService.getCurrOrderOfUser(userId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> processOrder(@RequestBody ProcessOrderRequest request){
        Order order = orderService.processOrder(request);
        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }
}
