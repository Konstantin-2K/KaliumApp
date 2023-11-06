package com.Kalium.repo;

import com.Kalium.model.productEntities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Product findByName(String name);
}
