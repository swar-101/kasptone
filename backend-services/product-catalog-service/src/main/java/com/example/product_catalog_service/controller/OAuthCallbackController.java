package com.example.product_catalog_service.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OAuthCallbackController {

    @GetMapping("/oauth/callback")
    public String handleOAuthCallback(@RequestParam("code") String authorizationCode) {
        String tokenEndpoint = "https://localhost:8443/oauth2/token";
        String clientId = "vQ363WvhHYKCFusfb3g8pr810KkiDXuH";
        String clientSecret = "Hd0d92JSSMyks1rhu43YI1ED4vdXkkIf";
        String redirectUri = "http://192.168.29.77:8080/oauth/callback";

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("grant_type", "authorization_code");
        requestParams.add("code", authorizationCode);
        requestParams.add("redirect_uri", redirectUri);
        requestParams.add("client_id", clientId);
        requestParams.add("client_secret", clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestParams, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenEndpoint, requestEntity, String.class);

        // Extract the access token from the response
        String accessToken = response.getBody();
        return "OAuth access token: " + accessToken;
    }
}
