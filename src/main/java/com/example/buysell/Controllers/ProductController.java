package com.example.buysell.Controllers;

import com.example.buysell.Models.Product;
import com.example.buysell.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false) String title, Model model){
        model.addAttribute("products",productService.listProducts(title));
        return "products";
    }

    @GetMapping("product/{id}")
    public String productInfo(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(Product product){
        productService.saveProduct(product);
        return "redirect:/";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "redirect:/";
    }


}
