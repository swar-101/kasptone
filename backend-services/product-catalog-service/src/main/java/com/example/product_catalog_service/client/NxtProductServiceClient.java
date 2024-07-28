package com.example.product_catalog_service.client;

import com.example.product_catalog_service.entity.Product;

public interface NxtProductServiceClient {

    Product getProductBasedOnUserScope(Long productId, Long userId);
}
