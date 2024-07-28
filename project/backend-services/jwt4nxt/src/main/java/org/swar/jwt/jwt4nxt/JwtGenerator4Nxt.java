package org.swar.jwt4nxt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

import java.security.Key;
import java.util.Date;

@Log4j2
public class JwtGenerator4Nxt implements JwtGenerator {

    @Override
    public String generateJWT() {
        // Define the secret key (same as the one you set up in Kong)
        String secret = "sYwYyLGIMJERQHsFoZXsdCIkvreJJ4Xv";
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        // Define the claims
        String jwt = Jwts.builder()
                .setIssuer("AGmgMirt4lxhq4M9VgApFyGwwG7a0i8m") // Key
                .setSubject("1234567890")
                .setAudience("product-catalog-service")
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiration
                .setIssuedAt(new Date())
                .claim("name", "John Doe")
                .claim("admin", true)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        log.info("Generated JWT: {}", jwt);

        return jwt;
    }
}