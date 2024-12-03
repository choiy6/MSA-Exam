package com.sparta.msa_exam.auth.controller;

import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signIn")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody SignInRequest signInRequest) {
        String token = authService.signIn(signInRequest.getUsername(), signInRequest.getPassword());

        // JWT를 Response Header에 추가
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(headers).body("Login successful");
    }

    @PostMapping("/signUp")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        User createdUser = authService.signUp(user);
        return ResponseEntity.ok(createdUser);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class AuthResponse {
        private String accessToken;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class SignInRequest {
        private String username;
        private String password;
    }
}
