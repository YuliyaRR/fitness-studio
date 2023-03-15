package by.it_academy.product.web.controllers.utils;

import by.it_academy.product.core.dto.user.UserToken;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtTokenUtil {

    private static final String jwtSecret = "zgxdtyjuihlhi";
    private static final String jwtIssuer = "fitnessStudio";

    public static String getMail(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public static String getRole(String token) {
        return  (String) Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().get("role");
    }

    public static String getFio(String token) {
        return (String) Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().get("fio");
    }

    public static UserToken getUserToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        String fio = (String) body.get("fio");
        String role = (String) body.get("role");
        String mail = body.getSubject();

        return new UserToken(mail, role, fio);
    }

    public static Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public static boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
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
