package com.example.product_catalog_service.mapper;

import com.example.product_catalog_service.dto.ProductDTO;
import com.example.product_catalog_service.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product mapToEntity(ProductDTO productDTO);
    ProductDTO mapToDTO(Product product);
}
