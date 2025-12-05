package com.scalar.blogapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {


    //TODO : Move the key  in the diff .properties file which should not be updated on git
    private static final String JWT_KEY = "alfjkasfhafuei03984safjjw894";
    private Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);

    public String createJWT(Long userId){
        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
//                .withExpiresAt(new Date()) // TODO : Setup Expiry parameter
                .sign(algorithm);
    }

    private Long retrieveUserId(String jwtString){
        var decodedJWT = JWT.decode(jwtString);
        var userId = Long.valueOf(decodedJWT.getSubject());
        return userId;
    }
}
