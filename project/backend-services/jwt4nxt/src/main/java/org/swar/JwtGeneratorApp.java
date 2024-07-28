package org.swar.jwt;

import lombok.extern.log4j.Log4j2;
import org.swar.jwt.jwt4nxt.JwtGenerator4Nxt;

/**
 * Hello world!
 *
 */
@Log4j2
public class JwtGeneratorApp
{
    public static void main( String[] args )
    {

        System.out.println( "[JwtGenerator][main] JWT Generation initiated" );
        JwtGenerator jwtGenerator = new JwtGenerator4Nxt();

        jwtGenerator.generateJWT();
    }
}