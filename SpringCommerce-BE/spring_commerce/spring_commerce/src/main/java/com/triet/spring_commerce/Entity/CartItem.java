package com.triet.spring_commerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cart_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "cart_id") // tên cột sẽ lưu id của ProductDetail
    private Cart cart;

    // Getter và Setter
    public Long getCartId() {
        return cart != null ? cart.getId() : null;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "product_id") // tên cột sẽ lưu id của ProductDetail
    private Product product;

    // Getter và Setter
    public Long getProductId() {
        return product != null ? product.getId() : null;
    }
}

