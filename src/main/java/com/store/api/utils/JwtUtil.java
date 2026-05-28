package com.store.api.utils;

import com.store.api.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
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

        Key signingKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .id(String.valueOf(user.get().getId()))
                .claim("role", user.get().getRole())
                .issuedAt(Date.from(Instant.now()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
