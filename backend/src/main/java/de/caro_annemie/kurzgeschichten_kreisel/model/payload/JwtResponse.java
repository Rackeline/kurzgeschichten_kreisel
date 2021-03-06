package de.caro_annemie.kurzgeschichten_kreisel.model.payload;

import java.util.List;

/**
 * response for succesful authentification
 */
public class JwtResponse {

  public JwtResponse(
    String token,
    String username,
    String email,
    List<String> roles
  ) {
    this.token = token;
    this.username = username;
    this.email = email;
    this.roles = roles;
  }

  private String token, username, email;
  private List<String> roles;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
}
