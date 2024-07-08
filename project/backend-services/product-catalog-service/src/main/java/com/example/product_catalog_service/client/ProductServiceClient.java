package com.example.product_catalog_service.client;

import com.example.product_catalog_service.dto.ProductDTO;

import java.util.List;

public interface ProductServiceClient {

    ProductDTO[] getAllProductsInPrim();

    List<ProductDTO> getAllProducts();

}