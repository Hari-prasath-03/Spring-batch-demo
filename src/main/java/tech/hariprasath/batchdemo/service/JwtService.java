package tech.hariprasath.batchdemo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 2;
    private final String SECRET_KEY = "9de11b8fb3b7b0c210e848df2fe19ead14ce4be5c81b897485afafd753b7c231";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .signWith(key)
                .compact();
    }

    private Claims extractToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractSubject(String token) {
        return extractToken(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails, String email) {
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractToken(token).getExpiration().before(new Date());
    }
}
