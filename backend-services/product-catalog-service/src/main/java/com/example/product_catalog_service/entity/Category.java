package com.example.product_catalog_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Category extends BaseModel {
    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> productList;
}