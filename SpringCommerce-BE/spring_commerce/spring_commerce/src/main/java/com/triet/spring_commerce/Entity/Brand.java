package com.triet.spring_commerce.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "brand")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;
}
