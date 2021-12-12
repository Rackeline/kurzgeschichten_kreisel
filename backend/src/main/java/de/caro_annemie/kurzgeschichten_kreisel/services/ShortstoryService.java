package de.caro_annemie.kurzgeschichten_kreisel.services;

import de.caro_annemie.kurzgeschichten_kreisel.ShortstoryNotFoundException;
import de.caro_annemie.kurzgeschichten_kreisel.model.SecurityUserDetails;
import de.caro_annemie.kurzgeschichten_kreisel.model.Shortstory;
import de.caro_annemie.kurzgeschichten_kreisel.repositories.ShortstoryRepository;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Shortstory related service methods.
 */
@Service
public class ShortstoryService {
  @Autowired
  private ShortstoryRepository repository;

  /**
   * Takes a shortstory, sets its author based on currently logged in user,
   * creation date to now and writes it to the database.
   * @param shortstory Shortstory to save
   * @return Reference to the saved shortstory
   */
  public Shortstory create(Shortstory shortstory) {
    // Get security context and extract user details
    var securityContext = SecurityContextHolder.getContext();
    var userDetails = (SecurityUserDetails) securityContext
      .getAuthentication()
      .getPrincipal();
    // Set creation date to now
    shortstory.setCreationDate(new Date());
    // Set shortstorys author based on currently logged in user
    shortstory.setAuthor(userDetails.getUsername());
    // Persist it and return the shortstory
    return repository.save(shortstory);
  }

  /**
   * Updates title, text and genre of an existing shortstory based on given id.
   * @param newShortstory New shortstory data
   * @param id Id of the shortstory to update
   * @return Updated shortstory
   */
  public Shortstory replace(Shortstory newShortstory, Long id) {
    return repository
      .findById(id)
      .map(
        shortstory -> {
          shortstory.setTitle(newShortstory.getTitle());
          shortstory.setText(newShortstory.getText());
          shortstory.setGenre(newShortstory.getGenre());
          return repository.save(shortstory);
        }
      )
      .orElseGet(
        () -> {
          return repository.save(newShortstory);
        }
      );
  }

  /**
   * Deletes a shortstory identified by given id from the database.
   * @param id Id of the shortstory to delete
   */
  public void delete(Long id) {
    repository.deleteById(id);
  }

  /**
   * Returns a list of Shortystory objects based on given search parameters. If no parameter ist set, a list of all entries will be returned.
   * @param title (Optional) Title of the shortstory to look for
   * @param genre (Optional) Genre of the shortstory to look for
   * @param author (Optional) Author of the shortstory to look for
   * @param limit (Optional) Max number of results to return. If not set, all are returned
   * @return List of Shortstory objects matching the given criteria
   */
  public List<Shortstory> getList(
    String title,
    String genre,
    String author,
    Integer limit
  ) {
    if (limit == null) {
      if (genre != null) {
        if (title != null) {
          return repository.findByTitleInGenre(title, genre);
        }
        return repository.findByGenre(genre);
      }
      if (title != null) {
        return repository.findByTitle(title);
      }
      if (author != null) {
        return repository.findByAuthor(author);
      }
      return repository.findAll();
    } else {
      if (genre != null) {
        if (title != null) {
          return repository.findByTitleInGenre(title, genre, limit);
        }
        return repository.findByGenre(genre, limit);
      }
      if (title != null) {
        return repository.findByTitle(title, limit);
      }
      if (author != null) {
        return repository.findByAuthor(author, limit);
      }
      return repository.findAll(limit);
    }
  }

  /**
   * Returns a single shortstory for given id.
   * @param id Id of the shortstory to return
   * @return Shortstory
   */
  public Shortstory getByID(Long id) {
    return repository
      .findById(id)
      .orElseThrow(() -> new ShortstoryNotFoundException(id));
  }
}
