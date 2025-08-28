package com.beratozturk.SystemOfExam.Controller;

import com.beratozturk.SystemOfExam.Model.User;
import com.beratozturk.SystemOfExam.Service.JwtService;
import com.beratozturk.SystemOfExam.Service.UserService;
import com.beratozturk.SystemOfExam.dto.AuthResponse;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    // POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User req) {
        try {
            User user = userService.register(req);
            String token = jwtService.generate(user.getUserName(), user.getRole().name());
            return ResponseEntity.ok(new AuthResponse(token, user.getUserName(), user.getRole().name()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public AuthResponse login(@RequestBody User req) {
        // 1) Kullanıcı adı/şifre kontrolü (UserService.login zaten yazmıştık)
        User user = userService.login(req.getUserName(), req.getPassword());

        // 2) Token üret
        String token = jwtService.generate(user.getUserName(), user.getRole().name());

        // 3) DTO ile token + rol + userName döndür
        return new AuthResponse(token, user.getUserName(), user.getRole().name());
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));
        }
        return ResponseEntity.ok(Map.of(
                "userName", auth.getName(),
                "roles", auth.getAuthorities().toString()
        ));
    }
}
