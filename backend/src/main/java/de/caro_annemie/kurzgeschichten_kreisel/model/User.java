package de.caro_annemie.kurzgeschichten_kreisel.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(
  name = "`user`",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email"),
  }
)

public class User {
  @Id
  @GeneratedValue
  private long id;

  @NotBlank
  private String username;

  @NotBlank
  private String role;

  @NotBlank
  private String password;

  @NotBlank
  private String email;

  //constructor
  public User(String username, String role, String password, String email) {
    this.username = username;
    this.role = role;
    this.password = password;
    this.email = email;
  }

  public User() {}

  //getter and setter
  public long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
