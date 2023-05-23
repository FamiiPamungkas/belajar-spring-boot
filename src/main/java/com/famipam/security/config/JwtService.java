package com.famipam.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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

    @Value("${app.version}")
    private String APP_VERSION;

    private static final String SECRET_KEY = "3778214125432A462D4A614E645267556B58703273357638792F423F4528472B";
    private static final int EXPIRATION_TIME = 1000 * 60 * 60;

    public String extractUsername(String token) throws SignatureException, ExpiredJwtException {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractAppVersion(String token) throws SignatureException, ExpiredJwtException {
        Claims claims = extractAllClaims(token);
        return claims.get("ver", String.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws SignatureException, ExpiredJwtException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ) {
        extractClaims.put("ver", APP_VERSION);
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (EXPIRATION_TIME)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) throws SignatureException, ExpiredJwtException {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) throws SignatureException, ExpiredJwtException {
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

    public String refreshToken(String token) {
        Claims claims = extractAllClaims(token);

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        claims.setIssuedAt(now);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isNeedToReAuthentication(String token) {
        try {
            String appVersion = extractAppVersion(token);
            String[] version = appVersion.split("\\.");
            String[] currentVersion = APP_VERSION.split("\\.");
            return !version[1].equals(currentVersion[1]);
        } catch (Exception e) {
            return true;
        }
    }
}
