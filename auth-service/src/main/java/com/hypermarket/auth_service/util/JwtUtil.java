package com.hypermarket.auth_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// ده بيعمل ويتحقق من JWT Tokens
@Component
public class JwtUtil {
    
    // نفس الـ Secret Key اللي في Gateway (مهم جداً!)
    private static final String SECRET_KEY = "MySecretKeyForHypermarketSystemApplicationVeryLongKey123456";
    private static final long EXPIRATION_TIME = 86400000; // 24 ساعة بالـ milliseconds
    
    // Method تعمل Token جديد
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);  // نضيف الـ role في الـ token
        
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        
        return Jwts.builder()
                .claims(claims)
                .subject(username)  // اسم المستخدم
                .issuedAt(new Date())  // وقت الإنشاء
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // وقت الانتهاء
                .signWith(key)  // التوقيع
                .compact();
    }
    
    // Method تجيب المعلومات من Token
    public Claims getClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}