package com.hcc.utils;

import com.hcc.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

    private static final long TOKEN_LIFETIME_SECONDS = 5 * 24 * 60 * 60;

    @Value("${jwt.secret}")
    private String jwtSecret;

    /* ===================== TOKEN READ METHODS ===================== */

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Date extractIssuedAt(String token) {
        return extractClaim(token, Claims::getIssuedAt);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(parseClaims(token));
    }

    /* ===================== TOKEN VALIDATION ===================== */

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !tokenExpired(token);
    }

    private boolean tokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /* ===================== TOKEN CREATION ===================== */

    public String createToken(User user) {
        return buildToken(user.getUsername());
    }

    private String buildToken(String username) {
        Claims claims = Jwts.claims();
        claims.setSubject(username);
        claims.put(
                "roles",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TOKEN_LIFETIME_SECONDS * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    /* ===================== INTERNAL ===================== */

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }
}
