package de.caro_annemie.kurzgeschichten_kreisel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.caro_annemie.kurzgeschichten_kreisel.model.Kurzgeschichte;

/**
 * Interface, that extends Spring Boots database capabilities for object: Kurzgeschichte
 */ 
public interface KurzgeschichtenRepository extends JpaRepository<Kurzgeschichte, Long> {
   
    @Query("SELECT k FROM Kurzgeschichte k WHERE k.title = ?1")
    List<Kurzgeschichte> findByTitle(String title);

}
