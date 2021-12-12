package de.caro_annemie.kurzgeschichten_kreisel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.caro_annemie.kurzgeschichten_kreisel.model.Shortstory;

/**
 * Interface, that extends Spring Boots database capabilities for object: Shortstory
 */ 
public interface ShortstoryRepository extends JpaRepository<Shortstory, Long> {
   
    /**
     * Returns a list of shortstories matching given title
     * @param title Title to search for
     * @return List of shortstories containing the title
     */
    @Query(value = "SELECT * FROM Shortstory s WHERE s.title ILIKE CONCAT('%', ?1, '%') ORDER BY s.id DESC", nativeQuery = true)
    List<Shortstory> findByTitle(String title);

    /**
     * Returns a list of shortstories matching given title and genre
     * @param title Title to search for
     * @param genre Genre to search for
     * @return List of shortstories containing the title and genre
     */
    @Query(value = "SELECT * FROM Shortstory s WHERE s.title ILIKE CONCAT('%', ?1, '%') AND s.genre = ?2 ORDER BY s.id DESC", nativeQuery = true)
    List<Shortstory> findByTitleInGenre(String title, String genre);

    /**
     * Returns a list of shortstories matching given genre
     * @param genre Genre to search for
     * @return List of shortstories containing the genre
     */
    @Query("SELECT s FROM Shortstory s WHERE s.genre = ?1 ORDER BY s.id DESC")
    List<Shortstory> findByGenre(String genre);

    /**
     * Returns a list of shortstories matching given author
     * @param author Author to search for
     * @return List of shortstories containing the author
     */
    @Query("SELECT s FROM Shortstory s WHERE s.author = ?1 ORDER BY s.id DESC")
    List<Shortstory> findByAuthor(String author);

    /**
     * Returns all shortstories.
     * @return All shortstories
     */
    @Query("SELECT s FROM Shortstory s ORDER BY s.id DESC")
    List<Shortstory> findAll();

    // ------- with limit ---------------------------------------------------------------------------------

    /**
     * Returns a list of shortstories matching given title
     * @param title Title to search for
     * @param limit max number of returned list entries
     * @return List of shortstories containing the title
     */
    @Query(value = "SELECT * FROM Shortstory s WHERE s.title ILIKE CONCAT('%', ?1, '%') ORDER BY s.id DESC LIMIT ?2", nativeQuery = true)
    List<Shortstory> findByTitle(String title, Integer limit);

    /**
     * Returns a list of shortstories matching given title and genre
     * @param title Title to search for
     * @param genre Genre to search for
     * @param limit max number of returned list entries
     * @return List of shortstories containing the title and genre
     */
    @Query(value = "SELECT * FROM Shortstory s WHERE s.title ILIKE CONCAT('%', ?1, '%') AND s.genre = ?2 ORDER BY s.id DESC LIMIT ?3", nativeQuery = true)
    List<Shortstory> findByTitleInGenre(String title, String genre, Integer limit);

    /**
     * Returns a list of shortstories matching given genre
     * @param genre Genre to search for
     * @param limit max number of returned list entries
     * @return List of shortstories containing the genre
     */
    @Query(value = "SELECT * FROM shortstory WHERE genre = ?1 ORDER BY id DESC LIMIT ?2", nativeQuery = true)
    List<Shortstory> findByGenre(String genre, Integer limit);

    /**
     * Returns a list of shortstories matching given author
     * @param author Author to search for
     * @param limit max number of returned list entries
     * @return List of shortstories containing the author
     */
    @Query(value = "SELECT * FROM shortstory WHERE author = ?1 ORDER BY id DESC LIMIT ?2", nativeQuery = true)
    List<Shortstory> findByAuthor(String author, Integer limit);

    /**
     * Returns all shortstories
     * @param limit max number of returned list entries
     * @return All shortstories
     */
    @Query(value = "SELECT * FROM shortstory ORDER BY id DESC LIMIT ?1", nativeQuery = true)
    List<Shortstory> findAll(Integer limit);

}
