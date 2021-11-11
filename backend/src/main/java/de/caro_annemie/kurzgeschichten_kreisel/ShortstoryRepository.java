package de.caro_annemie.kurzgeschichten_kreisel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.caro_annemie.kurzgeschichten_kreisel.model.Shortstory;

/**
 * Interface, that extends Spring Boots database capabilities for object: Shortstory
 */ 
public interface ShortstoryRepository extends JpaRepository<Shortstory, Long> {
   
    //ohne Limitierung
    @Query("SELECT s FROM Shortstory s WHERE s.title = ?1 ORDER BY s.id DESC")
    List<Shortstory> findByTitle(String title);

    @Query("SELECT s FROM Shortstory s WHERE s.genre = ?1 ORDER BY s.id DESC")
    List<Shortstory> findByGenre(String genre);

    @Query("SELECT s FROM Shortstory s WHERE s.author = ?1 ORDER BY s.id DESC")
    List<Shortstory> findByAuthor(String author);

    @Query("SELECT s FROM Shortstory s ORDER BY s.id DESC")
    List<Shortstory> findAll();
    
    //mit Limitierung
    @Query(value = "SELECT * FROM shortstory WHERE title = ?1 ORDER BY id DESC LIMIT ?2", nativeQuery = true)
    List<Shortstory> findByTitle(String title, Integer limit);

    @Query(value = "SELECT * FROM shortstory WHERE genre = ?1 ORDER BY id DESC LIMIT ?2", nativeQuery = true)
    List<Shortstory> findByGenre(String genre, Integer limit);

    @Query(value = "SELECT * FROM shortstory WHERE author = ?1 ORDER BY id DESC LIMIT ?2", nativeQuery = true)
    List<Shortstory> findByAuthor(String author, Integer limit);

    @Query(value = "SELECT * FROM shortstory ORDER BY id DESC LIMIT ?1", nativeQuery = true)
    List<Shortstory> findAll(Integer limit);

}
