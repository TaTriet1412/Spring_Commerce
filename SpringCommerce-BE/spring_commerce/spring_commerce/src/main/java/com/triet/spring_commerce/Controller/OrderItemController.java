package com.triet.spring_commerce.Controller;

import com.triet.spring_commerce.Entity.Order;
import com.triet.spring_commerce.Entity.OrderItem;
import com.triet.spring_commerce.Service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/order_items")
@Controller
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderItem>> getOrderItemByOrderId(@PathVariable Long id){
        List<OrderItem> orderItemList = orderItemService.getOrderItemListByOrderId(id);
        return new ResponseEntity<>(orderItemList, HttpStatus.OK);
    }
}
