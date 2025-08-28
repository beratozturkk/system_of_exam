package com.beratozturk.SystemOfExam.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    // Gerçekte .env / application.properties'ten gelmeli.
    // En az 256-bit uzunlukta olmalı (32+ karakter).
    private final String secret = "DEGISTIR-BU-COOOK-UZUN-GUVENLI-BIR-SECRET-KEY-OLMALI-123456";

    // Token üretir: içine subject=userName ve claim=role koyar.
    public String generate(String userName, String role) {
        var key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(userName)              // token sahibinin kim olduğu
                .claim("role", role)               // rol bilgisini ekledik
                .setIssuedAt(new Date())           // oluşturulma zamanı
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 saat geçerli
                .signWith(key, SignatureAlgorithm.HS256) // HS256 ile imzala
                .compact();                         // token string'i üret
    }

    // Token'ı doğrular ve içindeki claim'leri döndürür.
    public Jws<Claims> parse(String token) {
        var key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)     // aynı secret ile doğrula
                .build()
                .parseClaimsJws(token); // geçersizse exception fırlatır
    }

    // Token'dan username'i çıkarır
    public String extractUsername(String token) {
        try {
            Jws<Claims> claims = parse(token);
            return claims.getBody().getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Geçersiz token");
        }
    }
}