package com.hypermarket.api_gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

// ده Class للتعامل مع JWT Token
@Component
public class JwtUtil {
    
    // ده الـ Secret Key (لازم يكون نفسه في User Service)
    private static final String SECRET_KEY = "MySecretKeyForHypermarketSystemApplicationVeryLongKey123456";
    
    // Method تجيب الـ Claims من الـ Token (Username, Role, etc)
    public Claims getClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    // Method تتأكد إن الـ Token صحيح
    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}