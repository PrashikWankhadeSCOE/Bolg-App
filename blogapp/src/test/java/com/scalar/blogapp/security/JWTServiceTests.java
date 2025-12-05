package com.scalar.blogapp.security;

import com.auth0.jwt.JWT;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JWTServiceTests {

    JWTService jwtService = new JWTService();

    @Test
    void canCreateJwtFromUserID(){
        var jwt = jwtService.createJWT(1001L);

        assertNotNull(jwt);
    }
}
