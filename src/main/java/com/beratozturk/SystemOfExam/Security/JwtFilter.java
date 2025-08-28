package com.beratozturk.SystemOfExam.Security;

import com.beratozturk.SystemOfExam.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.List;


public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jvtService) {
        this.jwtService = jvtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization"); // 1) Header'ı al
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);             // "Bearer " sonrası token
            try {
                var claims = jwtService.parse(token).getBody(); // 2) Token'ı doğrula/çöz
                String userName = claims.getSubject();          // subject = userName
                String role = (String) claims.get("role");      // claim: role

                // 3) Spring Security context'e kullanıcıyı ve rolünü koy
                var auth = new UsernamePasswordAuthenticationToken(
                        userName, // principal
                        null,     // credentials (yok)
                        List.of(new SimpleGrantedAuthority("ROLE_" + role)) // Yetki formatı böyle
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ignored) {
                // Token hatalı/expired ise görmezden geliriz; endpoint yetkisi zaten engeller.
            }
        }

        // 4) Zincire devam
        chain.doFilter(request, response);
    }


}
