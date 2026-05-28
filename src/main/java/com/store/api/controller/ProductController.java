package com.store.api.controller;

import com.store.api.dto.ChangePriceDTO;
import com.store.api.entity.Product;
import com.store.api.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public void addProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @GetMapping("/search")
    public Product getProductByName(@RequestParam String name) {
       return productService.getProductByName(name);
    }

    @GetMapping("/search/{id:\\d+}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/{id}")
    public void deleteProductByName(@PathVariable long id) {
        productService.deleteProductById(id);
    }

    @PutMapping("/changePrice")
    public void changePrice(@RequestBody ChangePriceDTO changePriceDTO) {
        productService.changePriceById(changePriceDTO);
    }
}
