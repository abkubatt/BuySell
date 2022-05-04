package com.example.buysell.Services;

import com.example.buysell.Models.Product;
import com.example.buysell.Repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> listProducts(String title){
        if(title != null)
            return productRepository.findAllByTitle(title);
        return productRepository.findAll();
    }

    public void saveProduct(Product product){
        log.info("Saving new {}", product);
        productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
