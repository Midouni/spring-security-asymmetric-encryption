package com.midouni.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final String TOKEN_TYPE = "token_type";

    @Value("${spring.security.jwt.private-key-path}")
    private String privateKeyPath;
    @Value("${spring.security.jwt.public-key-path}")
    private String publicKeyPath;
    @Value("${spring.security.jwt.access-token-expiration}")
    private long accessTokenExpiration;
    @Value("${spring.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public JwtService() throws Exception {
        this.privateKey = KeyUtils.loadPrivateKey(privateKeyPath);
        this.publicKey = KeyUtils.loadPublicKey(publicKeyPath);
    }


    public String generateAccessToken(String email) {
        Map<String, Object> claims = Map.of(TOKEN_TYPE, "ACCESS_TOKEN");
        return buildToken(email,claims,accessTokenExpiration);
    }
    public String generateRefreshToken(final String  email) {
        Map<String, Object> claims = Map.of(TOKEN_TYPE, "REFRESH_TOKEN");
        return buildToken(email,claims,refreshTokenExpiration);
    }

    public boolean isTokenValid(String token, String expectedEmail) {
        final String email = extractEmail(token);
        return email.equals(expectedEmail) && isTokenExpired(token);
    }

    public String refreshAccessToken(String refreshToken)  {
        final Claims claims = extractClaims(refreshToken);
        if(!"REFRESH_TOKEN".equals(claims.get(TOKEN_TYPE))) {
            throw new RuntimeException("Invalid refresh token");
        }
        if(isTokenExpired(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }
        final String email = claims.getSubject();
        return generateAccessToken(email);
    }

    private String buildToken(String email, Map<String, Object> claims, long accessTokenExpiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(privateKey)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(this.publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (final JwtException e){
            throw new RuntimeException("Invalid JWT token", e);
        }

    }

}
