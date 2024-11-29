package com.triet.spring_commerce.Service;

import com.triet.spring_commerce.Entity.Brand;
import com.triet.spring_commerce.Entity.Color;
import com.triet.spring_commerce.Repository.BrandRepository;
import com.triet.spring_commerce.Repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;

    public List<Color> getColors(){
        return colorRepository.findAll();
    }

    public Color getColorById(Long id){
        return colorRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy màu này"));
    }
}
