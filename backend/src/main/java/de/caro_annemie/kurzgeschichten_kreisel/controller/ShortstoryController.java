package de.caro_annemie.kurzgeschichten_kreisel.controller;

import de.caro_annemie.kurzgeschichten_kreisel.model.Shortstory;
import de.caro_annemie.kurzgeschichten_kreisel.services.ShortstoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * provides endpoints to the frontend for CRUD operations on shortstories
 */
@CrossOrigin
@RestController
public class ShortstoryController {

  @Autowired
  private ShortstoryService shortstoryService;

  /**
   * create shortstory in database, transfers the shortstory object to the ShortstoryService and stores it in the database
   * @param shortstory shortstory parameters from request body
   * @return shortstory as JSON
   */
  @PostMapping("/shortstories")
  @PreAuthorize("hasRole('Author')")
  public Shortstory create(@RequestBody Shortstory shortstory) {
    return shortstoryService.create(shortstory);
  }

  /**
   * passes the shortstory object to the ShortstoryService to replace an existing object in the database
   * @param newShortstory shortstory parameters from request body
   * @param id id of shortstory to replace
   * @return shortstory as JSON
   */
  @PutMapping("/shortstories/{id}")
  @PreAuthorize("hasRole('Author')")
  public Shortstory replace(@RequestBody Shortstory newShortstory, @PathVariable Long id) {
    return shortstoryService.replace(newShortstory, id);
  }

  /**
   * delete shortstory from database
   * @param id id of shortstory to delete
   */
  @DeleteMapping("/shortstories/{id}")
  @PreAuthorize("hasRole('Author')")
  public void delete(@PathVariable Long id) {
    shortstoryService.delete(id);
  }

  /**
   * get list of shortstories matching the given parameters
   * @param title title 
   * @param genre genre 
   * @param author author
   * @param limit limit of list entries
   * @return list of matching shortstories as JSON
   */
  @GetMapping("/shortstories")
  @PreAuthorize("hasRole('Author') or hasRole('Reader')")
  public List<Shortstory> getList(
    @RequestParam(required = false) String title, 
    @RequestParam(required = false) String genre, 
    @RequestParam(required = false) String author,
    @RequestParam(required = false) Integer limit
  ) {
     return shortstoryService.getList(title, genre, author, limit);
  }

  /**
   * get shortstory by id
   * @param id id of shortstory to return
   * @return shortstory as JSON
   */
  @GetMapping("/shortstories/{id}")
  @PreAuthorize("hasRole('Author') or hasRole('Reader')")
  public Shortstory getByID(@PathVariable Long id) {
    return shortstoryService.getByID(id);
  }
}
