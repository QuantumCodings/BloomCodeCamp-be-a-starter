package com.hcc.controllers;

import com.hcc.entities.Authority;
import com.hcc.entities.User;
import com.hcc.enums.AuthorityEnum;
import com.hcc.exceptions.UsernameNotFoundException;
import com.hcc.model.AuthCredentialRequest;
import com.hcc.model.MessageResponse;
import com.hcc.model.SignUpRequest;
import com.hcc.repositories.AuthorityRepository;
import com.hcc.repositories.UserRepository;
import com.hcc.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthCredentialRequest credentials) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found: " + userDetails.getUsername()));

            String token = jwtUtil.generateToken(user);

            List<String> roles = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            headers.add("Access-Control-Expose-Headers", "Authorization");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(user.getId());

        } catch (AuthenticationException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Authentication failed");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signup(@RequestBody SignUpRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        User user = new User(
                request.getCohortStartDate(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );

        Set<Authority> authorities = resolveRoles(request.getRole());
        user.setAuthorities(authorities);

        userRepository.save(user);

        return ResponseEntity.ok(
                new MessageResponse("User registered successfully!!"));
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateJwt(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        UserDetails userDetails = userRepository
                .findByUsername(jwtUtil.getUsernameFromToken(token))
                .orElse(null);

        return jwtUtil.validateToken(token, userDetails)
                ? ResponseEntity.ok("Token is valid!")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Token is invalid!");
    }

    private Set<Authority> resolveRoles(Set<String> roleNames) {
        Set<Authority> roles = new HashSet<>();

        if (roleNames == null || roleNames.isEmpty()) {
            roles.add(fetchRole(AuthorityEnum.USER));
            return roles;
        }

        for (String role : roleNames) {
            switch (role.toLowerCase()) {
                case "admin":
                    roles.add(fetchRole(AuthorityEnum.ADMIN));
                    break;
                case "mod":
                    roles.add(fetchRole(AuthorityEnum.MODERATOR));
                    break;
                default:
                    roles.add(fetchRole(AuthorityEnum.USER));
            }
        }
        return roles;
    }

    private Authority fetchRole(AuthorityEnum authorityEnum) {
        return authorityRepository.findByAuthority(authorityEnum)
                .orElseThrow(() ->
                        new RuntimeException("Error: Role not found."));
    }
}