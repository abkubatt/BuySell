package com.example.buysell.Services;

import com.example.buysell.Models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private List<Product> products = new ArrayList<>();
    private long ID = 0;
    {
        products.add(new Product(++ID,"Playstation 5 ", "Simple description",55000,"Bishkek","Abdulmajit"));
        products.add(new Product(++ID,"iPhone 8 ", "Simple description",20000,"Osh","Nurakhmat"));

    }

    public List<Product> list(){
        return products;
    }

    public void saveProduct(Product product){
        product.setId(++ID);
        products.add(product);
    }

    public void deleteProduct(Long id){
        products.removeIf(product -> product.getId().equals(id));
    }

    public Product getProductById(Long id) {
        for (Product product : products) {
            if (product.getId().equals(id))
                return product;
        }
        return null;
    }
}
