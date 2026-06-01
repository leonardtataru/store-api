package com.store.api.service;

import com.store.api.dto.ChangePriceDTO;
import com.store.api.entity.Journal;
import com.store.api.entity.Product;
import com.store.api.exceptions.NoProductException;
import com.store.api.exceptions.ProductExistException;
import com.store.api.repository.JournalRepository;
import com.store.api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final JournalRepository orderRepository;

    ProductService(ProductRepository productRepository, JournalRepository journalRepository) {
        this.productRepository = productRepository;
        this.orderRepository = journalRepository;
    }

    @Transactional
    public void saveProduct(Product product) {
        boolean exists = productRepository.existsByName(product.getName());
        if (exists) {
            throw new ProductExistException("Product already exists");
        }
        productRepository.save(product);
        Journal journal = new Journal();
        journal.setProductId(product.getId());
        journal.setOrderId(1L);
        createJournal(journal);
    }

    public Product getProductByName(String name) {
        Product productByName = productRepository.findByName(name);
        if (productByName == null) {
            throw new NoProductException("No product found with name " + name);
        }
        return productByName;
    }

    public Optional<Product> getProductById(Long id) {
        Optional<Product> productById = productRepository.findById(id);
        if (!productById.isPresent()) {
            throw new NoProductException("No product found with id " + id);
        }
        return productById;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public void softDeleteProduct(Long id) {
        Optional<Product> productById = productRepository.findById(id);
        if (productById.isEmpty()) {
            throw new NoProductException("No product found with id " + id);
        }
        productRepository.softDelete(productById.get().getId());
        Journal journal = new Journal();
        journal.setProductId(id);
        journal.setOrderId(3L);
        createJournal(journal);
    }

    @Transactional
    public void deleteProductById(Long id) {
        boolean exists = productRepository.existsById(id);
        if (!exists) {
            throw new NoProductException("No product found with id " + id);
        }
        productRepository.softDelete(id);
        productRepository.deleteById(id);
    }

    @Transactional
    public void changePriceById(ChangePriceDTO changePriceDTO) {
        boolean exists = productRepository.existsById(changePriceDTO.getProductId());
        if (!exists) {
            throw new NoProductException("No product found with id " + changePriceDTO.getProductId());
        }
        productRepository.changePriceById(changePriceDTO.getProductId(), changePriceDTO.getPrice());
        Journal journal = new Journal();
        journal.setProductId(changePriceDTO.getProductId());
        journal.setOrderId(2L);
        createJournal(journal);
    }

    private void createJournal(Journal order) {
        orderRepository.save(order);
    }
}
