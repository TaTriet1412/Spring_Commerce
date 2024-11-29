package com.triet.spring_commerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String phone;

    @Column
    private LocalDate birthday;

    @Column
    private LocalDateTime date_begin;

    @Column
    private boolean gender;

    @PrePersist protected void onCreate() {
        if (date_begin == null) {
            date_begin = LocalDateTime.now();
        }
    }

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY) // liên kết với Product, để bi-directional relationship
    @JsonIgnore
    private Cart cart;
}
