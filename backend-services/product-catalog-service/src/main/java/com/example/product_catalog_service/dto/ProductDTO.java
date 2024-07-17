package com.example.product_catalog_service.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private CategoryDTO category;
}