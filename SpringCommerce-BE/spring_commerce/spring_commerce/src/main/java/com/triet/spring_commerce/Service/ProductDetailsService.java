package com.triet.spring_commerce.Service;

import com.triet.spring_commerce.Entity.Product;
import com.triet.spring_commerce.Entity.ProductDetails;
import com.triet.spring_commerce.Repository.ProductDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailsService {
    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    public List<ProductDetails> getProductDetails(){
        return productDetailsRepository.findAll();
    }

    public ProductDetails getProductDetailsById(Long id){
        return productDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết sản phẩm này"));
    }
}
