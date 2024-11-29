package com.triet.spring_commerce.Repository;

import com.triet.spring_commerce.Entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails,Long> {
}
