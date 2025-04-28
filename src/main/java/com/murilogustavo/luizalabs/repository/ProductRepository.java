package com.murilogustavo.luizalabs.repository;

import com.murilogustavo.luizalabs.model.Client;
import com.murilogustavo.luizalabs.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByClientAndProductId(Client client, String productId);
    List<Product> findByClient(Client client);
    void deleteByClientAndProductId(Client client, String productId);
}
