package de.caro_annemie.kurzgeschichten_kreisel;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.caro_annemie.kurzgeschichten_kreisel.model.User;

/**
 * Interface, that extends Spring Boots database capabilities for object: User
 */ 
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
  
    Boolean existsByEmail(String email);
}
