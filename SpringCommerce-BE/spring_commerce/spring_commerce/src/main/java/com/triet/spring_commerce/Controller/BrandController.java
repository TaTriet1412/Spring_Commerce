package com.triet.spring_commerce.Controller;

import com.triet.spring_commerce.Entity.Brand;
import com.triet.spring_commerce.Entity.Category;
import com.triet.spring_commerce.Service.BrandService;
import com.triet.spring_commerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/brands")
@Controller
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<List<Brand>> getBrands(){
        List<Brand> brands = brandService.getBrands();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }
}
