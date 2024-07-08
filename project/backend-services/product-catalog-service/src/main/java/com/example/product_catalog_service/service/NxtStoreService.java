package com.example.product_catalog_service.service;

import com.example.product_catalog_service.dto.Ack;
import com.example.product_catalog_service.dto.ProductDTO;
import com.example.product_catalog_service.entity.Product;
import com.example.product_catalog_service.mapper.ProductMapper;
import com.example.product_catalog_service.repo.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class NxtStoreService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public NxtStoreService(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    public Ack createProduct(ProductDTO productDTO) {
        Product product = productMapper.mapToEntity(productDTO);
        productRepository.save(product);

        Ack ack = new Ack();
        ack.setId(product.getId());
        ack.setMessage("Successfully created product");

        return ack;
    }

    public ProductDTO getProductById(String productId) {
        Optional<Product> optionalProduct = productRepository.findById(1L);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            ProductDTO productDTO = productMapper.mapToDTO(product);
            return productDTO;
        }

        // Alternatively, we can use a declarative style code:
/*
*       optionalProduct.ifPresentOrElse();
*
*
* */


        throw new NullPointerException("The requested product was not found.");
    }

    public ProductDTO getProductByName(String name) {
        List<Product> productList = productRepository.findByName(name);
        return null;
    }
}