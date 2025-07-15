package com.SmartMarket.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JWT (JSON Web Token) yaratish va tekshirish uchun klass
 * Tokenlar bilan ishlash uchun asosiy metodlarni taqdim etadi
 */
@Component
public class JwtTokenProvider {

    // Token uchun maxfiy kalit (avtomatik yaratiladi)
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // Token amal qilish muddati (1 soat)
    @Value("${jwt.expiration}")
    private long validityInMilliseconds; 

    /**
     * Yangi JWT token yaratish
     * @param username - foydalanuvchi nomi (token subyekti)
     * @param role - foydalanuvchi roli
     * @return - yaratilgan token stringi
     */
    public String createToken(String username, String role,int storeId) {
        // Token claims (payload) qismini yaratish
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        claims.put("storeId", storeId);

        // Token yaratilgan va amal qilish vaqtlari
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMilliseconds);

        // Tokenni qurish va imzolash
        return Jwts.builder()
                .setClaims(claims)             // Ma'lumotlar
                .setIssuedAt(now)             // Yaralgan vaqt
                .setExpiration(expiry)         // Amal qilish muddati
                .signWith(secretKey)           // Imzolash
                .compact();                   // Yakuniy token
    }

    /**
     * Tokendan foydalanuvchi nomini olish
     * @param token - JWT token
     * @return - foydalanuvchi nomi
     */
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(secretKey)   // Kalitni o'rnatish
                   .build()
                   .parseClaimsJws(token)      // Tokenni tahlil qilish
                   .getBody()
                   .getSubject();              // Foydalanuvchi nomini olish
    }
    
    public int getStoreId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object storeIdObj = claims.get("storeId");

        if (storeIdObj instanceof Integer) {
            return (Integer) storeIdObj;
        } else if (storeIdObj instanceof Number) {
            return ((Number) storeIdObj).intValue();
        } else if (storeIdObj instanceof String) {
            return Integer.parseInt((String) storeIdObj);
        }

        throw new IllegalArgumentException("Token içinde geçersiz storeId değeri.");
    }


    /**
     * Tokenni tekshirish
     * @param token - tekshiriladigan JWT token
     * @return - token yaroqli bo'lsa true, aks holda false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)     // Kalitni o'rnatish
                .build()
                .parseClaimsJws(token);      // Tokenni tekshirish
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token yaroqsiz yoki muddati tugagan
            return false;
        }
    }
}