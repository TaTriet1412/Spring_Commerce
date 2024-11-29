package com.triet.spring_commerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private LocalDateTime created_at;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id") // tên cột sẽ lưu id của ProductDetail
    private User user;

    // Getter và Setter
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    @PrePersist protected void onCreate() {
        if(created_at == null){
            created_at = LocalDateTime.now();
        }
    }
}
