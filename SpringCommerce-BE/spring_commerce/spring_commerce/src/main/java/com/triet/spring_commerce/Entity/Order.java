package com.triet.spring_commerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Integer total_amount;

    @Column
    private boolean order_status;

    @Column
    private String delivery_address;

    @Column
    private String payment_method;

    @Column
    private LocalDateTime order_date;

    @PrePersist protected void onCreate() {
        if (payment_method == null) {
            payment_method = "Thanh toán khi nhận hàng";
        }
        if(order_date == null){
            order_date = LocalDateTime.now();
        }
    }



    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id") // tên cột sẽ lưu id của ProductDetail
    private User user;

    // Getter và Setter
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }
}


