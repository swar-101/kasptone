package org.swar;

import org.swar.secret.SecretGenerator;

import java.security.SecureRandom;

public class SecretGenerator4Nxt implements SecretGenerator {
    @Override
    public String generateSecret() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32]; // 256 bits
        random.nextBytes(bytes);
        String secret = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
         return secret;
    }
}
