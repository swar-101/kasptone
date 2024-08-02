package org.swar;

import lombok.extern.log4j.Log4j2;
import org.swar.jwt.JwtGenerator;
import org.swar.jwt.jwt4nxt.JwtGenerator4Nxt;
import org.swar.secret.SecretGenerator;
import org.swar.secret.secret4nxt.SecretGenerator4Nxt;

/**
 * Hello world!
 *
 */
@Log4j2
public class JwtGeneratorApp
{
    public static void main( String[] args )
    {

        log.info( "[JwtGenerator][main] JWT Generation initiated" );
        JwtGenerator jwtGenerator = new JwtGenerator4Nxt();

        jwtGenerator.generateJWT();

        SecretGenerator secretGenerator = new SecretGenerator4Nxt();
        secretGenerator.generateSecret();
    }
}