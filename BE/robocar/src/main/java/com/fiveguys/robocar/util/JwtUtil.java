package com.fiveguys.robocar.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;


public class JwtUtil {
    //시간 단위는 ms
    static final Long EXPIRE_TIME = 30L * 24 * 60 * 60 * 1000;
    //TODO
    //아래 필드 값들은 꼭 secret으로 빼놓을 것
    // SECRET_KEY는 BASE64로 인코딩 된 값으로 가정, 그냥 String으로 하고 싶으면 코드 수정 필요
    // Base64.getDecoder().decode(SECRET_KEY) -> SECRET_KEY.getBytes(StandardCharsets.UTF_8))
    private static final String SECRET_KEY = "CHANGETHISCHANGETHISCHANGETHISCHANGETHISCHANGETHIS";
    private static final String ISSUER = "FIVEGUYS";
    public static String createToken(String loginId){
        return Jwts.builder()
                .setSubject(loginId)
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, Base64.getDecoder().decode(SECRET_KEY)).compact();

    }

    public static String extractLoginId(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimsResolver){


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

    public static Boolean validateToken(String token) {
        try {
            String issuer = extractIssuer(token);
            return !isTokenExpired(token) && ISSUER.equals(issuer);
        } catch(Exception e){
            return false;
        }
    }

    private static Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private static String extractIssuer(String token){ return extractClaim(token, Claims::getIssuer);}

}
