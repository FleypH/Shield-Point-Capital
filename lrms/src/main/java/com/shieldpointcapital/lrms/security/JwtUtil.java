package com.shieldpointcapital.lrms.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // ─── Configuration ───────────────────────────────────────────

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    // ─── Key generation ──────────────────────────────────────────

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // ─── Token generation ────────────────────────────────────────

    public String generateToken(String staffId,
                                String role,
                                Integer tokenVersion) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("tokenVersion", tokenVersion);

        return Jwts.builder()
            .subject(staffId)
            .claims(claims)
            .issuedAt(new Date())
            .expiration(new Date(
                System.currentTimeMillis() + jwtExpiration))
            .signWith(getSigningKey())
            .compact();
    }

    // ─── Token validation ────────────────────────────────────────

    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ─── Data extraction ─────────────────────────────────────────

    public String extractStaffId(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public Integer extractTokenVersion(String token) {
        return getClaims(token).get("tokenVersion", Integer.class);
    }

    // ─── Internal helper ─────────────────────────────────────────

    private Claims getClaims(String token) {
        return Jwts.parser()
            .verifyWith(
                Keys.hmacShaKeyFor(jwtSecret.getBytes())
            )
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}