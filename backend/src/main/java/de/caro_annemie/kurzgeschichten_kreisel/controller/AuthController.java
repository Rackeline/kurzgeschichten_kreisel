package de.caro_annemie.kurzgeschichten_kreisel.controller;

import de.caro_annemie.kurzgeschichten_kreisel.model.payload.LoginRequest;
import de.caro_annemie.kurzgeschichten_kreisel.model.payload.MessageResponse;
import de.caro_annemie.kurzgeschichten_kreisel.model.payload.SignupRequest;
import de.caro_annemie.kurzgeschichten_kreisel.repositories.UserRepository;
import de.caro_annemie.kurzgeschichten_kreisel.services.AuthService;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * provides endpoints to the frontend for registering and login
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class AuthController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  AuthService authService;

  /**
   * user login based on given request body
   * @param loginRequest login parameters from request body
   * @return returns jwt, id, username and email as JSON
   */
  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    var jwtResponse = authService.authenticateUser(loginRequest);
    return ResponseEntity.ok(jwtResponse);
  }

  /**
   * stores new user in database using the signup informations from request body
   * @param signUpRequest registration parameters from request body
   * @return response message as JSON
   */
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
        .badRequest()
        .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
        .badRequest()
        .body(new MessageResponse("Error: Email is already in use!"));
    }

    authService.registerUser(signUpRequest);

    return ResponseEntity.ok(
      new MessageResponse("User registered successfully!")
    );
  }
}
