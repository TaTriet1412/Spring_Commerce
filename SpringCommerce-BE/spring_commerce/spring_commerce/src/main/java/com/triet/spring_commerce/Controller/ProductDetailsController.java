package com.triet.spring_commerce.Controller;

import com.triet.spring_commerce.Entity.Product;
import com.triet.spring_commerce.Entity.ProductDetails;
import com.triet.spring_commerce.Service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/product_details")
@Controller
public class ProductDetailsController {
    @Autowired
    private ProductDetailsService productDetailsService;

    @GetMapping
    public ResponseEntity<List<ProductDetails>> getProductDetails(){
        List<ProductDetails> productDetails = productDetailsService.getProductDetails();
        return new ResponseEntity<>(productDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetails> getProductById(@PathVariable Long id){
        ProductDetails productDetails = productDetailsService.getProductDetailsById(id);
        return new ResponseEntity<>(productDetails, HttpStatus.OK);
    }
}
