package com.fiveguys.robocar.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    final Long EXPIRE_TIME = 30L * 24 * 60 * 60 * 1000;


    private final String SECRET_KEY;

    private final String ISSUER;

    public JwtUtil(@Value("${jwt.key}") String secretKey, @Value("${jwt.issuer}") String issuer) {
        SECRET_KEY = secretKey;
        ISSUER = issuer;
    }

    public String createToken(Long userId){
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, Base64.getDecoder().decode(SECRET_KEY)).compact();

    }

    public String extractUserId(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Base64.getDecoder().decode(SECRET_KEY))
                    .parseClaimsJws(token)
                    .getBody();
            return claimsResolver.apply(claims);
        }
        catch(Exception e){
            throw new RuntimeException("JWT 토큰 오류", e);
        }
    }

    public Boolean validateToken(String token) {
        try {
            String issuer = extractIssuer(token);
            return !isTokenExpired(token) && ISSUER.equals(issuer);
        } catch(Exception e){
            return false;
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String extractIssuer(String token){ return extractClaim(token, Claims::getIssuer);}

}
