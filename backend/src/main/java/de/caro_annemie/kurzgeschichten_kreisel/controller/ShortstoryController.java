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

@CrossOrigin
@RestController
public class ShortstoryController {

  @Autowired
  private ShortstoryService shortstoryService;

  // --------------URL mappings------------------

  //create shortstory in database Übergibt das Shortstory-Objekt dem ShortstoryService zur Ablage in der Datenbank
  @PostMapping("/shortstories")
  @PreAuthorize("hasRole('Author')")
  public Shortstory create(@RequestBody Shortstory shortstory) {
    return shortstoryService.create(shortstory);
  }

  //change shortstory Übergibt das Shortstory-Objekt dem ShortstoryService zur Ersetzung eines bestehenden Objekts in der Datenbank
  @PutMapping("/shortstories/{id}")
  @PreAuthorize("hasRole('Author')")
  Shortstory replace(
    @RequestBody Shortstory newShortstory,
    @PathVariable Long id
  ) {
    return shortstoryService.replace(newShortstory, id);
  }

  //delete shortstory from database
  @DeleteMapping("/shortstories/{id}")
  @PreAuthorize("hasRole('Author')")
  void delete(@PathVariable Long id) {
    shortstoryService.delete(id);
  }

  //get list of entries
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

  //get single item by id
  @GetMapping("/shortstories/{id}")
  @PreAuthorize("hasRole('Author') or hasRole('Reader')")
  public Shortstory getByID(@PathVariable Long id) {
    return shortstoryService.getByID(id);
  }
}
