package com.sabindra.portfolio.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component // create exactly one instance of this class and keep it ready to hand to anyone who needs it.
public class JwtUtil {

    @Value("${app.jwt.secret}")//gets secret key from application property
    private String jwtSecret;

    @Value("${app.jwt.expiration}")//how long a token stays valid before someone has to long in again
    private long jwtExpirationMS;

    //converts plain text to cryptographic signing key
    private Key getSigninKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    //GenerateToken for the client
    public String generateToken(String username, String role){
        Map<String, Object> claims = new HashMap<>();//claims is the information that we provide to the client so
        // that they have enough info for the authorization
        claims.put("role", role);
        return Jwts.builder()//builder pattern is used to attach the required information
                .claims(claims)
                .subject(username)//client username
                .issuedAt(new Date())//Date it was given
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMS))//Date when it will expire
                .signWith(getSigninKey())//cryptographically seal with secret key so nobody can break it
                .compact();// this is used to print as a string
    }

    //Given a badge, read whose name is on it
    public String extractUsername(String token){
        return extractAllCalims(token).getSubject();
    }

    //Extracts the role from the token
    public String extractRole(String token){
        return extractAllCalims(token).get("role", String.class);
    }

    //Check if the date expires
    public boolean isTokenExpired(String token){
        return extractAllCalims(token).getExpiration().before(new Date());
    }

    //Two checks: name on the badge match who is claiming it and is it not expired yet
    public boolean validateToken(String token, String username){
        String extractUsername = extractUsername(token);
        return extractUsername.equals(username)&& !isTokenExpired(token);
    }

    //unlock the badge and read everything written on it,
    private Claims extractAllCalims(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) getSigninKey())//re-checks the cryptographic seal using your secret key.
                .build().parseSignedClaims(token)
                .getPayload();
    }

}
