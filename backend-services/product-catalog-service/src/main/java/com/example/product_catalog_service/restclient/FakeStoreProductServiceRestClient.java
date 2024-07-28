package com.example.product_catalog_service.restclient;

import com.example.product_catalog_service.client.ProductServiceClient;
import com.example.product_catalog_service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductServiceRestClient implements ProductServiceClient {

    public RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductServiceRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProductDTO[] getAllProductsInPrim() {
        ResponseEntity<ProductDTO[]> response = restTemplate
                .getForEntity("https://fakestoreapi.com/products", ProductDTO[].class);

        return response.getBody();
    }

    public List<ProductDTO> getAllProducts() {
        ResponseEntity<List<ProductDTO>> response = restTemplate
                .exchange("https://fakestoreapi.com/products", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});

        return response.getBody();
    }
}