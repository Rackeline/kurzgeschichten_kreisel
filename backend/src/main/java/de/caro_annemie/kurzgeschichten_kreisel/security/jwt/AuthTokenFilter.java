package de.caro_annemie.kurzgeschichten_kreisel.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * request filter for validation of JSON Webtoken (JWT)
 */
public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsService userDetailsService;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  /**
   * extracts JWT from request (if existing), validates and marks user as logged in if everything is ok
   * @param request http request
   * @param response http response (not used, but set as mandatory by parent class)
   * @param filterChain request filter chain
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    try {
      // extract JWT from request
      String jwt = parseJwt(request);

      // validate token
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        // get username from token 
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        // generate userDetails object from username
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // generate authentication object from userDetails
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // add request to authentication
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // magic in Spring Security --> gives authenticated UserDetails to global Security context 
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }

    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response);
  }

  /**
   * get Jwt from Http-request-Header
   * @param request http request
   * @return JWT String
   */
  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length());
    }

    return null;
  }
}
