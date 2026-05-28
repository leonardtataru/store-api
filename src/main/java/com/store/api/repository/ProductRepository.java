package com.store.api.repository;

import com.store.api.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.name like %:name%")
    Product findByName(@Param("name") String name);

    @Modifying
    @Query("update Product p set p.price = ?2 where p.id = ?1")
    void changePriceById(long id, double price);

    boolean existsByName(String name);
}
