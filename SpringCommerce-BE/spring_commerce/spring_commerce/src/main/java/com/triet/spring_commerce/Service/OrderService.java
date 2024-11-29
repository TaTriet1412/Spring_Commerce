package com.triet.spring_commerce.Service;

import com.triet.spring_commerce.Entity.Order;
import com.triet.spring_commerce.Entity.User;
import com.triet.spring_commerce.Repository.OrderRepository;
import com.triet.spring_commerce.Request.OrderItemRequest;
import com.triet.spring_commerce.Request.ProcessOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderItemService orderItemService;

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng này"));
    }

    public List<Order> getOrdersByUserId(Long userId){
        List<Order> orderList = new ArrayList<>();
        for(Order order:getOrders()){
            if(Objects.equals(order.getUserId(), userId)){
                orderList.add(order);
            }
        }
        return orderList;
    }

    public Order getCurrOrderOfUser(Long userId){
        for(Order currOrder:getOrdersByUserId(userId)){
            if(!currOrder.isOrder_status()){
                return currOrder;
            }
        }
        return null;
    }

    public Order processOrder(ProcessOrderRequest request) {
        // Lấy thông tin người dùng một lần
        User user = userService.getUserById(request.getUserId());

        // Lấy hoặc tạo mới đơn hàng
        Order order = Optional.ofNullable(getCurrOrderOfUser(request.getUserId()))
                .orElse(new Order());

        // Cập nhật thông tin cho đơn hàng
        order.setTotal_amount(request.getTotal_amount());
        order.setUser(user);
        order.setDelivery_address(request.getDelivery_address());
        orderRepository.save(order);

        // Xử lý các OrderItemRequest
        orderItemService.processOrderItem(order, request.getOrderItemRequests());

        return order;
    }

}
