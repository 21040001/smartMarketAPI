package com.SmartMarket.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    // application.properties faylidan o'qiydigan qiymatlar
    @Value("${jwt.secret}") // Maxfiy kalit (kamida 32 belgidan iborat bo'lishi kerak)
    private String secret;

    @Value("${jwt.expiration}") // Token amal qilish muddati (millisekundlarda)
    private long expiration;

    private Key key; // Shifrlash uchun kalit

    // Dastur ishga tushganda kalitni yaratish
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Yangi JWT token yaratish
     * @param username - foydalanuvchi nomi
     * @param roles - foydalanuvchi rollari ro'yxati
     * @return - yaratilgan token stringi
     */
    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)  // Foydalanuvchi identifikatori
                .claim("roles", roles) // Foydalanuvchi rollari
                .setIssuedAt(new Date())  // Yaralgan vaqt
                .setExpiration(new Date(System.currentTimeMillis() + expiration))  // Amal qilish muddati
                .signWith(key, SignatureAlgorithm.HS256)  // Kalit bilan imzolash
                .compact();  // Yakuniy tokenni olish
    }

    /**
     * Tokenning to'g'riligini tekshirish
     * @param token - tekshiriladigan token
     * @return - to'g'ri bo'lsa true, aks holda false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)  // Kalit yordamida tekshirish
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.err.println("JWT tekshiruv xatosi: " + e.getMessage());
            return false;
        }
    }

    /**
     * Tokendan foydalanuvchi nomini olish
     * @param token - JWT token
     * @return - foydalanuvchi nomi
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();  // Foydalanuvchi nomi
    }

    /**
     * Tokendan foydalanuvchi rollarini olish
     * @param token - JWT token
     * @return - rollar ro'yxati (Spring Security uchun)
     */
    public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        @SuppressWarnings("unchecked")
        List<String> roles = claims.get("roles", List.class);  // Rollarni olish

        // Spring Security talablariga moslashtirish
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }
}