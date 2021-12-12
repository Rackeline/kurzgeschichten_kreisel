package de.caro_annemie.kurzgeschichten_kreisel.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.caro_annemie.kurzgeschichten_kreisel.model.User;

/**
 * Interface, that extends Spring Boots database capabilities for object: User
 */ 
public interface UserRepository extends JpaRepository<User, Long>{
    /**
     * returns user
     * @param username
     * @return user
     */
    Optional<User> findByUsername(String username);

    /**
     * checks if username already exists in database
     * @param username
     * @return boolean
     */
    Boolean existsByUsername(String username);
  
    /**
     * checks if email is already exists in database
     * @param email
     * @return boolean
     */
    Boolean existsByEmail(String email);
}
