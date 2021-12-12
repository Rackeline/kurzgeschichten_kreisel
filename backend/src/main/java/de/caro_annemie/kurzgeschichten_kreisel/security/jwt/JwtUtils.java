package de.caro_annemie.kurzgeschichten_kreisel.security.jwt;

import io.jsonwebtoken.*;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import de.caro_annemie.kurzgeschichten_kreisel.model.SecurityUserDetails;

/**
 * helper methods to use JsonWebToken
 */
@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${kurzgeschichten_kreisel.app.jwtSecret}")
  private String jwtSecret;

  @Value("${kurzgeschichten_kreisel.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  /**
   * make JsonWebToken from authentication object
   * @param authentication
   * @return JWT as String
   */
  public String generateJwtToken(Authentication authentication) {
    SecurityUserDetails userPrincipal = (SecurityUserDetails) authentication.getPrincipal();

    return Jwts
      .builder()
      .setSubject((userPrincipal.getUsername()))
      .setIssuedAt(new Date())
      .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
      .signWith(SignatureAlgorithm.HS512, jwtSecret)
      .compact();
  }

  /**
   * extract username from JsonWebTokenken
   * @param token JWT as String
   * @return username
   */
  public String getUserNameFromJwtToken(String token) {
    return Jwts
      .parser()
      .setSigningKey(jwtSecret)
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }

  /**
   * validate JsonWebToken
   * @param authToken JWT to validate
   * @return boolean
   */
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
}
