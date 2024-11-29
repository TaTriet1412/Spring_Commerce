package com.triet.spring_commerce.Entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "color")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;
}
