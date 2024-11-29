package com.triet.spring_commerce.Repository;

import com.triet.spring_commerce.Entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color,Long> {
}
