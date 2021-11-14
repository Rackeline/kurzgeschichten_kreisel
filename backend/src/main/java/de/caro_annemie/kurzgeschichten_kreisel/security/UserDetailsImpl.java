package de.caro_annemie.kurzgeschichten_kreisel.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.caro_annemie.kurzgeschichten_kreisel.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  @JsonIgnore
  private Long id;

  private String username;

  @JsonIgnore
  private String email;

  @JsonIgnore
  private String password;

  // Collection mit vielen Objekten die alle von Granted Authority (Spring boot Security) erben
  private Collection<? extends GrantedAuthority> authorities;

  //constructor
  public UserDetailsImpl(
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

  //macht aus User ein UserDetails
  public static UserDetailsImpl build(User user) {
	  // legt ListObjekt zur Speicherung der Rollen an
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	  //fügt Rolle des User Objekts zu Liste hinzu
    authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

    return new UserDetailsImpl(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getPassword(),
      authorities
    );
  }

  // getter
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

  // Prüfmethoden, von Interface vorgegeben --> unbenutzt deswegen immer true
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
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
