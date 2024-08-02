package org.swar.secret.secret4nxt;

import lombok.extern.log4j.Log4j2;
import org.swar.secret.SecretGenerator;

import java.security.SecureRandom;
import java.util.Base64;

@Log4j2
public class SecretGenerator4Nxt implements SecretGenerator {
    @Override
    public String generateSecret() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32]; // 256 bits
        random.nextBytes(bytes);
        String secret = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        log.info("Generated secret: {}", secret);

        return secret;
    }
}