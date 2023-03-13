package edu.poly.springshop.repository;

import edu.poly.springshop.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}