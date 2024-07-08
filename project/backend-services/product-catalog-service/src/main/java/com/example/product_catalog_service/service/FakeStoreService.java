package com.example.product_catalog_service.service;

import com.example.product_catalog_service.client.ProductServiceClient;
import com.example.product_catalog_service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FakeStoreService {

    ProductServiceClient productServiceClient;

    @Autowired
    public FakeStoreService(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    public ProductDTO[] getAllProductsInPrim() {
        return productServiceClient.getAllProductsInPrim();
    }

    public List<ProductDTO> getAllProducts() {
        return productServiceClient.getAllProducts();
    }
}