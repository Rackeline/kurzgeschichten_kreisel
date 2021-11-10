package de.caro_annemie.kurzgeschichten_kreisel.controller;

import de.caro_annemie.kurzgeschichten_kreisel.ShortstoryNotFoundException;
import de.caro_annemie.kurzgeschichten_kreisel.ShortstoryRepository;
import de.caro_annemie.kurzgeschichten_kreisel.model.Shortstory;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortstoryController {
  private final ShortstoryRepository repository;

  //constructor
  public ShortstoryController(ShortstoryRepository repository) {
    this.repository = repository;
  }

  // --------------URL mappings------------------

  //create shortstory in database
  @PostMapping("/shortstories")
  @CrossOrigin
  public Shortstory create(@RequestBody Shortstory shortstory) {
    return repository.save(shortstory);
  }

  //change shortstory
  @PutMapping("/shortstories/{id}")
  @CrossOrigin
  Shortstory replace(
    @RequestBody Shortstory newShortstory,
    @PathVariable Long id
  ) {
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

  //delete shortstory from database
  @DeleteMapping("/shortstories/{id}")
  @CrossOrigin
  void delete(@PathVariable Long id) {
    repository.deleteById(id);
  }

  //get list of entries
  @GetMapping("/shortstories")
  @CrossOrigin
  public List<Shortstory> getList(
    @RequestParam(required = false) String title, 
    @RequestParam(required = false) String genre, 
    @RequestParam(required = false) String author,
    @RequestParam(required = false) Integer limit
  ) {
    if (limit == null) {
      if (title != null) {
        return repository.findByTitle(title);
      }
      if (genre != null) {
        return repository.findByGenre(genre);
      }
      if (author != null) {
        return repository.findByAuthor(author);
      }
      return repository.findAll();
    }
    else {
      if (title != null) {
        return repository.findByTitle(title, limit);
      }
      if (genre != null) {
        return repository.findByGenre(genre, limit);
      }
      if (author != null) {
        return repository.findByAuthor(author, limit);
      }
      return repository.findAll(limit);
    }
    
    
  }


  //get single item by id
  @GetMapping("/shortstories/{id}")
  @CrossOrigin
  public Shortstory getByID(@PathVariable Long id) {
    return repository
      .findById(id)
      .orElseThrow(() -> new ShortstoryNotFoundException(id));
  }
}
