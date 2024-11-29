package com.triet.spring_commerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Integer quantity;

    @Column
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "order_id") // tên cột sẽ lưu id của ProductDetail
    private Order order;

    // Getter và Setter
    public Long getOrderId() {
        return order != null ? order.getId() : null;
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
