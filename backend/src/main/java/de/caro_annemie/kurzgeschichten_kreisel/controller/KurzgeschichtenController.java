package de.caro_annemie.kurzgeschichten_kreisel.controller;

import de.caro_annemie.kurzgeschichten_kreisel.KurzgeschichteNotFoundException;
import de.caro_annemie.kurzgeschichten_kreisel.KurzgeschichtenRepository;
import de.caro_annemie.kurzgeschichten_kreisel.model.Kurzgeschichte;
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
public class KurzgeschichtenController {
  private final KurzgeschichtenRepository repository;

  //constructor
  public KurzgeschichtenController(KurzgeschichtenRepository repository) {
    this.repository = repository;
  }

  // --------------URL mappings------------------

  //create shortstory in database
  @PostMapping("/shortstories")
  @CrossOrigin
  public Kurzgeschichte create(@RequestBody Kurzgeschichte kurzgeschichte) {
    return repository.save(kurzgeschichte);
  }

  //change shortstory
  @PutMapping("/shortstories/{id}")
  @CrossOrigin
  Kurzgeschichte replace(
    @RequestBody Kurzgeschichte newKurzgeschichte,
    @PathVariable Long id
  ) {
    return repository
      .findById(id)
      .map(
        kurzgeschichte -> {
          kurzgeschichte.setTitle(newKurzgeschichte.getTitle());
          kurzgeschichte.setText(newKurzgeschichte.getText());
          kurzgeschichte.setGenre(newKurzgeschichte.getGenre());
          return repository.save(kurzgeschichte);
        }
      )
      .orElseGet(
        () -> {
          //newKurzgeschichte.setId(id);
          return repository.save(newKurzgeschichte);
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
  public List<Kurzgeschichte> getList(
    @RequestParam(required = false) String title, String genre, String author
  ) {
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

  //get single item by id
  @GetMapping("/shortstories/{id}")
  @CrossOrigin
  public Kurzgeschichte getByID(@PathVariable Long id) {
    return repository
      .findById(id)
      .orElseThrow(() -> new KurzgeschichteNotFoundException(id));
  }
}
