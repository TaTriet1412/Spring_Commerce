package com.triet.spring_commerce.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateItemRequest {
    private Long cartId;
    private Long productId;
    private Integer quantity;
}
