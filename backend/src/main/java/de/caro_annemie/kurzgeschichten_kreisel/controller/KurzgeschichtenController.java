package de.caro_annemie.kurzgeschichten_kreisel.controller;

import de.caro_annemie.kurzgeschichten_kreisel.KurzgeschichteNotFoundException;
import de.caro_annemie.kurzgeschichten_kreisel.KurzgeschichtenRepository;
import de.caro_annemie.kurzgeschichten_kreisel.model.Kurzgeschichte;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  //get list of entries
  @GetMapping("/shortstories")
  public List<Kurzgeschichte> getList(@RequestParam(required = false) String title) {
      if (title != null) {
        return repository.findByTitle(title);
      }
    return repository.findAll();
  }

  //get single item by id
  @GetMapping("/shortstories/{id}")
  public Kurzgeschichte getByID(@PathVariable Long id) {
    return repository
      .findById(id)
      .orElseThrow(() -> new KurzgeschichteNotFoundException(id));
  }

  //create shortstory in database
  @PostMapping("/shortstories")
  public Kurzgeschichte create(@RequestBody Kurzgeschichte kurzgeschichte) {
      return repository.save(kurzgeschichte);
  }

  //delete shortstory from database
  @DeleteMapping("/shortstories/{id}")
  void delete(@PathVariable Long id) {
    repository.deleteById(id);
  }

}
