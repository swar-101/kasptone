package com.example.product_catalog_service.util;

public class Constants {

    private Constants() throws IllegalAccessException {
        throw new IllegalAccessException(UTILITY_CLASS_MSG);
    }

    public static final String UTILITY_CLASS_MSG = "This is a utility class";
    public static final String PRODUCT_CREATED_MSG = "Product entry created successfully";
    public static final String PRODUCT_NOT_FOUND_MSG = "Product not found";

}
