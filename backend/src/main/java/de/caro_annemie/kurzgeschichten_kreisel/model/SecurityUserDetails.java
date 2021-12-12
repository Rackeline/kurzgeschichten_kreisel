package de.caro_annemie.kurzgeschichten_kreisel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * user representation for spring security
 */
public class SecurityUserDetails implements UserDetails {
  private static final long serialVersionUID = 1L;

  @JsonIgnore
  private Long id;

  private String username;

  @JsonIgnore
  private String email;

  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  /**
   * constructor
   * @param id
   * @param username
   * @param email
   * @param password
   * @param authorities roles of user (more than one is possible: list)
   */
  public SecurityUserDetails(
    Long id,
    String username,
    String email,
    String password,
    Collection<? extends GrantedAuthority> authorities
  ) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  /**
   * creates securityUserDetails object from user
   * @param user user to convert
   * @return securityUserDetails created from user
   */
  public static SecurityUserDetails build(User user) {
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

    return new SecurityUserDetails(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getPassword(),
      authorities
    );
  }

  //getter
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  // methods defined by interface
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SecurityUserDetails user = (SecurityUserDetails) o;
    return Objects.equals(id, user.id);
  }
}
