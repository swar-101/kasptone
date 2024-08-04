package com.example.product_catalog_service.service;

import com.example.product_catalog_service.dto.Ack;
import com.example.product_catalog_service.dto.ProductDTO;
import com.example.product_catalog_service.entity.Product;
import com.example.product_catalog_service.entity.State;
import com.example.product_catalog_service.mapper.ProductMapper;
import com.example.product_catalog_service.repo.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.product_catalog_service.util.Constants.PRODUCT_CREATED_MSG;
import static com.example.product_catalog_service.util.Constants.PRODUCT_NOT_FOUND_MSG;

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

    /**
     * Creates a new product from the provided {@link ProductDTO}.
     *
     * Maps the DTO to a {@link Product} entity, sets base values, saves it to the repository,
     * and returns an {@link Ack} indicating the result of the operation.
     *
     * @param productDTO The product details to create.
     * @return An {@link Ack} with the result of the operation.
     *
     * @throws IllegalArgumentException if the {@link ProductDTO} is invalid.
     * @throws DataAccessException if there is a database access issue.
     */
    public Ack createProduct(ProductDTO productDTO) {
        Product product = productMapper.mapToEntity(productDTO);

        setBaseValues(product);
        productRepository.save(product);

        return ackProductCreated(product);
    }

    /**
     *
     * @param productId ID of the Product
     * @return
     */
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


        // Custom Product Not Found
        throw new NullPointerException(PRODUCT_NOT_FOUND_MSG);
    }

    public ProductDTO getProductByName(String name) {
        List<Product> productList = productRepository.findByName(name);
        return null;
    }

    private Ack ackProductCreated(Product product) {
        Ack ack = new Ack();
        ack.setId(product.getId());
        ack.setMessage(PRODUCT_CREATED_MSG);
        return ack;
    }

    private void setBaseValues(Product product) {
        product.setCreatedAt(new Date());
        product.setLastUpdatedAt(new Date());
        product.setState(State.ACTIVE);
    }
}