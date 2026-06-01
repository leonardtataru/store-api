package com.store.api;

import com.store.api.dto.ChangePriceDTO;
import com.store.api.entity.Journal;
import com.store.api.entity.Product;
import com.store.api.exceptions.NoProductException;
import com.store.api.exceptions.ProductExistException;
import com.store.api.repository.JournalRepository;
import com.store.api.repository.ProductRepository;
import com.store.api.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private JournalRepository journalRepository;

    List<Product> listOfProducts;
    Product product1;
    Product product2;
    Journal journal;

    @BeforeEach
    public void load() {
        product1 = new Product();
        product1.setId(1);
        product1.setName("test1");
        product1.setDescription("description");
        product1.setPrice(12.0);

        product2 = new Product();
        product2.setId(2);
        product2.setName("test2");
        product2.setDescription("description");
        product2.setPrice(12.0);

        journal = new Journal();

        listOfProducts = new ArrayList<>();
        listOfProducts.add(product1);
        listOfProducts.add(product2);
    }

    @Test
    public void createProductTestPass() {
        String productName = "test1";
        when(productRepository.existsByName(productName)).thenReturn(false);
        when(productRepository.save(product1)).thenReturn(product1);

        productService.saveProduct(product1);

        verify(productRepository, times(1)).save(product1);
        verify(journalRepository, times(1)).save(any(Journal.class));
    }

    @Test
    public void createProductTestFail() {
        String productName = "test1";

        when(productRepository.existsByName(productName)).thenReturn(true);

        assertThatThrownBy(() -> productService.saveProduct(product1)).isInstanceOf(ProductExistException.class).hasMessage("Product already exists");
    }

    @Test
    public void getProductByNameTestPass() {
        String productName = "test1";
        journal.setId(1L);
        journal.setProductId(product1.getId());
        journal.setOrderId(1L);

        when(productRepository.findByName(productName)).thenReturn(product1);

        Product productByName = productService.getProductByName(productName);

        assertThat(productByName.getId()).isEqualTo(product1.getId());
    }

    @Test
    public void getProductByNameTestFail() {
        String productName = "test3";

        when(productRepository.findByName(productName)).thenReturn(null);

        assertThatThrownBy(() -> productService.getProductByName(productName)).isInstanceOf(NoProductException.class).hasMessage("No product found with name " + productName);
    }

    @Test
    public void getListOfProductsPass() {
        when(productRepository.findAll()).thenReturn(listOfProducts);

        List<Product> list = productService.getAllProducts();

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(listOfProducts.size());

        verify(productRepository).findAll();
    }

    @Test
    public void changeProductPriceByIdPass() {
        ChangePriceDTO changePriceDTO = new ChangePriceDTO();
        changePriceDTO.setProductId(1L);
        changePriceDTO.setPrice(11.0);

        when(productRepository.existsById(changePriceDTO.getProductId())).thenReturn(true);

        productService.changePriceById(changePriceDTO);

        verify(productRepository, times(1)).changePriceById(changePriceDTO.getProductId(), changePriceDTO.getPrice());
        verify(journalRepository, times(1)).save(any(Journal.class));
    }

    @Test
    public void changeProductPriceByIdFailByNoProductId() {
        ChangePriceDTO changePriceDTO = new ChangePriceDTO();
        changePriceDTO.setProductId(1L);
        changePriceDTO.setPrice(11.0);
        when(productRepository.existsById(any())).thenReturn(false);

        assertThatThrownBy(() -> productService.changePriceById(changePriceDTO)).isInstanceOf(NoProductException.class).hasMessage("No product found with id " + changePriceDTO.getProductId());
    }

    @Test
    public void softDeleteProductPass() {
        Product softDeleteProduct;
        softDeleteProduct = product1;
        softDeleteProduct.setDeleted(true);

        when(productRepository.findById(product1.getId())).thenReturn(Optional.of(softDeleteProduct));

        productService.softDeleteProduct(product1.getId());

        verify(productRepository, times(1)).softDelete(product1.getId());
        verify(journalRepository, times(1)).save(any(Journal.class));
    }

    @Test
    public void softDeleteProductFail() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.softDeleteProduct(1L)).isInstanceOf(NoProductException.class).hasMessage("No product found with id " + 1);
    }
}
