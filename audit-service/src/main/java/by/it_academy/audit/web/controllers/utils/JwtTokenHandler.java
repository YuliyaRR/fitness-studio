package by.it_academy.audit.web.controllers.utils;

import by.it_academy.audit.config.JWTProperty;
import by.it_academy.audit.core.dto.UserToken;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenHandler {
    private final JWTProperty property;

    public JwtTokenHandler(JWTProperty property) {
        this.property = property;
    }

    public String getMail(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getRole(String token) {
        return  (String) Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody().get("role");
    }

    public String getFio(String token) {
        return (String) Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody().get("fio");
    }

    public UUID getUUID(String token) {
        String uuid = (String) Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody().get("uuid");
        return UUID.fromString(uuid);
    }

    public UserToken getUserToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();
        String fio = (String) body.get("fio");
        String role = (String) body.get("role");
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
