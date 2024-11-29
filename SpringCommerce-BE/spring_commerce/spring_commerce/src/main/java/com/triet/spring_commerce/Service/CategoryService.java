package com.triet.spring_commerce.Service;

import com.triet.spring_commerce.Entity.Category;
import com.triet.spring_commerce.Entity.Product;
import com.triet.spring_commerce.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy loại sản phẩm này"));
    }
}
