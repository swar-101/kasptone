package com.example.product_catalog_service.dto;

import lombok.Data;
import lombok.NonNull;

@Data
@NonNull
public class ProductDTO {
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private CategoryDTO category;
}