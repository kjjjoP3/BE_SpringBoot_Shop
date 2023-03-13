package edu.poly.springshop.repository;

import edu.poly.springshop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainsIgnoreCase(String name, Pageable pageable);
}