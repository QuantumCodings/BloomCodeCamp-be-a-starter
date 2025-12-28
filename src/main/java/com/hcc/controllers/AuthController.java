package com.hcc.controllers;

import com.hcc.dto.AuthCredentialsRequest;
import com.hcc.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthCredentialsRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam("token") String token) {
        boolean isValid = authService.validateToken(token);
        return ResponseEntity.ok().body(isValid);
    }
}