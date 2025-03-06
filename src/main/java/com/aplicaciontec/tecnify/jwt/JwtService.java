package com.aplicaciontec.tecnify.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String SECRET_KEY = "MuySeguraClaveDeMasDeTreintaYDosCaracteres!!!";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String,Object> extraClaims, UserDetails user){
;
        Instant now = Instant.now(); // Fecha actual
        Instant expiration = now.plus(60, ChronoUnit.DAYS);

        Date issuedAt = Date.from(now); // Convierte a Date
        Date expirationDate = Date.from(expiration); // Convierte a Date

        return Jwts.builder()
                .claims()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .issuedAt(issuedAt)
                .and()
                .signWith(key)
                .compact();

    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private Claims getAllClaims(String token){
        return Jwts
                .parser().setSigningKey(key).build().parseSignedClaims(token).getPayload();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
}