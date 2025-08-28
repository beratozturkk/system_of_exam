package com.beratozturk.SystemOfExam.dto;

// record = sade, immutable DTO yazmanın kısa yolu
public record AuthResponse(
        String token,     // JWT token (frontend her istekte bunu gönderecek)
        String userName,  // kullanıcı adı (ekranda göstermek için)
        String role       // STUDENT / TEACHER / ADMIN (yetki için)
) {}
