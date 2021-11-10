package de.caro_annemie.kurzgeschichten_kreisel;

public class ShortstoryNotFoundException extends RuntimeException {

    public ShortstoryNotFoundException(Long id) {
      super("Could not find story with id: " + id);
    }
  }
