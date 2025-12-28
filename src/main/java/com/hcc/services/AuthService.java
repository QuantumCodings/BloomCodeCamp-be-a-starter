package com.hcc.services;

import com.hcc.dto.AuthCredentialsRequest;
import com.hcc.entities.User;
import com.hcc.repositories.UserRepository;
import com.hcc.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(AuthCredentialsRequest request) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Retrieve user
            Optional<User> userOpt = userRepo.findByUsername(request.getUsername());
            User user = userOpt.orElseThrow(() -> new RuntimeException("User not found"));

            // Generate JWT
            return jwtUtil.generateToken(user);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid Credentials");
        }
    }

    public boolean validateToken(String token) {
        Optional<User> userOpt = userRepo.findByUsername(jwtUtil.getUsernameFromToken(token));
        return userOpt.map(user -> jwtUtil.validateToken(token, user)).orElse(false);
    }
}
