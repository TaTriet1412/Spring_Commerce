package com.triet.spring_commerce.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {
    private Long productId;
    private Integer quantity;
    private Integer price;
}
