package com.triet.spring_commerce.Service;

import com.triet.spring_commerce.Entity.Brand;
import com.triet.spring_commerce.Entity.Product;
import com.triet.spring_commerce.Repository.BrandRepository;
import com.triet.spring_commerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getBrands(){
        return brandRepository.findAll();
    }

    public Brand getBrandById(Long id){
        return brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy thương hiệu này"));
    }
}
