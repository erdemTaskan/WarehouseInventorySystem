package com.erdem.jwt;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private static final String SECRET_KEY="H9xWcK6V5+g/ltZ5TfrOibEwhGUT0UymqtRbZ8NTDUg=";



 public String generateToken(String username){
    return Jwts.builder()
             .setSubject(username)
             .setIssuedAt(new Date())
             .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*2))
             .signWith(getKey(), SignatureAlgorithm.HS256)
             .compact();
 }

  public String getUsernameFromToken(String token){
    return Jwts.parserBuilder()
             .setSigningKey(getKey())
             .build()
             .parseClaimsJws(token)
             .getBody()
             .getSubject();
  }


  public boolean validateToken(String token){

     try {
         Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
         return true;
     }catch (JwtException | IllegalArgumentException exception){
         return false;
     }


  }

 public Key getKey(){
     byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
 }

}
