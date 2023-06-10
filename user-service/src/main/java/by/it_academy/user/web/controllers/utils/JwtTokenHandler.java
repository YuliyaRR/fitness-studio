package by.it_academy.user.web.controllers.utils;

import by.it_academy.user.config.JWTProperty;
import by.it_academy.user.core.dto.user.UserRole;
import by.it_academy.user.core.dto.user.UserToken;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
@RequiredArgsConstructor
@Component
public class JwtTokenHandler {
    private final JWTProperty property;

    public String generateAccessToken(UserToken user) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("fio", user.getFio());
        payload.put("role", user.getRole());
        payload.put("uuid", user.getUuid().toString());
        return Jwts.builder()
                .setSubject(user.getMail())
                .addClaims(payload)
                .setIssuer(property.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7))) // 1 week
                .signWith(SignatureAlgorithm.HS512, property.getSecret())
                .compact();
    }

    public String getMail(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public UserToken getUserToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();
        String fio = (String) body.get("fio");
        UserRole role = UserRole.valueOf((String) body.get("role"));
        String mail = body.getSubject();
        UUID uuid = UUID.fromString((String)body.get("uuid"));

        return new UserToken(mail, role, fio, uuid);
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(property.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            //logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            //logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            //logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            //logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            //logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
