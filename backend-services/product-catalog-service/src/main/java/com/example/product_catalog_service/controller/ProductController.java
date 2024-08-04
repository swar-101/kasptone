package com.example.product_catalog_service.controller;

import com.example.product_catalog_service.dto.ProductDTO;
import com.example.product_catalog_service.service.FakeStoreService;
import com.example.product_catalog_service.service.NxtStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.product_catalog_service.controller.ControllerAdvisor.handleExceptions;

@RestController
@RequestMapping("/fakestore/products")
public class ProductController {

    public FakeStoreService fakestoreService;

    @Autowired
    public ProductController(FakeStoreService fakestoreService, NxtStoreService nxtStoreService) {
        this.fakestoreService = fakestoreService;
    }

    @GetMapping("/all/prim")
    public ProductDTO[] getAllProductsInPrim() {
        return fakestoreService.getAllProductsInPrim();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(Long productId) {
        try {
            if (productId <= 0)
                throw new IllegalArgumentException("invalid productId");

            List<ProductDTO> response = fakestoreService.getAllProducts();
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("called by", "swar");
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return handleExceptions(ex);
        }
    }
}