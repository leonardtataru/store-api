package com.store.api.service;

import com.store.api.dto.ChangePriceDTO;
import com.store.api.entity.Journal;
import com.store.api.entity.Product;
import com.store.api.exceptions.NoProductException;
import com.store.api.exceptions.ProductExistException;
import com.store.api.repository.JournalRepository;
import com.store.api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final JournalRepository journalRepository;

    ProductService(ProductRepository productRepository, JournalRepository journalRepository) {
        this.productRepository = productRepository;
        this.journalRepository = journalRepository;
    }

    @Transactional
    public void saveProduct(Product product) {
        log.info("Saving product {}", product);
        boolean exists = productRepository.existsByName(product.getName());
        if (exists) {
            log.error("Product with name {} already exists", product.getName());
            throw new ProductExistException("Product already exists");
        }
        log.debug("Saving product {}", product);
        productRepository.save(product);
        Journal journal = new Journal();
        journal.setProductId(product.getId());
        journal.setOrderId(1L);
        createJournal(journal);
    }

    public Product getProductByName(String name) {
        log.debug("Getting product by name {}", name);
        Product productByName = productRepository.findByName(name);
        if (productByName == null) {
            log.error("Product with name {} not found", name);
            throw new NoProductException("No product found with name " + name);
        }
        return productByName;
    }

    public Optional<Product> getProductById(Long id) {
        log.debug("Getting product by id {}", id);
        Optional<Product> productById = productRepository.findById(id);
        if (!productById.isPresent()) {
            log.error("Product with id {} not found", id);
            throw new NoProductException("No product found with id " + id);
        }
        return productById;
    }

    public List<Product> getAllProducts() {
        log.debug("Getting all products");
        return productRepository.findAll();
    }

    @Transactional
    public void softDeleteProduct(Long id) {
        log.debug("Soft deleting product {}", id);
        Optional<Product> productById = productRepository.findById(id);
        if (productById.isEmpty()) {
            log.error("Product with id {} not found", id);
            throw new NoProductException("No product found with id " + id);
        }
        log.debug("Soft deleting product {}", id);
        productRepository.softDelete(productById.get().getId());
        Journal journal = new Journal();
        journal.setProductId(id);
        journal.setOrderId(3L);
        createJournal(journal);
    }

    @Transactional
    public void deleteProductById(Long id) {
        log.debug("Deleting product {}", id);
        boolean exists = productRepository.existsById(id);
        if (!exists) {
            log.error("Product with id {} not found", id);
            throw new NoProductException("No product found with id " + id);
        }
        log.debug("Soft deleting product {}", id);
        productRepository.softDelete(id);
        log.debug("Deleting product {}", id);
        productRepository.deleteById(id);
    }

    @Transactional
    public void changePriceById(ChangePriceDTO changePriceDTO) {
        log.debug("Changing price {}", changePriceDTO);
        boolean exists = productRepository.existsById(changePriceDTO.getProductId());
        if (!exists) {
            log.error("Product with id {} not found", changePriceDTO.getProductId());
            throw new NoProductException("No product found with id " + changePriceDTO.getProductId());
        }
        log.debug("Changing price {}", changePriceDTO);
        productRepository.changePriceById(changePriceDTO.getProductId(), changePriceDTO.getPrice());
        Journal journal = new Journal();
        journal.setProductId(changePriceDTO.getProductId());
        journal.setOrderId(2L);
        createJournal(journal);
    }

    public void createJournal(Journal order) {
        log.debug("Creating journal {}", order);
        journalRepository.save(order);
    }
}
