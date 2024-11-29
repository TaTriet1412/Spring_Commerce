package com.triet.spring_commerce.Service;

import com.triet.spring_commerce.Entity.Product;
import com.triet.spring_commerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm này"));
    }
}
