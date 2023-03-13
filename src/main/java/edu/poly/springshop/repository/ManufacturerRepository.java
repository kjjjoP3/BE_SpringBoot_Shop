package edu.poly.springshop.repository;

import edu.poly.springshop.domain.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    List<Manufacturer> findByNameContainsIgnoreCase(String name);

    List<Manufacturer> findByIdNotAndNameContainsIgnoreCase(Long id, String name);

    Page<Manufacturer> findByNameContainsIgnoreCase(String name, Pageable pageable);
}