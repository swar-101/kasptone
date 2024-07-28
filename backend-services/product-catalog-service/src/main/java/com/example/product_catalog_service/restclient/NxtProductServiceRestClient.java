package com.example.product_catalog_service.restclient;

import com.example.product_catalog_service.client.NxtProductServiceClient;
import com.example.product_catalog_service.dto.UserDTO;
import com.example.product_catalog_service.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NxtProductServiceRestClient implements NxtProductServiceClient {

    private final RestTemplate restTemplate;

    public NxtProductServiceRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductBasedOnUserScope(Long productId, Long userId) {
        Product product = new Product();
        UserDTO userDTO = restTemplate.getForEntity("http://userservice/users", UserDTO.class, userId).getBody();
        System.out.println(userDTO.getEmail());
        if(userDTO == null)
            return null;

        return product;
    }

}
