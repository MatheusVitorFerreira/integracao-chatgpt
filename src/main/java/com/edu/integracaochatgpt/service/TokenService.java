package com.edu.integracaochatgpt.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.edu.integracaochatgpt.domain.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenService {

    private static final String SECRET = "segredinho";
    private static final long EXPIRATION_TIME_MINUTES = 120;

    public TokenResponse generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);

            Instant expirationInstant = Instant.now().plus(EXPIRATION_TIME_MINUTES, ChronoUnit.MINUTES);

            String token = JWT.create()
                    .withIssuer("auth")
                    .withSubject(user.getUsername())
                    .withExpiresAt(Date.from(expirationInstant))
                    .sign(algorithm);

            return new TokenResponse(token, Long.toString(EXPIRATION_TIME_MINUTES));
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public static class TokenResponse {
        private final String token;
        private final String expiration;

        public TokenResponse(String token, String expiration) {
            this.token = token;
            this.expiration = expiration;
        }

        public String getToken() {
            return token;
        }

        public String getExpiration() {
            return expiration;
        }
    }
}

