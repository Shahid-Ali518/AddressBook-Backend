package com.example.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtils {


    private static final String SECRET_KEY =  "TaK+HaV&ucCHEPsEVfypW#7g9^k*Z9$V"; // self generated must be 32 bit long

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // hmacsha a algorithm to generate key
    }


    public String generateToken(String username){
        HashMap<String, Object> claims = new HashMap<>(); // use for data like user and its detail
        return createToken(claims, username);
    }

    public String createToken(HashMap<String, Object > claims,String username) {

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .header().empty().add("typ", "JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 3 k
                .signWith(getSigningKey())
                .compact();
    }


    // method to extract all claims from generated token
    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // method to extract username from generated token
    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    // method to extract expiry  from generated token
    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    // method to extract claim  from generated token
    public String extractClaim(String token, String claim){
        return extractAllClaims(token).get(claim, String.class);
    }

    // method to check if token is expired
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date()); // checks if current date is before of expiration or not
    }

    // method to check validation of token
    public boolean validateToken(String token, String username){
        final String extract = extractUsername(token);
        return (extract.equals(username) && !isTokenExpired(token));
    }

}
