package com.store.api.utils;

import com.store.api.entity.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtil {

    private final String key;

    public JwtUtil(@Value("${key}") String key) {
        this.key = key;
    }

    public String generateToken(Optional<User> user) {

        return Jwts.builder()
                .id(String.valueOf(user.get().getId()))
                .claim("role", user.get().getRole_id())
                .subject(user.get().getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }


    public Integer extractRole(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", Integer.class);
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (JwtException e) {
            throw new RuntimeException("Expired or invalid JWT token");
        }
    }
}
