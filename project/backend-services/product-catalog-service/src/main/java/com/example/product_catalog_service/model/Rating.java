package com.example.product_catalog_service.model;

import lombok.Data;

@Data
public class Rating {
    private Float rate;
    private Integer count;
}