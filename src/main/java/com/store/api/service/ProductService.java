package com.store.api.service;

import com.store.api.dto.ChangePriceDTO;
import com.store.api.entity.Product;
import com.store.api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void saveProduct(Product product) {
        boolean exists = productRepository.existsByName(product.getName());
        if (exists) {
            throw new RuntimeException("Product already exists");
        }
        productRepository.save(product);
    }

    public Product getProductByName(String name) {
        Product productByName = productRepository.findByName(name);
        if (productByName == null) {
            throw new RuntimeException("No product found with name " + name);
        }
        return productByName;
    }

    public Optional<Product> getProductById(Long id) {
        Optional<Product> productById = productRepository.findById(id);
        if (!productById.isPresent()) {
            throw new RuntimeException("No product found with id " + id);
        }
        return productById;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void changePriceById(ChangePriceDTO changePriceDTO) {
        productRepository.changePriceById(changePriceDTO.getProductId(), changePriceDTO.getPrice());
    }
}
