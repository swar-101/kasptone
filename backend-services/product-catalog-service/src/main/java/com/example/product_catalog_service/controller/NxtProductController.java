package com.example.product_catalog_service.controller;

import com.example.product_catalog_service.dto.Ack;
import com.example.product_catalog_service.dto.ProductDTO;
import com.example.product_catalog_service.service.NxtStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nxt")
public class NxtProductController {

    private final NxtStoreService nxtStoreService;

    @Autowired
    public NxtProductController(NxtStoreService nxtStoreService) {
        this.nxtStoreService = nxtStoreService;
    }

    @PostMapping("/new")
    public ResponseEntity<Ack> createProduct(ProductDTO request) {
        return new ResponseEntity<>(nxtStoreService.createProduct(request), HttpStatus.CREATED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String id) {
        return new ResponseEntity<>(nxtStoreService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping("/nxt/product/")
    public ResponseEntity<ProductDTO> getProductsByName(@RequestParam String name) {
        return new ResponseEntity<>(nxtStoreService.getProductByName(name), HttpStatus.OK);
    }
}