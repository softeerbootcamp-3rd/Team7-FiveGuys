package com.fiveguys.robocar.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;

import java.util.Date;
import java.util.function.Function;


public class JwtUtil {
    //시간 단위는 ms
    static final Long EXPIRE_TIME = 30L * 24 * 60 * 60 * 1000;
    //TODO
    //아래 필드 값들은 꼭 secret으로 빼놓을 것
    // SECRET_KEY는 BASE64로 인코딩 된 값으로 가정, 그냥 String으로 하고 싶으면 코드 수정 필요
    // DatatypeConverter.parseBase64Binary(SECRET_KEY)) -> SECRET_KEY.getBytes(StandardCharsets.UTF_8))
    private static final String SECRET_KEY = "CHANGETHISCHANGETHISCHANGETHISCHANGETHISCHANGETHIS";

    public String createToken(String loginId){
        return Jwts.builder()
                .setSubject(loginId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256,DatatypeConverter.parseBase64Binary(SECRET_KEY)).compact();

    }

    public String extractLoginId(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                            .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                            .parseClaimsJws(token)
                            .getBody();
        return claimsResolver.apply(claims);
    }
    //TODO
    //날짜 뿐만아니라 우리가 발급한 토큰인지 구별할 필요도 있어보임
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
