package com.example.product_catalog_service.repo;

import com.example.product_catalog_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    Optional<Product> findById(Long id);

    @Query("SELECT p FROM Product p WHERE p.name= :name")
    List<Product> findByName(@Param("name") String name);

    
    List<Product> findAllByIsPrime(Boolean isPrime);
}