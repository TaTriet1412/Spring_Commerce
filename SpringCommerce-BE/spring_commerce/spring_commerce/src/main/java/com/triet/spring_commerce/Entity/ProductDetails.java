package com.triet.spring_commerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "product_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String sku;

    @Column
    private Integer stock_quantity;

    @Column
    private Double weight;

    @Column
    private String dimensions;

    @Column
    private String color;

    @Column
    private String material;

    @Column
    private String image_url;

    @Column
    private String short_description;

    @Column
    private String long_description;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "product_id") // tên cột sẽ lưu id của ProductDetail
    private Product product;

    // Getter và Setter
    public Long getProductId() {
        return product != null ? product.getId() : null;
    }
}

