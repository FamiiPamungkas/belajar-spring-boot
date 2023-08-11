package com.famipam.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${auth.version}")
    private String AUTH_VERSION;

    private static final String SECRET_KEY = "3778214125432A462D4A614E645267556B58703273357638792F423F4528472B";
    private static final long TOKEN_EXPIRATION_TIME = (long) 1000 * 60 * 30;
    private static final int REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    public String extractUsername(String token) throws JwtException {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractAppVersion(String token) throws JwtException {
        Claims claims = extractAllClaims(token);
        return claims.get("ver", String.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws JwtException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("ver", AUTH_VERSION);
        claims.put("type", TokenType.ACCESS_TOKEN);

        long expirationTime = TOKEN_EXPIRATION_TIME;
        if ("admin".equals(userDetails.getUsername())) expirationTime = REFRESH_TOKEN_EXPIRATION_TIME;
        return generateToken(claims, userDetails, expirationTime);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", TokenType.REFRESH_TOKEN);

        return generateToken(claims, userDetails, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails,
            long expirationTime
    ) {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (expirationTime)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && isAccessToken(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isAccessToken(String token) {
        String type = (String) extractClaim(token, x -> x.get("type"));
        if (type == null) return false;

        return type.equals(TokenType.ACCESS_TOKEN.name());
    }

    public boolean isRefreshToken(String token) {
        String type = (String) extractClaim(token, x -> x.get("type"));
        if (type == null) return false;

        return type.equals(TokenType.REFRESH_TOKEN.name());
    }

    private Date extractExpiration(String token) throws JwtException {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) throws JwtException {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isNeedToReAuthentication(String token) {
        try {
            String appVersion = extractAppVersion(token);
            String[] version = appVersion.split("\\.");
            String[] currentVersion = AUTH_VERSION.split("\\.");
            return !version[1].equals(currentVersion[1]);
        } catch (Exception e) {
            return true;
        }
    }
}

enum TokenType {
    ACCESS_TOKEN,
    REFRESH_TOKEN
}
