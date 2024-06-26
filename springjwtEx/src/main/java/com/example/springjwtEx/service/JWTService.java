package com.example.springjwtEx.service;

import com.example.springjwtEx.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public String extractUserId(String token){
        return extractClaim(token, Claims::getSubject);
    }

//    public boolean isValid(String token, UserDetails user){
//        String username = extractUsername(token);
//        return (username.equals(user.getUsername()) && !isTokenExpired(token));
//    }
    public boolean isValid(String token, User user){
        String userid = extractUserId(token);
        return (Integer.valueOf(userid).equals(user.getId()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User user){

        return Jwts
                .builder()
//                .subject(user.getUsername())
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey(){
        String SECRET_KEY = "ca21021fa511a1017173fd1ef04af43e589471b72aa76fc726b5d1bcf171771a";
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
