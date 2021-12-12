package de.caro_annemie.kurzgeschichten_kreisel.services;

import de.caro_annemie.kurzgeschichten_kreisel.model.SecurityUserDetails;
import de.caro_annemie.kurzgeschichten_kreisel.model.User;
import de.caro_annemie.kurzgeschichten_kreisel.model.payload.JwtResponse;
import de.caro_annemie.kurzgeschichten_kreisel.model.payload.LoginRequest;
import de.caro_annemie.kurzgeschichten_kreisel.model.payload.SignupRequest;
import de.caro_annemie.kurzgeschichten_kreisel.repositories.UserRepository;
import de.caro_annemie.kurzgeschichten_kreisel.security.jwt.JwtUtils;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * service methods to use UserDetails
 */
@Service
public class AuthService implements UserDetailsService {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    User user = userRepository
      .findByUsername(username)
      .orElseThrow(
        () ->
          new UsernameNotFoundException(
            "User Not Found with username: " + username
          )
      );

    return SecurityUserDetails.build(user);
  }

  /**
   * authenticates user by given login request
   * @param loginRequest login request
   * @return jwt, id, username and email as JwtResponse
   */
  public JwtResponse authenticateUser(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginRequest.getUsername(),
        loginRequest.getPassword()
      )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
    List<String> roles = userDetails
      .getAuthorities()
      .stream()
      .map(item -> item.getAuthority())
      .collect(Collectors.toList());

    // return jwt, id, username, email as JSON
    return new JwtResponse(
      jwt,
      userDetails.getUsername(),
      userDetails.getEmail(),
      roles
    );
  }

  /**
   * stores new user in database using the given signup informations
   * @param signUpRequest registration parameters as SignUpRequest
   * @return created user as User
   */
  public User registerUser(SignupRequest signUpRequest) {

    // Create new user object
    User user = new User(
      signUpRequest.getUsername(),
      signUpRequest.getRole(),
      encoder.encode(signUpRequest.getPassword()),
      signUpRequest.getEmail()
    );

    // save user in database and return user
    return userRepository.save(user);
  }
}
