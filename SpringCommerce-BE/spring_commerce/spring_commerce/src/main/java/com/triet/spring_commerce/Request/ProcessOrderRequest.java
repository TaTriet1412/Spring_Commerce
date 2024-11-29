package com.triet.spring_commerce.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProcessOrderRequest {
    private Long userId;
    private Integer total_amount;
    private String delivery_address;
    private List<OrderItemRequest> orderItemRequests;
}

