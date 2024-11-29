package com.triet.spring_commerce.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private Integer price;

    @Column
    private Integer discount;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public Long getBrandId() { return brand != null ? brand.getId() : null; }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "category_id")
    private Category category;

    public Long getCategoryId() { return category != null ? category.getId() : null; }


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "color_id")
    private Color color;

    public Long getColorId() { return color != null ? color.getId() : null; }

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY) // liên kết với Product, để bi-directional relationship
    @JsonIgnore
    private ProductDetails productDetails;
}

